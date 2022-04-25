package org.jmisb.identicalpropertiesshader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import org.apache.maven.plugins.shade.relocation.Relocator;
import org.apache.maven.plugins.shade.resource.ReproducibleResourceTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transformer for maven shade to include one of several identical properties files.
 *
 * <p>Identical in this case excludes any leading or trailing whitespace, or difference in line
 * endings.
 */
public class IdenticalPropertiesTransformer implements ReproducibleResourceTransformer {

    List<String> files = new ArrayList<>();
    private final Map<String, PropertiesFile> propertiesFiles = new HashMap<>();
    private final Logger logger;

    /** Constructor. */
    public IdenticalPropertiesTransformer() {
        this.logger = LoggerFactory.getLogger(IdenticalPropertiesTransformer.class);
    }

    @Override
    public final void processResource(String resource, InputStream is, List<Relocator> relocators)
            throws IOException {
        processResource(resource, is, relocators, 0);
    }

    @Override
    public void processResource(
            String resource, InputStream inputStream, List<Relocator> relocators, long time)
            throws IOException {
        byte[] fileBytes = inputStream.readAllBytes();
        String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
        String normalisedFileContent = fileContent.replaceAll("\\r\\n?", "\n").trim();
        byte[] normalisedFileBytes = normalisedFileContent.getBytes(StandardCharsets.UTF_8);
        Checksum checksum = new CRC32();
        checksum.update(normalisedFileBytes);
        long checksumValue = checksum.getValue();
        if (!propertiesFiles.containsKey(resource)) {
            logger.debug("Matched first instance of " + resource);
            PropertiesFile propertiesFile =
                    new PropertiesFile(normalisedFileBytes, checksumValue, time);
            propertiesFiles.put(resource, propertiesFile);
        } else {
            logger.debug("Matched subsequent instance of " + resource);
            PropertiesFile referenceProperties = propertiesFiles.get(resource);
            if (referenceProperties.getPropertiesChecksum() != checksumValue) {
                logger.warn(
                        "Expected duplicate properties file "
                                + resource
                                + " to be identical, but first instance was: ["
                                + new String(
                                        referenceProperties.getContent(), StandardCharsets.UTF_8)
                                + "] while this instance was ["
                                + normalisedFileContent
                                + "]");
            }
        }
    }

    @Override
    public boolean canTransformResource(String resource) {
        for (String f : files) {
            if (resource.equals(f)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasTransformedResource() {
        if (!propertiesFiles.isEmpty()) {
            return true;
        } else {
            logger.warn("Did not match any properties files in IdenticalPropertiesTransformer");
            return false;
        }
    }

    @Override
    public void modifyOutputStream(JarOutputStream jos) throws IOException {
        for (Entry<String, PropertiesFile> entry : propertiesFiles.entrySet()) {
            logger.info("Including single instance of " + entry.getKey() + " in the shaded jar.");
            PropertiesFile propertiesFile = entry.getValue();
            JarEntry jarEntry = new JarEntry(entry.getKey());
            jarEntry.setTime(propertiesFile.getTimestamp());
            jos.putNextEntry(jarEntry);
            jos.write(propertiesFile.getContent());
        }
    }
}

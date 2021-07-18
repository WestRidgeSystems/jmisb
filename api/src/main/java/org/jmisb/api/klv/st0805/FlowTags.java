package org.jmisb.api.klv.st0805;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is the model class for the CoT {@code _flow-tags_} detail element.
 *
 * <p>It represents all of the Flow Tags for a single message.
 */
public class FlowTags extends CotElement {
    private final Map<String, ZonedDateTime> tags = new LinkedHashMap<>();

    /**
     * Add a flow tag.
     *
     * <p>This should be done every time a system "touches" the CoT message.
     *
     * @param systemName the system name
     * @param timestamp the "now" timestamp
     */
    public void addFlowTag(String systemName, ZonedDateTime timestamp) {
        tags.put(systemName, timestamp);
    }

    /**
     * Write out the flow tags as XML.
     *
     * <p>This includes the {@code detail} element.
     *
     * @param sb the string builder to append to.
     */
    @Override
    public void writeAsXML(StringBuilder sb) {
        sb.append("<_flow-tags_");
        for (Entry<String, ZonedDateTime> entry : tags.entrySet()) {
            sb.append(" ");
            sb.append(entry.getKey());
            sb.append("='");
            sb.append(entry.getValue().format(DateTimeFormatter.ISO_DATE_TIME));
            sb.append("'");
        }
        sb.append("/>");
    }
}

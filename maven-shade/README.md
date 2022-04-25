# Identical Properties Transformer for Apache Maven Shade Plugin

This is a transformer for the "Shade" plugin for Maven.

It provides selective filtering of duplicate entries in the dependencies being shaded.
This occurs with the javacpp jars when multiple architectures are included in an
uber-jar, and we get duplicates of things like
`META-INF/maven/org.bytedeco/ffmpeg/pom.properties`.

By default, the maven shade plugin will add one (and warn about it). This plugin
checks that the files are actually the same, and avoid the warning. In this context,
"the same" means that the content is identical (to CRC32 level of fidelity), except
for possible differences in line endings (which will be ignored).

This is only sensible for text files like properties files. Also, the CRC32 is only
meant to check for possible inadvertent inclusion, and is not proof against
malicious action (noting that if an attacker has modified the input jars, you
have much more serious problems than duplicate properties files).

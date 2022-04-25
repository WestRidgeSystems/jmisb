package org.jmisb.st0805;

/**
 * Cursor On Target Element.
 *
 * <p>This corresponds to something that maps to an XML element when serialized as CoT.
 */
public abstract class CotElement {
    /**
     * Serialize this element as an XML string.
     *
     * @param sb the string builder to serialize to.
     */
    abstract void writeAsXML(StringBuilder sb);

    /**
     * Write start element.
     *
     * @param sb the string builder to serialize to.
     * @param elementName the element name to be started.
     */
    protected void writeStartElement(StringBuilder sb, String elementName) {
        sb.append("<");
        sb.append(elementName);
        sb.append(">");
    }

    /**
     * Write end element.
     *
     * @param sb the string builder to serialize to.
     * @param elementName the name of the element to be closed.
     */
    protected void writeEndElement(StringBuilder sb, String elementName) {
        sb.append("</");
        sb.append(elementName);
        sb.append(">");
    }

    /**
     * Write an attribute key and value pair.
     *
     * @param sb the string builder to serialize to.
     * @param key the attribute key
     * @param value the attribute value as a String.
     */
    protected void writeAttribute(StringBuilder sb, String key, String value) {
        sb.append(" ");
        sb.append(key);
        sb.append("='");
        sb.append(value);
        sb.append("'");
    }

    /**
     * Write an attribute key and value pair.
     *
     * @param sb the string builder to serialize to.
     * @param key the attribute key
     * @param value the attribute value as a double.
     */
    protected void writeAttribute(StringBuilder sb, String key, double value) {
        sb.append(" ");
        sb.append(key);
        sb.append("='");
        sb.append(value);
        sb.append("'");
    }
}

package org.jmisb.api.klv.st0805;

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

    protected void writeStartElement(StringBuilder sb, String elementName) {
        sb.append("<");
        sb.append(elementName);
        sb.append(">");
    }

    protected void writeEndElement(StringBuilder sb, String elementName) {
        sb.append("</");
        sb.append(elementName);
        sb.append(">");
    }

    protected void writeAttribute(StringBuilder sb, String key, String value) {
        sb.append(" ");
        sb.append(key);
        sb.append("='");
        sb.append(value);
        sb.append("'");
    }

    protected void writeAttribute(StringBuilder sb, String key, double value) {
        sb.append(" ");
        sb.append(key);
        sb.append("='");
        sb.append(value);
        sb.append("'");
    }
}

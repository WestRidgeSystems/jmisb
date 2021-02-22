package org.jmisb.api.video;

import java.util.List;

/** Packetized Elementary Stream (PES) information. */
public class PesInfo {
    private final int index;
    private final PesType type;
    private final String codecName;

    /**
     * Constructor.
     *
     * @param index The stream index
     * @param type The type
     * @param codecName The codec name
     */
    public PesInfo(int index, PesType type, String codecName) {
        this.index = index;
        this.type = type;
        this.codecName = codecName;
    }

    /**
     * Get the stream index.
     *
     * @return The stream index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the type.
     *
     * @return The type
     */
    public PesType getType() {
        return type;
    }

    /**
     * Get the codec name.
     *
     * @return The codec name
     */
    public String getCodecName() {
        return codecName;
    }

    /**
     * Return a JSON string representing the value.
     *
     * @return The JSON string
     */
    public String asJson() {
        // TODO: this might be tedious to maintain; consider gson
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("\"index\": ");
        sb.append(index);
        sb.append(", ");
        sb.append("\"type\": \"");
        sb.append(type);
        sb.append("\", ");
        sb.append("\"codec\": \"");
        sb.append(codecName);
        sb.append("\"");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Return a JSON string containing a list of PesInfo objects.
     *
     * @param list The list
     * @return The JSON string
     */
    public static String asJson(List<PesInfo> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("    \"streams\": [\n");
        int i = 0;
        for (PesInfo pes : list) {
            sb.append("        ");
            sb.append(pes.asJson());
            if (++i != list.size()) sb.append(",");
            sb.append("\n");
        }
        sb.append("    ]\n");
        sb.append("}\n");
        return sb.toString();
    }
}

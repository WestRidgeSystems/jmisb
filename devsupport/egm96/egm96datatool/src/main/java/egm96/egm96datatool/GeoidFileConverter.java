package egm96.egm96datatool;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.jmisb.elevation.geoid.Grid;

/**
 * Geoid File Converter.
 *
 * <p>Tool to do offline conversion of the NGA format "WW15MGH.GRD" interpolation grid into a local
 * format that is easy to deserialise using the Java DataInput interface.
 *
 * <p>This is not normally needed by end-users, and is not considered public API.
 */
class GeoidFileConverter {

    /** @param args the command line arguments */
    public static void main(String[] args) throws IOException {
        GeoidFileConverter converter = new GeoidFileConverter();
        converter.writeValuesToFile("egm96.dat");
    }

    void writeValuesToFile(String filename) throws IOException {
        try (Reader reader =
                        new InputStreamReader(
                                getClass()
                                        .getResourceAsStream(
                                                "/EGM96_Interpolation_Grid/WW15MGH.GRD"),
                                StandardCharsets.UTF_8);
                BufferedReader inputStream = new BufferedReader(reader);
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            String firstLine = inputStream.readLine();
            if (firstLine == null) {
                return;
            }
            String[] headerParts = firstLine.trim().split("\\s+");
            if (headerParts.length != 6) {
                throw new IOException("Header line not in expected format:" + firstLine);
            }
            Grid grid = new Grid();
            grid.setBottom(Float.parseFloat(headerParts[0]));
            grid.setTop(Float.parseFloat(headerParts[1]));
            grid.setLeft(Float.parseFloat(headerParts[2]));
            grid.setRight(Float.parseFloat(headerParts[3]));
            grid.setyResolution(Float.parseFloat(headerParts[4]));
            grid.setxResolution(Float.parseFloat(headerParts[5]));
            grid.writeHeaderTo(dos);
            int numColumns = grid.getNumColumns();
            String line = inputStream.readLine();
            int rowIndex = 0;
            int columnIndex = 0;
            while (line != null) {
                String cleanline = line.strip();
                String[] lineParts = cleanline.split("\\s+");
                for (String linePart : lineParts) {
                    if (linePart.strip().isEmpty()) {
                        continue;
                    }
                    float value = Float.parseFloat(linePart.strip());
                    grid.setValue(rowIndex, columnIndex, value);
                    columnIndex += 1;
                    if (columnIndex > numColumns) {
                        rowIndex += 1;
                        columnIndex = 0;
                    }
                }
                line = inputStream.readLine();
            }
            grid.writeValuesTo(dos);
        }
    }
}

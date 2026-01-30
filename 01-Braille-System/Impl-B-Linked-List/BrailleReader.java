package AB3;
import AB3.Interfaces.Decoder;

/**
 * The BrailleReader class provides functionality to translate Braille text lines represented as
 * scan lines into ASCII text. It uses a Decoder to decode Braille character bitmaps into corresponding
 * ASCII characters.
 */
public class BrailleReader {
    private final int WIDTH=2;
    private final int HEIGHT=3;

    private Decoder decoder;    // can store a reference to a BrailleDecoder

    /**
     * Constructs a BrailleReader instance.
     */
    public BrailleReader(Decoder decoder){ // Hint: pass your BrailleDecoder to this constructor
        this.decoder = decoder;
    }

    /**
     * Extracts a Braille character represented as a 2D array from the given Braille scanlines.
     *
     * @param position the zero-based number of the Braille character in the given line data.
     * @param spacing the number of characters used as spacing between Braille characters in the line data.
     * @param brailleLine an array of strings (scanlines) representing the Braille line data.
     * @return the bitmap of a single Braille character extracted from the provided
     *         line data, or null if the {@code brailleLine} is invalid or {@code null},
     *         or the specified position is out of bounds.
     */
    private char[][] getBrailleChar(int position, int spacing, String[] brailleLine){
        int currentIndex = (WIDTH + spacing) * position;
        if (brailleLine == null || brailleLine.length < HEIGHT) {
            return null;
        }
        int length = brailleLine[0].length();
        if (currentIndex + WIDTH > length || currentIndex < 0) {
            return null;
        }
        for (String s : brailleLine) {
            if (s.length() != length) {
                return null;
            }
        }

        char[][] brailleChar = new char[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (brailleLine[i].charAt(currentIndex + j) == ' ') {
                    return null;
                } else {
                    brailleChar[i][j] = brailleLine[i].charAt(currentIndex + j);
                }
            }
        }
        return brailleChar;
    }

    /**
     * Translates the given Braille text lines into an ASCII representation.
     *
     * @param brailleLine an array of strings representing scanlines of Braille text.
     * @param dotSymbol the character used to represent raised Braille dots in the bitmap.
     * @param spacing the number of spaces between individual Braille characters within the input.
     * @return a string representing the ASCII translation of the Braille text. Returns an
     *         empty string if the input is invalid or no Braille characters are detected.
     */
    public String translate(String[] brailleLine, char dotSymbol, int spacing){
        if (brailleLine == null || brailleLine.length < HEIGHT || spacing < 0) {
            return "";
        }

        StringBuilder decoded = new StringBuilder();
        int charCount = (brailleLine[0].length() + spacing) / (WIDTH + spacing);
        for (int i = 0; i < charCount; i++) {
            char[][] bitMap = getBrailleChar(i, spacing, brailleLine);
            if (bitMap == null) {
                return "";
            }
            char decodedChar = decoder.decodeBitmap(bitMap, dotSymbol);
            if (decodedChar == 0) {
                return "";
            }
            decoded.append(decodedChar);
        }
        return decoded.toString();
    }
}

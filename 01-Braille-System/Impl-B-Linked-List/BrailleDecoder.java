package AB3;
import AB3.Interfaces.Decoder;
import AB3.Provided.BrailleEncoder;

/**
 * The class implements a decoders, which decodes Braille symbols (bitmaps) into ASCII characters.
 * <p>It utilizes a binary search tree, to find ASCII characters corresponding to e letter's Braille
 * binary encoding.</p>
 */
public class BrailleDecoder implements Decoder {
    private static final int BITMAP_HEIGHT=3;
    private static final int BITMAP_WIDTH=2;
    private static final char SPACE_SYMBOL=' ';
    private BrailleSymbolTree decoderTree;

    /**
     * Constructs a BrailleDecoder object that decodes Braille symbols (bitmaps) into their
     * corresponding ASCII characters, utilizing a predefined binary search tree.
     *
     * @param encoder     the Braille encoder that corresponds to this decoder. Required by
     *                    the construction of the binary search tree.
     *                    Precondition: ( encoder != null )
     */
    public BrailleDecoder(BrailleEncoder encoder){
        decoderTree = new BrailleSymbolTree(encoder);
    }

    /**
     * Decodes a Braille bitmap into its corresponding ASCII character.
     * The bitmap is represented as a 2D array of characters. The raised dots in the
     * Braille bitmap are identified based on the provided `dotSymbol`. Uses a binary
     * encoding derived from the bitmap to determine the ASCII equivalent via a Braille
     * symbol tree.
     *
     * @param bitMap a 2D character array representing the Braille bitmap.
     *               The array must have dimensions corresponding to the expected
     *               Braille format (height and width). If the input is null or has
     *               incompatible dimensions, a null character (0) is returned.
     * @param dotSymbol the character that represents a raised dot in the Braille bitmap.
     * @return the corresponding ASCII character for the given Braille bitmap.
     *         Returns a space (' ') if the character is unknown or cannot be decoded,
     *         and a null character (0) if the input is invalid.
     */
    public char decodeBitmap(char[][] bitMap, char dotSymbol){
        if (bitMap == null || bitMap.length != BITMAP_HEIGHT || bitMap[0].length != BITMAP_WIDTH) {
            return 0;
        }

        byte encoded = 0;
        for (int i = BITMAP_WIDTH - 1; i >= 0; i--) {
            for (int j = BITMAP_HEIGHT - 1; j >= 0; j--) {
                encoded = (byte) (encoded << 1);

                if (bitMap[j][i] == dotSymbol) {
                    encoded = (byte) (encoded | 1);
                }
            }
        }

        var node = decoderTree.getNode(encoded);
        if (node != null) {
            return node.getSymbol();
        } else {
            return SPACE_SYMBOL;
        }
    }
}




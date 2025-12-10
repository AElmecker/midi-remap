package at.elmecker.midiremap;

import java.util.Locale;

public final class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @see #toHexAndDecString(int)
     */
    public static String toHexString(byte b) {
        return toHexString(Byte.toUnsignedInt(b));
    }

    /**
     * Converts the given unsigned byte value into a hex string representation,
     * ranging from {@code 0x00} to {@code 0xFF}.
     */
    public static String toHexString(int unsignedByte) {
        return String.format(Locale.ROOT, "0x%02X", unsignedByte);
    }

    /**
     * Converts the given unsigned byte value into a hex and decimal string representation like {@code 0x0A (10)}.
     *
     * @see #toHexAndDecString(int)
     */
    public static String toHexAndDecString(int unsignedByte) {
        return toHexString(unsignedByte) + " (" + unsignedByte + ")";
    }

    /**
     * Creates a string representation from the given byte-array, using the unsigned byte values in hex format like
     * {@code [0x00, 0x0A, 0xFF]}
     *
     * @see #toHexString(byte)
     */
    public static String byteArrayToHexString(byte[] b) {
        if (b == null || b.length == 0) {
            return "[]";
        }

        StringBuilder builder = new StringBuilder(2 + b.length * 6);
        builder.append('[');

        for (int i = 0; i < b.length; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(toHexString(b[i]));
        }

        builder.append(']');
        return builder.toString();
    }
}

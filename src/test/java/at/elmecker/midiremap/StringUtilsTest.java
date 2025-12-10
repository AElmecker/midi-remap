package at.elmecker.midiremap;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilsTest {

    @ParameterizedTest
    @MethodSource
    void toHexStringIntegers(int input, String output) {
        String result = StringUtils.toHexString(input);
        assertThat(result).isEqualTo(output);
    }

    private static Stream<Arguments> toHexStringIntegers() {
        return Stream.of(
                Arguments.of(0, "0x00"),
                Arguments.of(127, "0x7F"),
                Arguments.of(255, "0xFF"),
                Arguments.of(Integer.MIN_VALUE, "0x80000000"),
                Arguments.of(Integer.MAX_VALUE, "0x7FFFFFFF")
        );
    }

    @ParameterizedTest
    @MethodSource
    void toHexStringBytes(byte input, String output) {
        String result = StringUtils.toHexString(input);
        assertThat(result).isEqualTo(output);
    }

    private static Stream<Arguments> toHexStringBytes() {
        return Stream.of(
                Arguments.of((byte) 0, "0x00"),
                Arguments.of(Byte.MAX_VALUE, "0x7F"),
                Arguments.of(Byte.MIN_VALUE, "0x80"),
                Arguments.of((byte) -1, "0xFF")
        );
    }

    @ParameterizedTest
    @MethodSource
    void toHexAndDecString(int input, String output) {
        String result = StringUtils.toHexAndDecString(input);
        assertThat(result).isEqualTo(output);
    }

    private static Stream<Arguments> toHexAndDecString() {
        return Stream.of(
                Arguments.of(0, "0x00 (0)"),
                Arguments.of(127, "0x7F (127)"),
                Arguments.of(255, "0xFF (255)"),
                Arguments.of(Integer.MIN_VALUE, "0x80000000 (-2147483648)"),
                Arguments.of(Integer.MAX_VALUE, "0x7FFFFFFF (2147483647)")
        );
    }

    @ParameterizedTest
    @MethodSource
    void byteArrayToHexString(byte[] input, String output) {
        String result = StringUtils.byteArrayToHexString(input);
        assertThat(result).isEqualTo(output);
    }

    private static Stream<Arguments> byteArrayToHexString() {
        return Stream.of(
                Arguments.of(null, "[]"),
                Arguments.of(new byte[0], "[]"),
                Arguments.of(new byte[]{10}, "[0x0A]"),
                Arguments.of(new byte[]{10, 0}, "[0x0A, 0x00]"),
                Arguments.of(new byte[]{0, Byte.MAX_VALUE, Byte.MIN_VALUE, -1}, "[0x00, 0x7F, 0x80, 0xFF]")
        );
    }
}
package at.elmecker.midiremap;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static at.elmecker.midiremap.Utils.OctaveAlignment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UtilsTest {

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -2, -1, 128, 129, Integer.MAX_VALUE})
    void checksInvalidMidiNoteToEnglishTwelveToneChromaticScaleValue(int note) {
        assertThatThrownBy(() ->
                Utils.midiNoteToEnglishTwelveToneChromaticScaleValue(note, OctaveAlignment.MIDDLE_C_AS_C3))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("midiNoteToEnglishTwelveToneChromaticScaleValue")
    void midiNoteToEnglishTwelveToneChromaticScaleValue(
            int midiNote, OctaveAlignment octaveAlignment, String expectedNote) {

        assertThat(Utils.midiNoteToEnglishTwelveToneChromaticScaleValue(midiNote, octaveAlignment))
                .isEqualTo(expectedNote);
    }

    private static Stream<Arguments> midiNoteToEnglishTwelveToneChromaticScaleValue() {
        return Stream.concat(middleC3(), middleC4());
    }

    private static Stream<Arguments> middleC3() {
        return Stream.of(
                Arguments.of(0, OctaveAlignment.MIDDLE_C_AS_C3, "C-2"),
                Arguments.of(1, OctaveAlignment.MIDDLE_C_AS_C3, "C#-2"),
                Arguments.of(2, OctaveAlignment.MIDDLE_C_AS_C3, "D-2"),
                Arguments.of(3, OctaveAlignment.MIDDLE_C_AS_C3, "D#-2"),
                Arguments.of(4, OctaveAlignment.MIDDLE_C_AS_C3, "E-2"),
                Arguments.of(5, OctaveAlignment.MIDDLE_C_AS_C3, "F-2"),
                Arguments.of(6, OctaveAlignment.MIDDLE_C_AS_C3, "F#-2"),
                Arguments.of(7, OctaveAlignment.MIDDLE_C_AS_C3, "G-2"),
                Arguments.of(8, OctaveAlignment.MIDDLE_C_AS_C3, "G#-2"),
                Arguments.of(9, OctaveAlignment.MIDDLE_C_AS_C3, "A-2"),
                Arguments.of(10, OctaveAlignment.MIDDLE_C_AS_C3, "A#-2"),
                Arguments.of(11, OctaveAlignment.MIDDLE_C_AS_C3, "B-2"),

                Arguments.of(12, OctaveAlignment.MIDDLE_C_AS_C3, "C-1"),
                Arguments.of(13, OctaveAlignment.MIDDLE_C_AS_C3, "C#-1"),
                Arguments.of(14, OctaveAlignment.MIDDLE_C_AS_C3, "D-1"),
                Arguments.of(15, OctaveAlignment.MIDDLE_C_AS_C3, "D#-1"),
                Arguments.of(16, OctaveAlignment.MIDDLE_C_AS_C3, "E-1"),
                Arguments.of(17, OctaveAlignment.MIDDLE_C_AS_C3, "F-1"),
                Arguments.of(18, OctaveAlignment.MIDDLE_C_AS_C3, "F#-1"),
                Arguments.of(19, OctaveAlignment.MIDDLE_C_AS_C3, "G-1"),
                Arguments.of(20, OctaveAlignment.MIDDLE_C_AS_C3, "G#-1"),
                Arguments.of(21, OctaveAlignment.MIDDLE_C_AS_C3, "A-1"),
                Arguments.of(22, OctaveAlignment.MIDDLE_C_AS_C3, "A#-1"),
                Arguments.of(23, OctaveAlignment.MIDDLE_C_AS_C3, "B-1"),

                Arguments.of(24, OctaveAlignment.MIDDLE_C_AS_C3, "C0"),
                Arguments.of(25, OctaveAlignment.MIDDLE_C_AS_C3, "C#0"),
                Arguments.of(26, OctaveAlignment.MIDDLE_C_AS_C3, "D0"),
                Arguments.of(27, OctaveAlignment.MIDDLE_C_AS_C3, "D#0"),
                Arguments.of(28, OctaveAlignment.MIDDLE_C_AS_C3, "E0"),
                Arguments.of(29, OctaveAlignment.MIDDLE_C_AS_C3, "F0"),
                Arguments.of(30, OctaveAlignment.MIDDLE_C_AS_C3, "F#0"),
                Arguments.of(31, OctaveAlignment.MIDDLE_C_AS_C3, "G0"),
                Arguments.of(32, OctaveAlignment.MIDDLE_C_AS_C3, "G#0"),
                Arguments.of(33, OctaveAlignment.MIDDLE_C_AS_C3, "A0"),
                Arguments.of(34, OctaveAlignment.MIDDLE_C_AS_C3, "A#0"),
                Arguments.of(35, OctaveAlignment.MIDDLE_C_AS_C3, "B0"),

                Arguments.of(36, OctaveAlignment.MIDDLE_C_AS_C3, "C1"),
                Arguments.of(37, OctaveAlignment.MIDDLE_C_AS_C3, "C#1"),
                Arguments.of(38, OctaveAlignment.MIDDLE_C_AS_C3, "D1"),
                Arguments.of(39, OctaveAlignment.MIDDLE_C_AS_C3, "D#1"),
                Arguments.of(40, OctaveAlignment.MIDDLE_C_AS_C3, "E1"),
                Arguments.of(41, OctaveAlignment.MIDDLE_C_AS_C3, "F1"),
                Arguments.of(42, OctaveAlignment.MIDDLE_C_AS_C3, "F#1"),
                Arguments.of(43, OctaveAlignment.MIDDLE_C_AS_C3, "G1"),
                Arguments.of(44, OctaveAlignment.MIDDLE_C_AS_C3, "G#1"),
                Arguments.of(45, OctaveAlignment.MIDDLE_C_AS_C3, "A1"),
                Arguments.of(46, OctaveAlignment.MIDDLE_C_AS_C3, "A#1"),
                Arguments.of(47, OctaveAlignment.MIDDLE_C_AS_C3, "B1"),

                Arguments.of(60, OctaveAlignment.MIDDLE_C_AS_C3, "C3"),

                Arguments.of(120, OctaveAlignment.MIDDLE_C_AS_C3, "C8"),
                Arguments.of(121, OctaveAlignment.MIDDLE_C_AS_C3, "C#8"),
                Arguments.of(122, OctaveAlignment.MIDDLE_C_AS_C3, "D8"),
                Arguments.of(123, OctaveAlignment.MIDDLE_C_AS_C3, "D#8"),
                Arguments.of(124, OctaveAlignment.MIDDLE_C_AS_C3, "E8"),
                Arguments.of(125, OctaveAlignment.MIDDLE_C_AS_C3, "F8"),
                Arguments.of(126, OctaveAlignment.MIDDLE_C_AS_C3, "F#8"),
                Arguments.of(127, OctaveAlignment.MIDDLE_C_AS_C3, "G8")
        );
    }

    private static Stream<Arguments> middleC4() {
        return Stream.of(
                Arguments.of(0, OctaveAlignment.MIDDLE_C_AS_C4, "C-1"),
                Arguments.of(1, OctaveAlignment.MIDDLE_C_AS_C4, "C#-1"),
                Arguments.of(2, OctaveAlignment.MIDDLE_C_AS_C4, "D-1"),
                Arguments.of(3, OctaveAlignment.MIDDLE_C_AS_C4, "D#-1"),
                Arguments.of(4, OctaveAlignment.MIDDLE_C_AS_C4, "E-1"),
                Arguments.of(5, OctaveAlignment.MIDDLE_C_AS_C4, "F-1"),
                Arguments.of(6, OctaveAlignment.MIDDLE_C_AS_C4, "F#-1"),
                Arguments.of(7, OctaveAlignment.MIDDLE_C_AS_C4, "G-1"),
                Arguments.of(8, OctaveAlignment.MIDDLE_C_AS_C4, "G#-1"),
                Arguments.of(9, OctaveAlignment.MIDDLE_C_AS_C4, "A-1"),
                Arguments.of(10, OctaveAlignment.MIDDLE_C_AS_C4, "A#-1"),
                Arguments.of(11, OctaveAlignment.MIDDLE_C_AS_C4, "B-1"),

                Arguments.of(12, OctaveAlignment.MIDDLE_C_AS_C4, "C0"),
                Arguments.of(13, OctaveAlignment.MIDDLE_C_AS_C4, "C#0"),
                Arguments.of(14, OctaveAlignment.MIDDLE_C_AS_C4, "D0"),
                Arguments.of(15, OctaveAlignment.MIDDLE_C_AS_C4, "D#0"),
                Arguments.of(16, OctaveAlignment.MIDDLE_C_AS_C4, "E0"),
                Arguments.of(17, OctaveAlignment.MIDDLE_C_AS_C4, "F0"),
                Arguments.of(18, OctaveAlignment.MIDDLE_C_AS_C4, "F#0"),
                Arguments.of(19, OctaveAlignment.MIDDLE_C_AS_C4, "G0"),
                Arguments.of(20, OctaveAlignment.MIDDLE_C_AS_C4, "G#0"),
                Arguments.of(21, OctaveAlignment.MIDDLE_C_AS_C4, "A0"),
                Arguments.of(22, OctaveAlignment.MIDDLE_C_AS_C4, "A#0"),
                Arguments.of(23, OctaveAlignment.MIDDLE_C_AS_C4, "B0"),

                Arguments.of(24, OctaveAlignment.MIDDLE_C_AS_C4, "C1"),
                Arguments.of(25, OctaveAlignment.MIDDLE_C_AS_C4, "C#1"),
                Arguments.of(26, OctaveAlignment.MIDDLE_C_AS_C4, "D1"),
                Arguments.of(27, OctaveAlignment.MIDDLE_C_AS_C4, "D#1"),
                Arguments.of(28, OctaveAlignment.MIDDLE_C_AS_C4, "E1"),
                Arguments.of(29, OctaveAlignment.MIDDLE_C_AS_C4, "F1"),
                Arguments.of(30, OctaveAlignment.MIDDLE_C_AS_C4, "F#1"),
                Arguments.of(31, OctaveAlignment.MIDDLE_C_AS_C4, "G1"),
                Arguments.of(32, OctaveAlignment.MIDDLE_C_AS_C4, "G#1"),
                Arguments.of(33, OctaveAlignment.MIDDLE_C_AS_C4, "A1"),
                Arguments.of(34, OctaveAlignment.MIDDLE_C_AS_C4, "A#1"),
                Arguments.of(35, OctaveAlignment.MIDDLE_C_AS_C4, "B1"),

                Arguments.of(36, OctaveAlignment.MIDDLE_C_AS_C4, "C2"),
                Arguments.of(37, OctaveAlignment.MIDDLE_C_AS_C4, "C#2"),
                Arguments.of(38, OctaveAlignment.MIDDLE_C_AS_C4, "D2"),
                Arguments.of(39, OctaveAlignment.MIDDLE_C_AS_C4, "D#2"),
                Arguments.of(40, OctaveAlignment.MIDDLE_C_AS_C4, "E2"),
                Arguments.of(41, OctaveAlignment.MIDDLE_C_AS_C4, "F2"),
                Arguments.of(42, OctaveAlignment.MIDDLE_C_AS_C4, "F#2"),
                Arguments.of(43, OctaveAlignment.MIDDLE_C_AS_C4, "G2"),
                Arguments.of(44, OctaveAlignment.MIDDLE_C_AS_C4, "G#2"),
                Arguments.of(45, OctaveAlignment.MIDDLE_C_AS_C4, "A2"),
                Arguments.of(46, OctaveAlignment.MIDDLE_C_AS_C4, "A#2"),
                Arguments.of(47, OctaveAlignment.MIDDLE_C_AS_C4, "B2"),

                Arguments.of(60, OctaveAlignment.MIDDLE_C_AS_C4, "C4"),

                Arguments.of(120, OctaveAlignment.MIDDLE_C_AS_C4, "C9"),
                Arguments.of(121, OctaveAlignment.MIDDLE_C_AS_C4, "C#9"),
                Arguments.of(122, OctaveAlignment.MIDDLE_C_AS_C4, "D9"),
                Arguments.of(123, OctaveAlignment.MIDDLE_C_AS_C4, "D#9"),
                Arguments.of(124, OctaveAlignment.MIDDLE_C_AS_C4, "E9"),
                Arguments.of(125, OctaveAlignment.MIDDLE_C_AS_C4, "F9"),
                Arguments.of(126, OctaveAlignment.MIDDLE_C_AS_C4, "F#9"),
                Arguments.of(127, OctaveAlignment.MIDDLE_C_AS_C4, "G9")
        );
    }
}
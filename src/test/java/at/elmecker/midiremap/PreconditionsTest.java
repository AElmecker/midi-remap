package at.elmecker.midiremap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.IntStream;

import static at.elmecker.midiremap.Preconditions.checkNote;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PreconditionsTest {

    @ParameterizedTest
    @MethodSource("checkValidMidiNote")
    void checkValidMidiNote(int midiNote) {
        assertThatCode(() -> checkNote("valid", midiNote)).doesNotThrowAnyException();
    }

    private static IntStream checkValidMidiNote() {
        return IntStream.rangeClosed(0, 127);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -2, -1, 128, 129, Integer.MAX_VALUE})
    void checkInvalidMidiNote(int midiNote) {
        assertThatThrownBy(() -> checkNote("invalid", midiNote))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void checkInvalidMidiNoteMessage() {
        assertThatThrownBy(() -> checkNote("test", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("test note -1 is not valid, must be in range of [0-127]");
    }
}
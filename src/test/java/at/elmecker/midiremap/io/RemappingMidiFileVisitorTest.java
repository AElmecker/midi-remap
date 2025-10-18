package at.elmecker.midiremap.io;

import at.elmecker.midiremap.definition.RemapDefinition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sound.midi.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class RemappingMidiFileVisitorTest {

    private static final RemapDefinition DEFINITION = RemapDefinition.builder()
            .addMapping(12, 100)
            .build();
    private final RemappingMidiFileVisitor visitor = new RemappingMidiFileVisitor(DEFINITION);

    @Test
    void startFile() {
        MidiFileFormat format = new MidiFileFormat(0, 1, 2, 3, 4);

        assertThatCode(() -> visitor.startFile("foo/bar", format)).doesNotThrowAnyException();
    }

    @Test
    void endFile() {
        assertThatCode(visitor::endFile).doesNotThrowAnyException();
    }

    @Test
    void startSequence() throws InvalidMidiDataException {
        Sequence sequence = new Sequence(Sequence.PPQ, 2);

        assertThatCode(() -> visitor.startSequence(sequence)).doesNotThrowAnyException();
    }

    @Test
    void endSequence() {
        assertThatCode(visitor::endSequence).doesNotThrowAnyException();
    }

    @Test
    void startTrack() throws InvalidMidiDataException {
        Track track = new Sequence(Sequence.PPQ, 2).createTrack();

        assertThatCode(() -> visitor.startTrack(track)).doesNotThrowAnyException();
    }

    @Test
    void endTrack() {
        assertThatCode(visitor::endTrack).doesNotThrowAnyException();
    }

    @Test
    void onEventOtherMessageTypeIsSkipped() {
        MidiEvent event = new MidiEvent(new MetaMessage(), 1L);

        assertThatCode(() -> visitor.onEvent(event)).doesNotThrowAnyException();
    }

    @Test
    void onEventShortMessageThrows() {
        MidiEvent event = new MidiEvent(new ThrowingShortMessage(), 1L);
        assertThatThrownBy(() -> visitor.onEvent(event))
                .isInstanceOf(RuntimeException.class)
                .hasCause(new InvalidMidiDataException("oh no"));
    }

    @ParameterizedTest
    @MethodSource("onEventShortMessage")
    void onEventShortMessage(ShortMessage message, ShortMessage expected) {
        visitor.onEvent(new MidiEvent(message, 1L));

        assertThat(message.getCommand()).isEqualTo(expected.getCommand());
        assertThat(message.getChannel()).isEqualTo(expected.getChannel());
        assertThat(message.getData1()).isEqualTo(expected.getData1());
        assertThat(message.getData2()).isEqualTo(expected.getData2());
    }

    private static Stream<Arguments> onEventShortMessage() throws InvalidMidiDataException {
        return Stream.of(
                Arguments.of(
                        new ShortMessage(ShortMessage.NOTE_ON, 4, 12, 123),
                        new ShortMessage(ShortMessage.NOTE_ON, 4, 100, 123)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.NOTE_OFF, 4, 12, 123),
                        new ShortMessage(ShortMessage.NOTE_OFF, 4, 100, 123)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.POLY_PRESSURE, 4, 12, 123),
                        new ShortMessage(ShortMessage.POLY_PRESSURE, 4, 100, 123)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.NOTE_ON, 15, 45, 120),
                        new ShortMessage(ShortMessage.NOTE_ON, 15, 45, 120)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.NOTE_OFF, 15, 45, 120),
                        new ShortMessage(ShortMessage.NOTE_OFF, 15, 45, 120)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.POLY_PRESSURE, 15, 45, 120),
                        new ShortMessage(ShortMessage.POLY_PRESSURE, 15, 45, 120)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.CHANNEL_PRESSURE, 4, 12, 123),
                        new ShortMessage(ShortMessage.CHANNEL_PRESSURE, 4, 12, 123)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.CONTROL_CHANGE, 4, 12, 123),
                        new ShortMessage(ShortMessage.CONTROL_CHANGE, 4, 12, 123)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.PITCH_BEND, 4, 12, 123),
                        new ShortMessage(ShortMessage.PITCH_BEND, 4, 12, 123)
                ),
                Arguments.of(
                        new ShortMessage(ShortMessage.PROGRAM_CHANGE, 4, 12, 123),
                        new ShortMessage(ShortMessage.PROGRAM_CHANGE, 4, 12, 123)
                )
        );
    }
}
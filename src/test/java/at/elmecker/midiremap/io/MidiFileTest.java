package at.elmecker.midiremap.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sound.midi.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MidiFileTest {

    private static final MidiFileFormat FORMAT = new MidiFileFormat(0, 1, 2, 3, 4);
    private static final Sequence SEQUENCE;

    static {
        try {
            SEQUENCE = new Sequence(Sequence.PPQ, 1);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    void invalidParameters(String filePath, MidiFileFormat format, Sequence sequence) {
        assertThatThrownBy(() -> new MidiFile(filePath, format, sequence)).isInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> invalidParameters() {
        return Stream.of(
                Arguments.of(null, FORMAT, SEQUENCE),
                Arguments.of("foo/bar", null, SEQUENCE),
                Arguments.of("foo/bar", FORMAT, null)
        );
    }

    @Test
    void acceptNoTracks() {
        RecordingMidiFileVisitor visitor = new RecordingMidiFileVisitor();
        MidiFile file = new MidiFile("foo/bar", FORMAT, SEQUENCE);

        file.accept(visitor);

        assertThat(visitor.methodCallOrder()).containsExactly(
                "startFile",
                "startSequence",
                "endSequence",
                "endFile"
        );

        assertThat(visitor.fileStarts()).containsExactly(new RecordingMidiFileVisitor.FileStarts("foo/bar", FORMAT));
        assertThat(visitor.sequences()).containsExactly(SEQUENCE);
    }

    @Test
    void accept() throws InvalidMidiDataException {
        RecordingMidiFileVisitor visitor = new RecordingMidiFileVisitor();

        Sequence sequence = new Sequence(Sequence.PPQ, 1);
        Track trackOne = sequence.createTrack();
        MidiEvent messageOne = new MidiEvent(new MetaMessage(), 1L);
        MidiEvent messageTwo = new MidiEvent(new MetaMessage(), 2L);
        trackOne.add(messageOne);
        trackOne.add(messageTwo);

        Track trackTwo = sequence.createTrack();
        MidiEvent messageThree = new MidiEvent(new MetaMessage(), 3L);
        trackTwo.add(messageThree);

        MidiFile file = new MidiFile("foo/bar", FORMAT, sequence);

        file.accept(visitor);

        assertThat(visitor.methodCallOrder()).containsExactly(
                "startFile",
                "startSequence",

                "startTrack",
                "onEvent",
                "onEvent",
                "onEvent", // EndOfTrack event is always added (internal logic of Track)
                "endTrack",

                "startTrack",
                "onEvent",
                "onEvent", // EndOfTrack event is always added (internal logic of Track)
                "endTrack",

                "endSequence",
                "endFile"
        );

        assertThat(visitor.fileStarts()).containsExactly(new RecordingMidiFileVisitor.FileStarts("foo/bar", FORMAT));
        assertThat(visitor.sequences()).containsExactly(sequence);
        assertThat(visitor.tracks()).containsExactly(trackOne, trackTwo);
        assertThat(visitor.events()).containsExactly(
                messageOne, messageTwo, trackOne.get(trackOne.size() - 1),
                messageThree, trackTwo.get(trackTwo.size() - 1)
        );
    }
}
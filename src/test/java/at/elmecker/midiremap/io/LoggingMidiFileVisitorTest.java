package at.elmecker.midiremap.io;

import org.junit.jupiter.api.Test;

import javax.sound.midi.*;

import static org.assertj.core.api.Assertions.assertThatCode;

class LoggingMidiFileVisitorTest {

    private final LoggingMidiFileVisitor visitor = new LoggingMidiFileVisitor();

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
    void onEvent() {
        MidiEvent event = new MidiEvent(new MetaMessage(), 1L);

        assertThatCode(() -> visitor.onEvent(event)).doesNotThrowAnyException();
    }
}
package at.elmecker.midiremap.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class CopyingMidiFileVisitorTest {

    private RecordingMidiWriter writer;
    private CopyingMidiFileVisitor visitor;

    @BeforeEach
    void beforeEach() {
        writer = new RecordingMidiWriter();
        visitor = new CopyingMidiFileVisitor(writer);
    }

    @Test
    void writeOnEndOfFile() throws InvalidMidiDataException {
        Sequence sequence = new Sequence(Sequence.PPQ, 1);
        Sequence sequence2 = new Sequence(Sequence.SMPTE_24, 2);

        visitor.startFile("foo/bar", new MidiFileFormat(0, 1, 2, 3, 4));
        visitor.startSequence(sequence);
        visitor.endFile();

        assertThat(writer.sequence()).isEqualTo(sequence);
        assertThat(writer.fileType()).isEqualTo(0);

        visitor.startFile("foo/bar", new MidiFileFormat(1, 1, 2, 3, 4));
        visitor.endFile();

        assertThat(writer.fileType()).isEqualTo(1);
        assertThat(writer.sequence()).isNull();

        visitor.startSequence(sequence2);
        visitor.endFile();

        assertThat(writer.fileType()).isEqualTo(1);
        assertThat(writer.sequence()).isEqualTo(sequence2);
    }

    @Test
    void writerThrows() {
        ThrowingMidiWriter throwingWriter = new ThrowingMidiWriter();
        CopyingMidiFileVisitor copyingMidiFileVisitor = new CopyingMidiFileVisitor(throwingWriter);

        assertThatCode(copyingMidiFileVisitor::endFile).doesNotThrowAnyException();
    }

    @Test
    void startFile() {
        MidiFileFormat format = new MidiFileFormat(0, 1, 2, 3, 4);

        assertThatCode(() -> visitor.startFile("foo/bar", format)).doesNotThrowAnyException();
    }

    @Test
    void endFile() {
        assertThatCode(visitor::endFile).doesNotThrowAnyException();

        assertThat(writer.sequence()).isNull();
        assertThat(writer.fileType()).isEqualTo(-1);
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
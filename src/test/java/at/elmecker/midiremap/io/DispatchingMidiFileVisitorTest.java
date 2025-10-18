package at.elmecker.midiremap.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.midi.*;

import static org.assertj.core.api.Assertions.assertThat;

class DispatchingMidiFileVisitorTest {

    private RecordingMidiFileVisitor recorderOne;
    private RecordingMidiFileVisitor recorderTwo;
    private ThrowingMidiFileVisitor throwingRecorder;

    @BeforeEach
    void beforeEach() {
        recorderOne = new RecordingMidiFileVisitor();
        recorderTwo = new RecordingMidiFileVisitor();
        throwingRecorder = new ThrowingMidiFileVisitor(new RuntimeException("oh no"));
    }

    @Test
    void startFile() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);
        MidiFileFormat format = new MidiFileFormat(0, 1, 2, 3, 4);

        visitor.startFile("foo/bar", format);

        assertThat(recorderOne.methodCallOrder()).containsExactly("startFile");
        assertThat(recorderOne.fileStarts()).containsExactly(
                new RecordingMidiFileVisitor.FileStarts("foo/bar", format)
        );

        assertThat(recorderTwo.methodCallOrder()).containsExactly("startFile");
        assertThat(recorderTwo.fileStarts()).containsExactly(
                new RecordingMidiFileVisitor.FileStarts("foo/bar", format)
        );
    }

    @Test
    void endFile() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);

        visitor.endFile();

        assertThat(recorderOne.methodCallOrder()).containsExactly("endFile");
        assertThat(recorderTwo.methodCallOrder()).containsExactly("endFile");
    }

    @Test
    void startSequence() throws InvalidMidiDataException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);
        Sequence sequence = new Sequence(Sequence.PPQ, 2);

        visitor.startSequence(sequence);

        assertThat(recorderOne.methodCallOrder()).containsExactly("startSequence");
        assertThat(recorderOne.sequences()).containsExactly(sequence);

        assertThat(recorderTwo.methodCallOrder()).containsExactly("startSequence");
        assertThat(recorderTwo.sequences()).containsExactly(sequence);
    }

    @Test
    void endSequence() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);

        visitor.endSequence();

        assertThat(recorderOne.methodCallOrder()).containsExactly("endSequence");
        assertThat(recorderTwo.methodCallOrder()).containsExactly("endSequence");
    }

    @Test
    void startTrack() throws InvalidMidiDataException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);
        Track track = new Sequence(Sequence.PPQ, 2).createTrack();

        visitor.startTrack(track);

        assertThat(recorderOne.methodCallOrder()).containsExactly("startTrack");
        assertThat(recorderOne.tracks()).containsExactly(track);

        assertThat(recorderTwo.methodCallOrder()).containsExactly("startTrack");
        assertThat(recorderTwo.tracks()).containsExactly(track);
    }

    @Test
    void endTrack() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);

        visitor.endTrack();

        assertThat(recorderOne.methodCallOrder()).containsExactly("endTrack");
        assertThat(recorderTwo.methodCallOrder()).containsExactly("endTrack");
    }

    @Test
    void onEvent() throws InvalidMidiDataException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, recorderTwo);
        MidiEvent event = new MidiEvent(new ShortMessage(0xFF), 1L);

        visitor.onEvent(event);

        assertThat(recorderOne.methodCallOrder()).containsExactly("onEvent");
        assertThat(recorderOne.events()).containsExactly(event);

        assertThat(recorderTwo.methodCallOrder()).containsExactly("onEvent");
        assertThat(recorderTwo.events()).containsExactly(event);
    }

    @Test
    void startFileContinuesAfterDelegateThrowsException() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);
        MidiFileFormat format = new MidiFileFormat(0, 1, 2, 3, 4);

        visitor.startFile("foo/bar", format);

        assertThat(recorderOne.methodCallOrder()).containsExactly("startFile");
        assertThat(recorderOne.fileStarts()).containsExactly(
                new RecordingMidiFileVisitor.FileStarts("foo/bar", format)
        );

        assertThat(throwingRecorder.methodCallOrder()).containsExactly("startFile");
        assertThat(throwingRecorder.fileStarts()).containsExactly(
                new RecordingMidiFileVisitor.FileStarts("foo/bar", format)
        );

        assertThat(recorderTwo.methodCallOrder()).containsExactly("startFile");
        assertThat(recorderTwo.fileStarts()).containsExactly(
                new RecordingMidiFileVisitor.FileStarts("foo/bar", format)
        );
    }

    @Test
    void endFileContinuesAfterDelegateThrowsException() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);

        visitor.endFile();

        assertThat(recorderOne.methodCallOrder()).containsExactly("endFile");
        assertThat(throwingRecorder.methodCallOrder()).containsExactly("endFile");
        assertThat(recorderTwo.methodCallOrder()).containsExactly("endFile");
    }

    @Test
    void startSequenceContinuesAfterDelegateThrowsException() throws InvalidMidiDataException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);
        Sequence sequence = new Sequence(Sequence.PPQ, 2);

        visitor.startSequence(sequence);

        assertThat(recorderOne.methodCallOrder()).containsExactly("startSequence");
        assertThat(recorderOne.sequences()).containsExactly(sequence);

        assertThat(throwingRecorder.methodCallOrder()).containsExactly("startSequence");
        assertThat(throwingRecorder.sequences()).containsExactly(sequence);

        assertThat(recorderTwo.methodCallOrder()).containsExactly("startSequence");
        assertThat(recorderTwo.sequences()).containsExactly(sequence);
    }

    @Test
    void endSequenceContinuesAfterDelegateThrowsException() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);

        visitor.endSequence();

        assertThat(recorderOne.methodCallOrder()).containsExactly("endSequence");
        assertThat(throwingRecorder.methodCallOrder()).containsExactly("endSequence");
        assertThat(recorderTwo.methodCallOrder()).containsExactly("endSequence");
    }

    @Test
    void startTrackContinuesAfterDelegateThrowsException() throws InvalidMidiDataException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);
        Track track = new Sequence(Sequence.PPQ, 2).createTrack();

        visitor.startTrack(track);

        assertThat(recorderOne.methodCallOrder()).containsExactly("startTrack");
        assertThat(recorderOne.tracks()).containsExactly(track);

        assertThat(throwingRecorder.methodCallOrder()).containsExactly("startTrack");
        assertThat(throwingRecorder.tracks()).containsExactly(track);

        assertThat(recorderTwo.methodCallOrder()).containsExactly("startTrack");
        assertThat(recorderTwo.tracks()).containsExactly(track);
    }

    @Test
    void endTrackContinuesAfterDelegateThrowsException() {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);

        visitor.endTrack();

        assertThat(recorderOne.methodCallOrder()).containsExactly("endTrack");
        assertThat(throwingRecorder.methodCallOrder()).containsExactly("endTrack");
        assertThat(recorderTwo.methodCallOrder()).containsExactly("endTrack");
    }

    @Test
    void onEventContinuesAfterDelegateThrowsException() throws InvalidMidiDataException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(recorderOne, throwingRecorder, recorderTwo);
        MidiEvent event = new MidiEvent(new ShortMessage(0xFF), 1L);

        visitor.onEvent(event);

        assertThat(recorderOne.methodCallOrder()).containsExactly("onEvent");
        assertThat(recorderOne.events()).containsExactly(event);

        assertThat(throwingRecorder.methodCallOrder()).containsExactly("onEvent");
        assertThat(throwingRecorder.events()).containsExactly(event);

        assertThat(recorderTwo.methodCallOrder()).containsExactly("onEvent");
        assertThat(recorderTwo.events()).containsExactly(event);
    }
}
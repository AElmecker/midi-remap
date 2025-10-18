package at.elmecker.midiremap.io;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import static java.util.Objects.requireNonNull;

public class ThrowingMidiFileVisitor extends RecordingMidiFileVisitor {

    private final RuntimeException exception;

    public ThrowingMidiFileVisitor(RuntimeException exception) {
        this.exception = requireNonNull(exception);
    }

    @Override
    public void startFile(String absoluteFilePath, MidiFileFormat format) {
        super.startFile(absoluteFilePath, format);
        throw exception;
    }

    @Override
    public void startSequence(Sequence sequence) {
        super.startSequence(sequence);
        throw exception;
    }

    @Override
    public void endSequence() {
        super.endSequence();
        throw exception;
    }

    @Override
    public void startTrack(Track track) {
        super.startTrack(track);
        throw exception;
    }

    @Override
    public void endTrack() {
        super.endTrack();
        throw exception;
    }

    @Override
    public void onEvent(MidiEvent event) {
        super.onEvent(event);
        throw exception;
    }

    @Override
    public void endFile() {
        super.endFile();
        throw exception;
    }
}

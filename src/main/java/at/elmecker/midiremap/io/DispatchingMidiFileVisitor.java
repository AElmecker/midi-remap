package at.elmecker.midiremap.io;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public final class DispatchingMidiFileVisitor implements MidiFileVisitor {

    private final List<MidiFileVisitor> delegates;

    public DispatchingMidiFileVisitor(MidiFileVisitor... delegates) {
        this(List.of(delegates));
    }

    public DispatchingMidiFileVisitor(List<MidiFileVisitor> delegates) {
        this.delegates = requireNonNull(delegates);
    }

    @Override
    public void startFile(String absoluteFilePath, MidiFileFormat format) {
        dispatch(delegate -> delegate.startFile(absoluteFilePath, format));
    }

    @Override
    public void startSequence(Sequence sequence) {
        dispatch(delegate -> delegate.startSequence(sequence));
    }

    @Override
    public void endSequence() {
        dispatch(MidiFileVisitor::endSequence);
    }

    @Override
    public void startTrack(Track track) {
        dispatch(delegate -> delegate.startTrack(track));
    }

    @Override
    public void endTrack() {
        dispatch(MidiFileVisitor::endTrack);
    }

    @Override
    public void onEvent(MidiEvent event) {
        dispatch(delegate -> delegate.onEvent(event));
    }

    @Override
    public void endFile() {
        dispatch(MidiFileVisitor::endFile);
    }

    private void dispatch(Consumer<MidiFileVisitor> action) {
        for (MidiFileVisitor delegate : delegates) {
            try {
                action.accept(delegate);
            } catch (RuntimeException e) {
                System.err.println("error dispatching to " + delegate.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }
}

package at.elmecker.midiremap.io;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.List;

public class RecordingMidiFileVisitor implements MidiFileVisitor {

    private final List<String> methodCallOrder = new ArrayList<>();
    private final List<FileStarts> fileStarts = new ArrayList<>();
    private final List<Sequence> sequences = new ArrayList<>();
    private final List<Track> tracks = new ArrayList<>();
    private final List<MidiEvent> events = new ArrayList<>();

    public record FileStarts(String absoluteFilePath, MidiFileFormat format) {
    }

    @Override
    public void startFile(String absoluteFilePath, MidiFileFormat format) {
        methodCallOrder.add("startFile");
        fileStarts.add(new FileStarts(absoluteFilePath, format));
    }

    @Override
    public void startSequence(Sequence sequence) {
        methodCallOrder.add("startSequence");
        sequences.add(sequence);
    }

    @Override
    public void endSequence() {
        methodCallOrder.add("endSequence");
    }

    @Override
    public void startTrack(Track track) {
        methodCallOrder.add("startTrack");
        tracks.add(track);
    }

    @Override
    public void endTrack() {
        methodCallOrder.add("endTrack");
    }

    @Override
    public void onEvent(MidiEvent event) {
        methodCallOrder.add("onEvent");
        events.add(event);
    }

    @Override
    public void endFile() {
        methodCallOrder.add("endFile");
    }

    public List<FileStarts> fileStarts() {
        return fileStarts;
    }

    public List<Sequence> sequences() {
        return sequences;
    }

    public List<Track> tracks() {
        return tracks;
    }

    public List<MidiEvent> events() {
        return events;
    }

    public List<String> methodCallOrder() {
        return methodCallOrder;
    }
}

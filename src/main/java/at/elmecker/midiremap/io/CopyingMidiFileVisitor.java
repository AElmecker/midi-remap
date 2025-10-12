package at.elmecker.midiremap.io;

import javax.sound.midi.*;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class CopyingMidiFileVisitor implements MidiFileVisitor {

    private final Path path;
    private boolean error = false;
    private Sequence sequence = null;
    private Track track = null;
    private int fileType = -1;

    public CopyingMidiFileVisitor(Path path) {
        this.path = requireNonNull(path);
    }

    @Override
    public void startFile(String absoluteFilePath, MidiFileFormat format) {
        error = false;
        sequence = null;
        track = null;
        fileType = format.getType();
    }

    @Override
    public void startSequence(Sequence sequence) {
        try {
            this.sequence = new Sequence(sequence.getDivisionType(), sequence.getResolution());
        } catch (InvalidMidiDataException e) {
            System.err.println("Could not start sequence for copying: " + e.getMessage());
            error = true;
        }
    }

    @Override
    public void endSequence() {
    }

    @Override
    public void startTrack(Track track) {
        if (error) {
            return;
        }

        this.track = sequence.createTrack();
    }

    @Override
    public void endTrack() {

    }

    @Override
    public void onEvent(MidiEvent event) {
        if (error) {
            return;
        }

        track.add(event);
    }

    @Override
    public void endFile() {
        if (error) {
            return;
        }

        try (OutputStream outputStream = Files.newOutputStream(path)) {
            MidiSystem.write(sequence, fileType, outputStream);
        } catch (Exception e) {
            System.err.println("Could not write to file: " + e.getMessage());
        }
    }
}

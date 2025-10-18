package at.elmecker.midiremap.io;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.IOException;
import java.io.OutputStream;

import static java.util.Objects.requireNonNull;

public class MidiSystemFileWriter implements MidiWriter {

    private final OutputStreamSupplier outputStreamSupplier;

    public MidiSystemFileWriter(OutputStreamSupplier outputStreamSupplier) {
        this.outputStreamSupplier = requireNonNull(outputStreamSupplier);
    }

    @Override
    public void write(Sequence sequence, int fileType) throws IOException {
        try (OutputStream outputStream = outputStreamSupplier.get()) {
            MidiSystem.write(sequence, fileType, outputStream);
        }
    }

    @FunctionalInterface
    public interface OutputStreamSupplier {

        OutputStream get() throws IOException;
    }
}

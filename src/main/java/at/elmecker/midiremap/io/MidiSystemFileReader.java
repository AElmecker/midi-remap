package at.elmecker.midiremap.io;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MidiSystemFileReader implements MidiReader {

    @Override
    public MidiFile read(Path inputPath) throws InvalidMidiDataException, IOException {
        File file = inputPath.toFile();
        MidiFileFormat fileFormat = MidiSystem.getMidiFileFormat(file);
        Sequence sequence = MidiSystem.getSequence(file);

        return new MidiFile(inputPath.toString(), fileFormat, sequence);
    }
}

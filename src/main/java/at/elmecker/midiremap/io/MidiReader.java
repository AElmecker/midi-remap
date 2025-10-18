package at.elmecker.midiremap.io;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.nio.file.Path;

public interface MidiReader {

    MidiFile read(Path inputPath) throws InvalidMidiDataException, IOException;
}

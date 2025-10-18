package at.elmecker.midiremap.io;

import javax.sound.midi.Sequence;
import java.io.IOException;

public interface MidiWriter {

    void write(Sequence sequence, int fileType) throws IOException;
}

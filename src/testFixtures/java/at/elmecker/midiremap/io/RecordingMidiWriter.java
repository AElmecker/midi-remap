package at.elmecker.midiremap.io;

import javax.sound.midi.Sequence;
import java.io.IOException;

public class RecordingMidiWriter implements MidiWriter {

    private Sequence sequence = null;
    private int fileType = -1;

    @Override
    public void write(Sequence sequence, int fileType) throws IOException {
        this.sequence = sequence;
        this.fileType = fileType;
    }

    public Sequence sequence() {
        return sequence;
    }

    public int fileType() {
        return fileType;
    }
}

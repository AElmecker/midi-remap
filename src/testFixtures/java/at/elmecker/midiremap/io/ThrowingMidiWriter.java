package at.elmecker.midiremap.io;

import javax.sound.midi.Sequence;
import java.io.IOException;

public class ThrowingMidiWriter extends RecordingMidiWriter {

    @Override
    public void write(Sequence sequence, int fileType) throws IOException {
        super.write(sequence, fileType);
        throw new IOException("oh no");
    }
}

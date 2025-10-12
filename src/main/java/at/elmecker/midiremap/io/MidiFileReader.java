package at.elmecker.midiremap.io;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MidiFileReader {

    public void read(String filePath, List<MidiFileVisitor> visitors) throws InvalidMidiDataException, IOException {
        DispatchingMidiFileVisitor visitor = new DispatchingMidiFileVisitor(visitors);
        File file = new File(filePath);

        MidiFileFormat format = MidiSystem.getMidiFileFormat(file);
        visitor.startFile(file.getAbsolutePath(), format);

        Sequence sequence = MidiSystem.getSequence(file);
        visitor.startSequence(sequence);

        for (Track track : sequence.getTracks()) {
            visitor.startTrack(track);

            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                visitor.onEvent(event);
            }
            visitor.endTrack();
        }

        visitor.endSequence();
        visitor.endFile();
    }
}

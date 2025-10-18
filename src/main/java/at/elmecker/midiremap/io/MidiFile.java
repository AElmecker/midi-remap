package at.elmecker.midiremap.io;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import static java.util.Objects.requireNonNull;

public record MidiFile(String filePath, MidiFileFormat format, Sequence sequence) {

    public MidiFile {
        requireNonNull(filePath);
        requireNonNull(format);
        requireNonNull(sequence);
    }

    public void accept(MidiFileVisitor visitor) {
        visitor.startFile(filePath, format);
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

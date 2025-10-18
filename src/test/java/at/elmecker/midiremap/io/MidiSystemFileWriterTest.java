package at.elmecker.midiremap.io;

import org.junit.jupiter.api.Test;

import javax.sound.midi.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MidiSystemFileWriterTest {

    @Test
    void write() throws IOException, InvalidMidiDataException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MidiSystemFileWriter writer = new MidiSystemFileWriter(() -> stream);

        Sequence sequence = new Sequence(Sequence.SMPTE_25, 41);
        Track track = sequence.createTrack();
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 1, 127), 1));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 0, 2, 127), 2));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 0, 1, 127), 3));

        track = sequence.createTrack();
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, 15, 126, 127), 1));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, 15, 126, 127), 2));

        writer.write(sequence, 1);

        assertThat(stream.toByteArray()).isEqualTo(new byte[]{
                // 4D 54 68 64 magic bytes MThd
                77, 84, 104, 100,
                // 00 00 00 06 chunk size
                0, 0, 0, 6,
                // 00 01       format
                0, 1,
                // 00 02       track count
                0, 2,
                // E7          division type
                -25,
                // 29          resolution
                41,

                // 4D 54 72 6B magic bytes MTrk
                77, 84, 114, 107,
                // 00 00 00 0F chunk size
                0, 0, 0, 15,
                // 01          tick delta
                1,
                // 90 01 7F    message: status + data byte 1 + data byte 2
                -112, 1, 127,
                // 01          tick delta
                1,
                // 02 7F       message: data byte 1 + data byte 2 (status byte omitted as previous message had the same)
                2, 127,
                // 01          tick delta
                1,
                // 90 01 7F    message: status + data byte 1 + data byte 2
                -128, 1, 127,
                // 01          tick delta
                0,
                // FF 2F 00    end of track
                -1, 47, 0,

                // 4D 54 72 6B magic bytes MTrk
                77, 84, 114, 107,
                // 00 00 00 0F chunk size
                0, 0, 0, 12,
                // 01          tick delta
                1,
                // 90 01 7F    message: status + data byte 1 + data byte 2
                -97, 126, 127,
                // 01          tick delta
                1,
                // 90 01 7F    message: status + data byte 1 + data byte 2
                -113, 126, 127,
                // 01          tick delta
                0,
                // FF 2F 00    end of track
                -1, 47, 0
        });
    }
}
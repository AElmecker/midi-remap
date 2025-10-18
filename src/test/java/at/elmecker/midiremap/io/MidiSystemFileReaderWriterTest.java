package at.elmecker.midiremap.io;

import at.elmecker.midiremap.ClasspathUtils;
import org.junit.jupiter.api.Test;

import javax.sound.midi.InvalidMidiDataException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MidiSystemFileReaderWriterTest {

    @Test
    void readWrite() throws InvalidMidiDataException, IOException {
        Path path = ClasspathUtils.pathOfResource("midi/simple.midi");
        MidiSystemFileReader reader = new MidiSystemFileReader();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MidiSystemFileWriter writer = new MidiSystemFileWriter(() -> stream);

        MidiFile file = reader.read(path);
        writer.write(file.sequence(), file.format().getType());

        assertThat(stream.toByteArray()).isEqualTo(Files.readAllBytes(path));
    }
}

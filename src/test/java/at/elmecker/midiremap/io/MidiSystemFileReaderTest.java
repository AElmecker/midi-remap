package at.elmecker.midiremap.io;

import at.elmecker.midiremap.ClasspathUtils;
import org.junit.jupiter.api.Test;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class MidiSystemFileReaderTest {

    @Test
    void read() throws InvalidMidiDataException, IOException {
        Path path = ClasspathUtils.pathOfResource("midi/simple.midi");
        MidiSystemFileReader reader = new MidiSystemFileReader();

        MidiFile file = reader.read(path);

        assertThat(file.filePath()).endsWith("midi/simple.midi");
        assertThat(file.format().getType()).isEqualTo(1);
        assertThat(file.sequence().getTracks()).hasSize(2);
    }
}
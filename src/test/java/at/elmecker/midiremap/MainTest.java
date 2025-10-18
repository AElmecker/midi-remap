package at.elmecker.midiremap;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static at.elmecker.midiremap.ClasspathUtils.pathOfResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MainTest {

    @ParameterizedTest
    @MethodSource("tooFewArguments")
    void tooFewArguments(String[] args) {
        assertThatThrownBy(() -> Main.main(args)).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> tooFewArguments() {
        return Stream.of(
                Arguments.of((Object) new String[0]),
                Arguments.of((Object) new String[]{"foo"}),
                Arguments.of((Object) new String[]{"foo", "bar"})
        );
    }

    @ParameterizedTest
    @MethodSource("run")
    void run(Path midiIn, Path definitionIn, byte[] expectedBytes) throws InvalidMidiDataException, IOException {
        Path midiOut = Files.createTempFile("midiremap-", ".midi");

        Main.main(new String[]{
                toAbsoluteString(midiIn),
                toAbsoluteString(midiOut),
                toAbsoluteString(definitionIn)
        });

        assertThat(Files.readAllBytes(midiOut)).isEqualTo(expectedBytes);
    }

    private static Stream<Arguments> run() throws IOException {
        return Stream.of(
                Arguments.of(
                        pathOfResource("midi/simple_main.midi"),
                        pathOfResource("definition/empty.csv"),
                        Files.readAllBytes(pathOfResource("midi/simple_main.midi"))
                ),
                Arguments.of(
                        pathOfResource("midi/simple_main.midi"),
                        pathOfResource("definition/valid.csv"),
                        Files.readAllBytes(pathOfResource("midi/expected_main.midi"))
                ),
                Arguments.of(
                        pathOfResource("midi/simple.midi"),
                        pathOfResource("definition/valid.csv"),
                        Files.readAllBytes(pathOfResource("midi/simple.midi"))
                )
        );
    }

    private static String toAbsoluteString(Path path) {
        return path.toAbsolutePath().toString();
    }
}
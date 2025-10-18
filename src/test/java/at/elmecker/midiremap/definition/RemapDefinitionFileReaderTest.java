package at.elmecker.midiremap.definition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static at.elmecker.midiremap.ClasspathUtils.pathOfResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RemapDefinitionFileReaderTest {

    private final RemapDefinitionFileReader reader = new RemapDefinitionFileReader();

    @Test
    void emptyFile() throws IOException {
        RemapDefinition definition = reader.read(pathOfResource("definition/empty.csv"));

        for (int note = 0; note < 127; note++) {
            assertThat(definition.mappedNote(note)).isEqualTo(note);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"definition/valid.csv", "definition/valid_with_empty_lines.csv"})
    void validFile(String path) throws IOException {
        RemapDefinition definition = reader.read(pathOfResource(path));

        assertThat(definition.mappedNote(11)).isEqualTo(55);
        assertThat(definition.mappedNote(22)).isEqualTo(66);
        assertThat(definition.mappedNote(60)).isEqualTo(60);
        assertThat(definition.mappedNote(61)).isEqualTo(61);
        assertThat(definition.mappedNote(10)).isEqualTo(111);
    }

    @ParameterizedTest
    @MethodSource("invalidFile")
    void invalidFile(String path, Class<Throwable> expectedThrowableInstance) {
        Path resourcePath = pathOfResource(path);
        assertThatThrownBy(() -> reader.read(resourcePath)).isInstanceOf(expectedThrowableInstance);
    }

    private static Stream<Arguments> invalidFile() {
        return Stream.of(
                Arguments.of("definition/invalid_origin_number.csv", NumberFormatException.class),
                Arguments.of("definition/invalid_origin_note.csv", IllegalArgumentException.class),
                Arguments.of("definition/invalid_destination_number.csv", NumberFormatException.class),
                Arguments.of("definition/invalid_destination_note.csv", IllegalArgumentException.class)
        );
    }

}
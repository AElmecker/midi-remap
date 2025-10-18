package at.elmecker.midiremap.definition;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RemapDefinitionTest {

    @Test
    void buildDefault() {
        RemapDefinition definition = RemapDefinition.builder().build();

        for (int note = 0; note < 127; note++) {
            assertThat(definition.mappedNote(note)).isEqualTo(note);
        }
    }

    @Test
    void addMapping() {
        RemapDefinition definition = RemapDefinition.builder()
                .addMapping(10, 12)
                .addMapping(10, 127)
                .addMapping(12, 99)
                .addMapping(10, 1)
                .addMapping(13, 1)
                .addMapping(100, 100)
                .build();

        assertThat(definition.mappedNote(10)).isEqualTo(1);
        assertThat(definition.mappedNote(12)).isEqualTo(99);
        assertThat(definition.mappedNote(13)).isEqualTo(1);
        assertThat(definition.mappedNote(100)).isEqualTo(100);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -2, -1, 128, 129, Integer.MAX_VALUE})
    void getInvalidMappedNote(int note) {
        RemapDefinition definition = RemapDefinition.builder().build();

        assertThatThrownBy(() -> definition.mappedNote(note))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -2, -1, 128, 129, Integer.MAX_VALUE})
    void checksOriginNoteBounds(int note) {
        RemapDefinition.Builder builder = RemapDefinition.builder();

        assertThatThrownBy(() -> builder.addMapping(note, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -2, -1, 128, 129, Integer.MAX_VALUE})
    void checksDestinationNoteBounds(int note) {

        RemapDefinition.Builder builder = RemapDefinition.builder();

        assertThatThrownBy(() -> builder.addMapping(0, note))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
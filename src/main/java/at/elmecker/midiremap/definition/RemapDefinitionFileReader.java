package at.elmecker.midiremap.definition;

import at.elmecker.midiremap.Preconditions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Expects a CSV file with comma ({@code ,}) as value delimiter and UTF-8 as charset.<br/>
 * Every line is interpreted (No headers in file).<br/>
 * MIDI Notes which have no mapping defined in the file are treated as {@code destination = origin}.
 * <p>
 * The columns are defined as:
 * <ul>
 *     <li>{@code Origin MIDI Note} - integer between 0 - 127 (e.g. valid MIDI note)</li>
 *     <li>{@code Origin Description} - any string not containing ({@code ,})</li>
 *     <li>{@code Destination MIDI Note} - integer between 0 - 127 (e.g. valid MIDI note) or blank (which translates to {@code destination = origin}</li>)
 *     <li>{@code Destination Description} - any string not containing ({@code ,})</li>
 * </ul>
 */
public class RemapDefinitionFileReader {

    public RemapDefinition read(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        RemapDefinition.Builder builder = RemapDefinition.builder();

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }

            // Limit is 5 to stop unnecessary regex matching, but also to prevent that the 4th segment contains all the
            // unmatched rest. Which is unwanted.
            String[] split = line.split(",", 5);
            int originNote = parseMidiNote("origin", split[0]);
            String originNoteDescription = null;
            int destinationNote = originNote;
            String destinationNoteDescription = null;

            if (split.length > 1) {
                originNoteDescription = split[1];
            }

            if (split.length > 2) {
                String unparsedMappedNote = split[2];

                if (!unparsedMappedNote.isBlank()) {
                    destinationNote = parseMidiNote("destination", unparsedMappedNote);
                }
            }

            if (split.length > 3) {
                destinationNoteDescription = split[3];
            }

            builder.addMapping(originNote, destinationNote);
        }

        return builder.build();
    }

    private static int parseMidiNote(String name, String unparsedNote) {
        int note = Integer.parseInt(unparsedNote, 10);
        Preconditions.checkNote(name, note);
        return note;
    }
}

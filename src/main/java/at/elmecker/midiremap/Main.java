package at.elmecker.midiremap;

import at.elmecker.midiremap.definition.RemapDefinition;
import at.elmecker.midiremap.definition.RemapDefinitionFileReader;
import at.elmecker.midiremap.io.CopyingMidiFileVisitor;
import at.elmecker.midiremap.io.LoggingMidiFileVisitor;
import at.elmecker.midiremap.io.MidiFileReader;
import at.elmecker.midiremap.io.RemappingMidiFileVisitor;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

public class Main {

    /**
     * Argument list (must use absolute file paths):
     * <ul>
     *     <li>{@code args[0]} - input file</li>
     *     <li>{@code args[1]} - output file</li>
     *     <li>{@code args[2]} - remapping definition file</li>
     * </ul>
     */
    public static void main(String[] args) throws IOException, InvalidMidiDataException {
        System.out.println("""
                --------------------------------------------------------------------------------
                
                                                   MIDI REMAP
                
                --------------------------------------------------------------------------------
                
                """);

        if (args.length < 3) {
            System.err.println("Too few arguments, please provide input and output file!");
            return;
        }

        String inputPath = args[0];
        Path outputPath = Path.of(URI.create("file:" + args[1]));
        Path remapDefinitionPath = Path.of(URI.create("file:" + args[2]));
        RemapDefinitionFileReader remapDefinitionFileReader = new RemapDefinitionFileReader();
        RemapDefinition definition = remapDefinitionFileReader.read(remapDefinitionPath);

        MidiFileReader midiFileReader = new MidiFileReader();
        midiFileReader.read(inputPath, List.of(
                new LoggingMidiFileVisitor(),
                new RemappingMidiFileVisitor(definition),
                new CopyingMidiFileVisitor(outputPath)
        ));
    }
}
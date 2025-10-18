package at.elmecker.midiremap;

import at.elmecker.midiremap.definition.RemapDefinition;
import at.elmecker.midiremap.definition.RemapDefinitionFileReader;
import at.elmecker.midiremap.io.*;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

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
            throw new IllegalArgumentException("Too few arguments, please provide input and output file!");
        }

        Path inputPath = path(args[0]);
        Path outputPath = path(args[1]);
        Path remapDefinitionPath = path(args[2]);

        RemapDefinitionFileReader remapDefinitionFileReader = new RemapDefinitionFileReader();
        RemapDefinition definition = remapDefinitionFileReader.read(remapDefinitionPath);

        MidiReader reader = new MidiSystemFileReader();
        MidiFile midiFile = reader.read(inputPath);
        midiFile.accept(new DispatchingMidiFileVisitor(
                new LoggingMidiFileVisitor(),
                new RemappingMidiFileVisitor(definition),
                new CopyingMidiFileVisitor(new MidiSystemFileWriter(() -> Files.newOutputStream(outputPath)))
        ));
    }

    private static Path path(String filePath) {
        return Path.of(URI.create("file:" + filePath));
    }
}
package at.elmecker.midiremap.io;

import at.elmecker.midiremap.definition.RemapDefinition;

import javax.sound.midi.*;

import static java.util.Objects.requireNonNull;

public class RemappingMidiFileVisitor implements MidiFileVisitor {

    private final RemapDefinition remapDefinition;

    public RemappingMidiFileVisitor(RemapDefinition remapDefinition) {
        this.remapDefinition = requireNonNull(remapDefinition);
    }

    @Override
    public void startFile(String absoluteFilePath, MidiFileFormat format) {
    }

    @Override
    public void startSequence(Sequence sequence) {
    }

    @Override
    public void endSequence() {
    }

    @Override
    public void startTrack(Track track) {
    }

    @Override
    public void endTrack() {
    }

    @Override
    public void onEvent(MidiEvent event) {
        MidiMessage message = event.getMessage();
        if (!(message instanceof ShortMessage shortMessage)) {
            return;
        }

        // 0x80 NOTE OFF      data1 = pitch/note, data2 = velocity
        // 0x90 NOTE ON       data1 = pitch/note, data2 = velocity
        // 0xA0 POLY PRESSURE data1 = pitch/note, data2 = velocity
        if (shortMessage.getCommand() != ShortMessage.NOTE_ON
                && shortMessage.getCommand() != ShortMessage.NOTE_OFF
                && shortMessage.getCommand() != ShortMessage.POLY_PRESSURE) {
            return;
        }

        try {
            shortMessage.setMessage(
                    shortMessage.getCommand(),
                    shortMessage.getChannel(),
                    remapDefinition.mappedNote(shortMessage.getData1()),
                    shortMessage.getData2()
            );
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endFile() {
    }
}

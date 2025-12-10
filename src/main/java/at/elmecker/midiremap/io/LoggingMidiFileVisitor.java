package at.elmecker.midiremap.io;

import at.elmecker.midiremap.StringUtils;

import javax.sound.midi.*;

public class LoggingMidiFileVisitor implements MidiFileVisitor {
    private static final String DIVIDER = "-".repeat(80);

    private int trackCounter = 0;

    @Override
    public void startFile(String absoluteFilePath, MidiFileFormat format) {
        System.out.println("File: " + absoluteFilePath);
        System.out.println("Title: " + format.getProperty("title"));
        System.out.println("Author: " + format.getProperty("author"));
        System.out.println("Date: " + format.getProperty("date"));
        System.out.println("Copyright: " + format.getProperty("copyright"));
        System.out.println("Comment: " + format.getProperty("comment"));
        System.out.println(DIVIDER);
        System.out.println("Type: " + format.getType());
        System.out.println("Division-Type: " + format.getDivisionType());
        System.out.println("Resolution: " + format.getResolution());
        System.out.println("Byte-Length: " + format.getByteLength());
        System.out.println("Microseconds-Length: " + format.getMicrosecondLength());
        System.out.println(DIVIDER);
        System.out.println();
    }

    @Override
    public void startSequence(Sequence sequence) {
        trackCounter = 0;
        Track[] tracks = sequence.getTracks();
        System.out.println("Tracks: " + tracks.length);
        System.out.println();
    }

    @Override
    public void endSequence() {
    }

    @Override
    public void startTrack(Track track) {
        System.out.println(DIVIDER);
        System.out.println("Track " + trackCounter);
        System.out.println("Ticks: " + track.ticks());
        System.out.println("Events: " + track.size());
    }

    @Override
    public void endTrack() {
        trackCounter++;
    }

    @Override
    public void onEvent(MidiEvent event) {
        System.out.println();
        System.out.println("Tick: " + event.getTick());

        MidiMessage message = event.getMessage();
        System.out.println("Type: " + message.getClass().getSimpleName());
        System.out.println("Status: " + message.getStatus());
        System.out.println("Length: " + message.getLength());

        switch (message) {
            case ShortMessage sm -> {
                System.out.println("Command: " + StringUtils.toHexAndDecString(sm.getCommand()));
                System.out.println("Channel: " + StringUtils.toHexAndDecString(sm.getChannel()));
                System.out.println("Data-1: " + sm.getData1());
                System.out.println("Data-2: " + sm.getData2());
            }
            case MetaMessage mm -> {
                System.out.println("Meta-Type: " + StringUtils.toHexAndDecString(mm.getType()));
                System.out.println("Data: " + StringUtils.byteArrayToHexString(mm.getData()));
            }
            default -> System.out.println("Message: " + message);
        }
    }

    @Override
    public void endFile() {

    }
}

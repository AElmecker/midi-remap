package at.elmecker.midiremap.io;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;

public class ThrowingShortMessage extends ShortMessage {

    @Override
    protected void setMessage(byte[] data, int length) throws InvalidMidiDataException {
        throw new InvalidMidiDataException("oh no");
    }

    @Override
    public void setMessage(int status) throws InvalidMidiDataException {
        throw new InvalidMidiDataException("oh no");
    }

    @Override
    public void setMessage(int status, int data1, int data2) throws InvalidMidiDataException {
        throw new InvalidMidiDataException("oh no");
    }

    @Override
    public void setMessage(int command, int channel, int data1, int data2) throws InvalidMidiDataException {
        throw new InvalidMidiDataException("oh no");
    }
}

package at.elmecker.midiremap.io;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/**
 * Visitor pattern to walk through the structure of a MIDI file.
 * <p>
 * The expected order of calls for one midi file is:
 * <ul>
 *     <li>{@link #startFile(String, MidiFileFormat)} exactly once</li>
 *     <li>{@link #startSequence(Sequence)} exactly once</li>
 *     <li>{@link #startTrack(Track)} 0-n times</li>
 *     <li>{@link #onEvent(MidiEvent)}} 0-m times</li>
 *     <li>{@link #endTrack()}  0-n times (exact same amount as {@link #startTrack(Track)} calls)</li>
 *     <li>{@link #endSequence()}} exactly once</li>
 *     <li>{@link #endFile()} exactly once</li>
 * </ul>
 * <p/>
 */
public interface MidiFileVisitor {

    /**
     * Notifies about the start of a new midi file.
     * Parameters contain meta information about the file and midi format.
     *
     * @see MidiFileFormat
     */
    void startFile(String absoluteFilePath, MidiFileFormat format);

    /**
     * @see Sequence
     */
    void startSequence(Sequence sequence);

    void endSequence();

    /**
     * @see Track
     */
    void startTrack(Track track);

    void endTrack();

    /**
     * @see MidiEvent
     */
    void onEvent(MidiEvent event);

    void endFile();
}

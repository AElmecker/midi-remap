package at.elmecker.midiremap;

import static at.elmecker.midiremap.Preconditions.checkNote;
import static java.util.Objects.requireNonNull;

public final class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class must not be instantiated");
    }

    private static final String[] TWELVE_NOTE_CHROMATIC_SCALE = new String[]{
            "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"
    };

    public static String midiNoteToEnglishTwelveToneChromaticScaleValue(int midiNote, OctaveAlignment octaveAlignment) {
        checkNote("input", midiNote);
        requireNonNull(octaveAlignment);

        int register = (midiNote / 12) + octaveAlignment.shiftValue();
        int note = midiNote % 12;
        return TWELVE_NOTE_CHROMATIC_SCALE[note] + register;
    }

    /**
     * Denotes which octave alignment should be used to display MIDI notes in the english twelve tone chromatic scale.
     * <br/>
     * The middle C is MIDI note 60.
     */
    public enum OctaveAlignment {
        /**
         * Uses C3 as the middle C, which aligns better with physical keyboards.
         * <br/>
         * Lowest note will be C-2 (MIDI note 0).
         */
        MIDDLE_C_AS_C3(-2),
        /**
         * Uses C4 as the middle C, which aligns with the MIDI standard.
         * <br/>
         * Lowest note will be C-1 (MIDI note 0).
         */
        MIDDLE_C_AS_C4(-1),
        ;

        private final int shiftValue;

        OctaveAlignment(int shiftValue) {
            this.shiftValue = shiftValue;
        }

        public int shiftValue() {
            return shiftValue;
        }
    }
}

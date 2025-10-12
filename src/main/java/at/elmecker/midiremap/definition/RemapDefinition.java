package at.elmecker.midiremap.definition;

import static at.elmecker.midiremap.Preconditions.checkNote;

public class RemapDefinition {

    private final int[] mapping;

    private RemapDefinition(int[] mapping) {
        this.mapping = mapping;
    }

    public int mappedNote(int originNote) {
        return mapping[originNote];
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final int[] notes = new int[128];

        private Builder() {
            for (int i = 0; i < notes.length; i++) {
                notes[i] = i;
            }
        }

        public Builder addMapping(int originNote, int destinationNote) {
            checkNote("origin", originNote);
            checkNote("destination", destinationNote);
            notes[originNote] = destinationNote;
            return this;
        }

        public RemapDefinition build() {
            return new RemapDefinition(notes);
        }
    }
}

package at.elmecker.midiremap;

public final class Preconditions {

    private Preconditions() {
        throw new IllegalStateException("Utility class must not be instantiated!");
    }

    public static void checkNote(String name, int note) {
        if (note < 0 || note >= 128) {
            throw new IllegalArgumentException(name + " note " + note + " is not valid, must be in range of [0-127]");
        }
    }
}

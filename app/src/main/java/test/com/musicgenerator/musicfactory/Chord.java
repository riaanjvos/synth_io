package test.com.musicgenerator.musicfactory;

/**
 * Created by riaanvos on 11/09/15.
 */
public class Chord {

    public static final int TYPE_MAJOR = 0;
    public static final int TYPE_MINOR = 1;
    public static final int TYPE_DIMINISHED = 2;

    double[] pitches;
    double noteDuration;

    public Chord(double[] pitches, double noteDuration) {
        this.pitches = pitches;
        this.noteDuration = noteDuration;
    }
}

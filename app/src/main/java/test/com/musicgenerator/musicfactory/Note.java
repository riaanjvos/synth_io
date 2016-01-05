package test.com.musicgenerator.musicfactory;

/**
 * Created by riaanvos on 15/09/15.
 */
public class Note {

    public static final int C_0 = 0;
    public static final int Db_0 = 1;
    public static final int D_0 = 2;
    public static final int Eb_0 = 3;
    public static final int E_0 = 4;
    public static final int F_0 = 5;
    public static final int Gb_0 = 6;
    public static final int G_0 = 7;
    public static final int Ab_0 = 8;
    public static final int A_0 = 9;
    public static final int Bb_0 = 10;
    public static final int B_0 = 11;

    public static final int C_1 = 12;
    public static final int Db_1 = 13;
    public static final int D_1 = 14;
    public static final int Eb_1 = 15;
    public static final int E_1 = 16;
    public static final int F_1 = 17;
    public static final int Gb_1 = 18;
    public static final int G_1 = 19;
    public static final int Ab_1 = 20;
    public static final int A_1 = 21;
    public static final int Bb_1 = 22;
    public static final int B_1 = 23;

    public static final int C_2 = 24;
    public static final int Db_2 = 25;
    public static final int D_2 = 26;
    public static final int Eb_2 = 27;
    public static final int E_2 = 28;
    public static final int F_2 = 29;
    public static final int Gb_2 = 30;
    public static final int G_2 = 31;
    public static final int Ab_2 = 32;
    public static final int A_2 = 33;
    public static final int Bb_2 = 34;
    public static final int B_2 = 35;

    public static final int C_3 = 36;
    public static final int Db_3 = 37;
    public static final int D_3 = 38;
    public static final int Eb_3 = 39;
    public static final int E_3 = 40;
    public static final int F_3 = 41;
    public static final int Gb_3 = 42;
    public static final int G_3 = 43;
    public static final int Ab_3 = 44;
    public static final int A_3 = 45;
    public static final int Bb_3 = 46;
    public static final int B_3 = 47;

    public static final int C_4 = 48;
    public static final int Db_4 = 49;
    public static final int D_4 = 50;
    public static final int Eb_4 = 51;
    public static final int E_4 = 52;
    public static final int F_4 = 53;
    public static final int Gb_4 = 54;
    public static final int G_4 = 55;
    public static final int Ab_4 = 56;
    public static final int A_4 = 57;
    public static final int Bb_4 = 58;
    public static final int B_4 = 59;

    public static final int C_5 = 60;
    public static final int Db_5 = 61;
    public static final int D_5 = 62;
    public static final int Eb_5 = 63;
    public static final int E_5 = 64;
    public static final int F_5 = 65;
    public static final int Gb_5 = 66;
    public static final int G_5 = 67;
    public static final int Ab_5 = 68;
    public static final int A_5 = 69;
    public static final int Bb_5 = 70;
    public static final int B_5 = 71;

    public static double WHOLE_NOTE = 240;
    public static double HALF_NOTE = 120;
    public static double QUATER_NOTE = 60;
    public static double EIGHT_NOTE = 30;
    public static double SIXTEENTH_NOTE = 15;
    public static double DOTTED_QUARTER_NOTE = 90;
    public static double DOTTED_EIGHT_NOTE = 45;
    public static double DOTTED_SIXTEENTH_NOTE = 22.5;
    public static double TRIPLET_QUARTER_NOTE = 40;
    public static double TRIPLET_EIGHT_NOTE = 20;
    public static double TRIPLET_SIXTEENTH_NOTE = 10;

    private double pitch;
    private double noteDuration;

    public Note(double pitch, double noteDuration) {
        this.pitch = pitch;
        this.noteDuration = noteDuration;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getNoteDuration() {
        return noteDuration;
    }

    public void setNoteDuration(double noteDuration) {
        this.noteDuration = noteDuration;
    }
}

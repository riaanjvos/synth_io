package test.com.musicgenerator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import test.com.musicgenerator.musicfactory.Chord;
import test.com.musicgenerator.musicfactory.Note;
import test.com.musicgenerator.musicfactory.SoundIOFactory;
import test.com.musicgenerator.musicfactory.ToneFactory;

public class MainActivity extends Activity {

    SoundIOFactory soundIOFactory;
    ToneFactory toneFactory;
    int tempo = 120;

    //http://basicsynth.com/index.php?page=wfinvest&WEBMGR=...3bf4c0509ba3e1b29dc79b64a

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toneFactory = ToneFactory.getInstance();
        soundIOFactory = SoundIOFactory.getInstance(tempo);

        //Play individual sound
//        soundIOFactory.playNote(new Note(toneFactory.getPitch(Note.C_4), Note.WHOLE_NOTE));

//        //Play note sequence
//        final LinkedList<Note> notes = new LinkedList<>();
//        notes.add(new Note(toneFactory.getPitch(Note.C_4), Note.EIGHT_NOTE));
//        notes.add(new Note(toneFactory.getPitch(Note.D_4), Note.EIGHT_NOTE));
//        notes.add(new Note(toneFactory.getPitch(Note.E_4), Note.EIGHT_NOTE));
//        notes.add(new Note(toneFactory.getPitch(Note.F_4), Note.EIGHT_NOTE));
//
//        notes.add(new Note(toneFactory.getPitch(Note.G_4), Note.EIGHT_NOTE));
//        notes.add(new Note(toneFactory.getPitch(Note.A_4), Note.EIGHT_NOTE));
//        notes.add(new Note(toneFactory.getPitch(Note.B_4), Note.EIGHT_NOTE));
//        notes.add(new Note(toneFactory.getPitch(Note.C_5), Note.EIGHT_NOTE));
//        soundIOFactory.playNoteSequence(notes);

//        //Play individual chord
//        soundIOFactory.playChord(toneFactory.getChordTones(Note.D_4, Chord.TYPE_MAJOR), Note.HALF_NOTE);
//
//        //Play chord sequence
//        ArrayList<Chord> sequence = new ArrayList<>();
//        sequence.add(new Chord(toneFactory.getChordTones(Note.C_4, Chord.TYPE_MAJOR), Note.QUATER_NOTE));
//        sequence.add(new Chord(toneFactory.getChordTones(Note.G_4, Chord.TYPE_MAJOR), Note.QUATER_NOTE));
//        sequence.add(new Chord(toneFactory.getChordTones(Note.A_4, Chord.TYPE_MINOR), Note.QUATER_NOTE));
//        sequence.add(new Chord(toneFactory.getChordTones(Note.F_4, Chord.TYPE_MAJOR), Note.QUATER_NOTE));
//        soundIOFactory.playChordSequence(sequence);
//
//        //Play simple chord progression
//        soundIOFactory.playChordSequence(toneFactory.getSimpleProgression(Note.C_3, Note.QUATER_NOTE));
//
        //Play simple arpeggio
//        soundIOFactory.playNoteSequence(toneFactory.getScale(Note.C_5, Chord.TYPE_MAJOR, Note.WHOLE_NOTE));

//        //Metronome
//        soundIOFactory.start(false, new ProgressCallback() {
//            @Override
//            public void onFull() {
//                Log.v("", "onFull");
//            }
//
//            @Override
//            public void onHalf() {
//                Log.v("", "onHalf");
//            }
//
//            @Override
//            public void onQuarter() {
//                Log.v("","onQuarter");
//            }
//        });

        soundIOFactory.playNotes(new double[]{toneFactory.getPitch(Note.D_4)}, Note.HALF_NOTE);
    }

    public void onDestroy(){
        super.onDestroy();

        try {
            soundIOFactory.stop();
            soundIOFactory = null;
            toneFactory = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void yeahClick1(View view) {
        soundIOFactory.playNotes(toneFactory.getChordTones(Note.D_4, Chord.TYPE_MAJOR), Note.WHOLE_NOTE);
    }

    public void yeahClick2(View view) {
        soundIOFactory.playNotes(toneFactory.getChordTones(Note.D_4, Chord.TYPE_MINOR), Note.WHOLE_NOTE);
    }

    public void yeahClick3(View view) {
        soundIOFactory.playNotes(new double[]{440.0,660.0}, Note.WHOLE_NOTE);
    }
}
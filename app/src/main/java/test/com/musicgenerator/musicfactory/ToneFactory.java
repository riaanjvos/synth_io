package test.com.musicgenerator.musicfactory;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by riaanvos on 11/09/15.
 */
public class ToneFactory {

    //Progression type
    public static final int PROGRESION_I_V_VI_VI = 0;

    //Progression MAJOR
    public static int I = Chord.TYPE_MAJOR;
    public static int II = Chord.TYPE_MINOR;
    public static int III = Chord.TYPE_MINOR;
    public static int IV = Chord.TYPE_MAJOR;
    public static int V = Chord.TYPE_MAJOR;
    public static int VI = Chord.TYPE_MINOR;
    public static int VII = Chord.TYPE_DIMINISHED;
    public static LinkedHashMap<Integer,HashMap<Integer, Integer>> progressionMap;
    //LinkedHashMap<prog_type,HashMap<root+var, chord_type>>

    public HashMap<Integer,Double> noteFrequencyMap = new HashMap<>();

    private static ToneFactory instance = null;

    private ToneFactory() { ; }

    public static ToneFactory getInstance() {

        if(instance == null) {
            instance = new ToneFactory();
            instance.loadTones();
            instance.loadProgressions();
        }

        return instance;
    }

    private void loadTones(){

        int octaveCount = 10;//max human hearing

        int j = 0;
        for(int octave = 0; octave < octaveCount; octave++){
            //always 12 notes in octave
            for(int i = 0; i < 12 ; i++){
                Log.v("", +j + " - " + getFrequency(i + (octave * 12)) + " hz");
                noteFrequencyMap.put(j, getFrequency(i + (octave * 12)));
                j++;
            }
        }
    }

    private void loadProgressions(){
        progressionMap = new LinkedHashMap<>();

        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0, Chord.TYPE_MAJOR);
        map.put(7, Chord.TYPE_MAJOR);
        map.put(9, Chord.TYPE_MINOR);
        map.put(5, Chord.TYPE_MAJOR);
        progressionMap.put(PROGRESION_I_V_VI_VI,map);

        //TODO extends this
    }

    private double getFrequency(double n){
        return 16.351 * (Math.pow(2.0,(n/12.0)));
    }

    public double getPitch(int note){
        return noteFrequencyMap.get(note);
    }

    public double[] getChordTones(int root, int type){

        switch (type){
            case Chord.TYPE_MAJOR:
                return (new double[]{
                        getPitch(root),
                        getPitch(root + 4),
                        getPitch(root + 7)});
            case Chord.TYPE_MINOR:
                return (new double[]{
                        getPitch(root),
                        getPitch(root + 3),
                        getPitch(root + 7)});

            //TODO extends this

            default: return null;
        }
    }

    public ArrayList<Chord> getSimpleProgression(int root, double noteDuration){

        ArrayList<Chord> result = new ArrayList<>();

        HashMap<Integer, Integer> map = progressionMap.get(0);
        Set<Map.Entry<Integer, Integer>> set = map.entrySet();
        for(Map.Entry<Integer, Integer> item : set){
            result.add(new Chord(getChordTones(root+item.getKey(), item.getValue()), noteDuration));
        }
        return result;
    }

    public LinkedList<Note> getScale(int root, int type, double noteDuration) {

        int[] intervals = Scale.MAJOR_INTERVALS;

        LinkedList<Note> result = new LinkedList<>();

        for(int i = 0; i < intervals.length ; i++){
            result.add(new Note(root+intervals[i], Note.QUATER_NOTE));
        }
        return result;
    }
}

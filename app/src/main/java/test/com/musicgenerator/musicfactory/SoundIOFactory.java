package test.com.musicgenerator.musicfactory;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by riaanvos on 11/09/15.
 */
public class SoundIOFactory {

    private ExecutorService service;
    private int sampleRate = 44100;
    private double tempo;//bpm
    private boolean running = false;

    private static SoundIOFactory instance = null;

    private SoundIOFactory() { ; }

    public static SoundIOFactory getInstance(int tempo) {

        if(instance == null) {
            instance = new SoundIOFactory();
            instance.tempo = tempo;
            instance.service = Executors.newFixedThreadPool(32);//can only create 32 AudioTracks
        }

        return instance;
    }

    public void playNote(final Note note){

        // start a new thread to synthesise audio
        Thread t = new Thread() {

            public void run() {

                Log.v("vos"," performed by "+ Thread.currentThread().getName());

                // set process priority
                setPriority(Thread.MAX_PRIORITY);

                // set the buffer size
                double duration = note.getNoteDuration()/tempo*1000.0;
                int numSamples = (int) (duration * sampleRate/1000.0);
                double[] samples = new double[numSamples];
                byte[] generatedSnd = new byte[2 * numSamples];

                // fill out the array
                for (int i = 0; i < numSamples; ++i)
                    samples[i] = Math.sin(2 * Math.PI * i / (sampleRate/note.getPitch()));

                // convert to 16 bit pcm sound array
                // assumes the sample buffer is normalised.
                int idx = 0;
                for (final double dVal : samples) {
                    // scale to maximum amplitude
                    final short val = (short) ((dVal * 32767));
                    // in 16 bit wav PCM, first byte is the low order byte
                    generatedSnd[idx++] = (byte) (val & 0x00ff);
                    generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                }

                final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                        AudioTrack.MODE_STATIC);
                audioTrack.write(generatedSnd, 0, generatedSnd.length);
                audioTrack.play();

                audioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
                    @Override
                    public void onMarkerReached(AudioTrack track) {
                        audioTrack.release();
                    }

                    @Override
                    public void onPeriodicNotification(AudioTrack track) {
                        //nothing
                    }
                });

                try{
                    Thread.sleep((long)duration);
                    audioTrack.release();
                    this.join();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        synchronized (service) {
            if (!service.isShutdown())
                service.execute(t);
        }
    }

    public void playNoteSequence(final LinkedList<Note> notes){

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(Note note : notes){
                    playNote(note);
                    try{Thread.sleep((long) (note.getNoteDuration()/tempo*1000.0));}catch (Exception e){}
                }
            }
        }).start();
    }

    public void playChord(final double[] pitches, final double noteDuration){

        final ArrayList<Thread> threadPool = new ArrayList<>();

        for(final double pitch : pitches) {
            // start a new thread to synthesise audio
            Thread t = new Thread() {

                public void run() {

                    // set process priority
                    setPriority(Thread.MAX_PRIORITY);

                    // set the buffer size
                    double duration = noteDuration/tempo*1000;
                    int numSamples = (int) (duration * sampleRate/1000.0);
                    double[] samples = new double[numSamples];
                    byte[] generatedSnd = new byte[2 * numSamples];

                    AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                            sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                            AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                            AudioTrack.MODE_STATIC);

                    // fill out the array
                    for (int i = 0; i < numSamples; ++i)
                        samples[i] = Math.sin(2 * Math.PI * i / (sampleRate/pitch));

                    // convert to 16 bit pcm sound array
                    // assumes the sample buffer is normalised.
                    int idx = 0;
                    for (final double dVal : samples) {
                        // scale to maximum amplitude
                        final short val = (short) ((dVal * 32767));
                        // in 16 bit wav PCM, first byte is the low order byte
                        generatedSnd[idx++] = (byte) (val & 0x00ff);
                        generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
                    }

                    audioTrack.write(generatedSnd, 0, generatedSnd.length);
                    audioTrack.play();

                    try{
                        Thread.sleep((long)duration);
                        audioTrack.release();
                        this.join();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            threadPool.add(t);
        }

        for(Thread t : threadPool) {
            synchronized (service) {
                if (!service.isShutdown()) service.execute(t);
            }
        }
    }

    public void playChordSequence(final ArrayList<Chord> chords){

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(Chord chord : chords){
                    playChord(chord.pitches, chord.noteDuration);
                    try{Thread.sleep((long) (chord.noteDuration /tempo*1000.0));}catch (Exception e){}
                }
            }
        }).start();
    }

    public void start(final boolean metronome, final ProgressCallback callback){

        running = true;

        new Thread() {
            public void run() {

                Looper.prepare();

                while(running){

                    callback.onFull();
                    callback.onQuarter();
                    callback.onHalf();
                    if(metronome)playNote(new Note(440, Note.SIXTEENTH_NOTE/2));
                    try{Thread.sleep((long) (Note.QUATER_NOTE/tempo*1000.0));}catch (Exception e){}

                    callback.onQuarter();
                    if(metronome)playNote(new Note(440, Note.SIXTEENTH_NOTE/2));
                    try{Thread.sleep((long) (Note.QUATER_NOTE/tempo*1000.0));}catch (Exception e){}

                    callback.onQuarter();
                    callback.onHalf();
                    if(metronome)playNote(new Note(440, Note.SIXTEENTH_NOTE/2));
                    try{Thread.sleep((long) (Note.QUATER_NOTE/tempo*1000.0));}catch (Exception e){}

                    callback.onQuarter();
                    if(metronome)playNote(new Note(440, Note.SIXTEENTH_NOTE/2));
                    try{Thread.sleep((long) (Note.QUATER_NOTE/tempo*1000.0));}catch (Exception e){}
                }

                Looper.loop();
            }
        }.start();

    }

    public void stop() throws InterruptedException{

        running = false;
    }

    public void playNotes(final double[] pitches, final double noteDuration){

        // start a new thread to synthesise audio
        Thread t = new Thread() {

            public void run() {

                // set process priority
                setPriority(Thread.MAX_PRIORITY);

                // set the buffer size
                double duration = noteDuration/tempo*1000.0;

//                byte[] generatedSnd1 = createSineWaveBuffer(pitches, duration);
                byte[] generatedSnd1 = calculateSineWave(pitches, calculateIncrements(pitches));

                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                        sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, generatedSnd1.length,
                        AudioTrack.MODE_STATIC);
                audioTrack.write(generatedSnd1, 0, generatedSnd1.length);
                audioTrack.play();

                try{
                    Thread.sleep((long)duration);
                    audioTrack.release();
                    this.join();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        synchronized (service) {
            if (!service.isShutdown()) service.execute(t);
        }
    }

    public byte[] createSineWaveBuffer(double[] frequencies, double duration) {

        int numSamples = (int) (duration * sampleRate/1000.0);
        double[] samples = new double[numSamples];
        double phaseIncrement = 2 * Math.PI;

        for (int j = 0; j < sampleRate; j++) {
            double sum = 0;

            for (double frequency : frequencies) {
                sum += Math.sin(phaseIncrement * j * frequency / sampleRate);
            }
            samples[j] = sum / frequencies.length;
        }

        byte[]  output = convertToPCM16Bit(samples);

        return output;
    }

    private byte[] convertToPCM16Bit(double[] sample) {

        byte[] result = new byte[2 * sample.length];

        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            result[idx++] = (byte) (val & 0x00ff);
            result[idx++] = (byte) ((val & 0xff00) >>> 8);
        }

        return result;
    }

    public double[] calculateIncrements(double[] frequencies) {

        double twoPi = 2 * Math.PI;

        double[] result = new double[frequencies.length];

        for (int i = 0; i < frequencies.length; i++) {
            result[i] = (twoPi / sampleRate) * frequencies[i];
        }

        return result;
    }

    public byte[] calculateSineWave(double[] phases, double[] increments) {
        double[] sample = new double[sampleRate];

        for (int i = 0; i < sampleRate; i++) {
            double sum = 0;

            for (int j = 0; j < phases.length; j++) {
                sum += Math.sin(phases[j]);
                phases[j] += increments[j];

                if (phases[j] >= 2 * Math.PI) {
                    phases[j] -= 2 * Math.PI;
                }
            }

            sample[i] = sum / phases.length;
        }

        return convertToPCM16Bit(sample);
    }
}

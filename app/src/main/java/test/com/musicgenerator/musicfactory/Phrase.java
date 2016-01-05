package test.com.musicgenerator.musicfactory;

/**
 * Created by riaanvos on 14/09/15.
 */
public class Phrase {

    private Note[] notes;

    public Phrase() {
    }

    public Phrase(Note[] notes) {
        this.notes = notes;
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setNotes(Note[] notes) {
        this.notes = notes;
    }
}

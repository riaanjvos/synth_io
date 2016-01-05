package test.com.musicgenerator.musicfactory;

/**
 * Created by riaanvos on 15/09/15.
 */
public interface ProgressCallback {
    public void onFull();
    public void onHalf();
    public void onQuarter();
}

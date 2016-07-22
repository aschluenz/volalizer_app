package volalizer.volalizer.sound;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.AutomaticGainControl;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by andyschlunz on 03.07.16.
 */
public class SoundRecorder {

    private final String log = "SoundRecorder class";
    private static final int SAMPLE_RATE = 44100;
    private static final int NUMBER_OF_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private Thread recordingThread = null;
    private boolean mISRecording = true;
    private short[] mBuffer;  //buffer element

    private int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    private int BytesPerElement = 2;

    private static String mFileName = null;
    private AudioRecord mRecorder;
    private double dbValue = 0;

    private double sum;
    private int bufferPos;


    /**
     * Constructor
     * init sound recorder
     */
    public SoundRecorder() {
        initRecorder();
    }

    /**
     * prepares buffer for audiorecoder
     */
    private void initRecorder() {
        int bufferSizePersonal = BufferElements2Rec * BytesPerElement;
        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                                                      AudioFormat.ENCODING_PCM_16BIT);
        mBuffer = new short[bufferSize];
        mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, NUMBER_OF_CHANNELS,
//                                    RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);
                                    RECORDER_AUDIO_ENCODING, bufferSize);
        Log.e(log, "initRecorder :: bufferSize = " + bufferSize);
    }

    public double doRecord() throws InterruptedException {
        mISRecording = true;
        onRecord(true);
        Thread.currentThread();
        Thread.sleep(5000); //record 5 seconds in milliseconds
        mISRecording = false;
        onRecord(false);

        Log.e(log, "GEMESSENER DB WERT: " + dbValue);
        return dbValue;
    }

    /**
     * controller to start and stop recording
     * @param start
     */
    private void onRecord(boolean start) {
        if (start) {
           startRecording();

        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        mRecorder.startRecording();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Double> dbValues = new ArrayList<Double>();
                while (mISRecording) {
                    double amplitude = 0.0;
                    double db = 0.0;
                    int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);

                    for (int i = 0; i < readSize; i++) {
                        double val = Math.abs(mBuffer[i]);
                        if (amplitude < val) {
                            amplitude = val;
                        }
                    }
                    db = 20 * Math.log10(amplitude);
                    dbValues.add(db);
                }
                // Bildung des Mittelwerts der Messung
                double sum = 0.0;
                for (double db : dbValues) {
                    sum += db;
                }
                dbValue = sum / dbValues.size();
            }
        }).start();
    }

    private void stopRecording() {
        if (null != mRecorder) {
            mISRecording = false;
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }
}

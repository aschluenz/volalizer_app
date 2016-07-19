package volalizer.volalizer.sound;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by andyschlunz on 03.07.16.
 */
public class SoundRecorder {

    private static final int SAMPLE_RATE = 44100;
    private static final int NUMBER_OF_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private Thread recordingThread = null;
    private boolean mISRecording = true;
    private short[] mBuffer;  //buffer element

    int BufferElements2Rec = 1024; // want to play 2048 (2K) since 2 bytes we use only 1024
    int BytesPerElement = 2;

    private static String mFileName = null;
    private AudioRecord mRecorder;
    double db_val = 0;

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
        int bufferSize = BufferElements2Rec * BytesPerElement;
        Log.e("BufferE2Rec * BytesPEle", String.valueOf(BufferElements2Rec * BytesPerElement));
        mBuffer = new short[bufferSize];
        mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, NUMBER_OF_CHANNELS, RECORDER_AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);
    }

    public boolean doRecord() throws InterruptedException {
        mISRecording = true;
        onRecord(true);
        Thread.currentThread();
        Thread.sleep(5000); //record 5 seconds in milliseconds
        mISRecording = false;
        onRecord(false);
        return true;
    }

    /**
     * controller to start and stop recording
     * @param start
     */
    private void onRecord(boolean start) {
        if (start) {
           double result = startRecording();

        } else {
            stopRecording();
        }
       // Log.e("result db:", String.valueOf(result));

    }

    private double startRecording() {
        mRecorder.startRecording();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (mISRecording) {
                    //     Log.e("in Recording while loop", String.valueOf(mISRecording));
                    double sum = 0;
                    int readSize = mRecorder.read(mBuffer, 0, mBuffer.length);
                    for (int i = 0; i < readSize; i++) {
                        sum += Math.pow((double) mBuffer[i], 2);

                        double rms = Math.sqrt(sum / (double) readSize);

                        double db = Math.log10(rms / 32768.0);
                     //   Log.println((Integer)db);
                       // if(db > db_val){
                            db_val = db;
                     //   };

                    }

                }

            }
            });
        return db_val;

    }

    //convert short to byte
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }

    private void stopRecording() {
        if (null != mRecorder) {
            mISRecording = false;
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }
/*
    private void writeAudioDataToFile() {
        // Write the output audio in byte
        String filePath = mFileName;
        short sData[] = new short[BufferElements2Rec];
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (isRecording) {
            // gets the voice output from microphone to byte format

            recorder.read(sData, 0, BufferElements2Rec);
            try {
                // writes the data to file from buffer
                // stores the voice buffer
                byte bData[] = short2byte(sData);
                os.write(bData, 0, BufferElements2Rec * BytesPerElement);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/


    /*
        private void processAudioData() {
            //String filePath = mFileName;
            short sData[] = new short[BufferElements2Rec];
            Log.e("sData: ",String.valueOf(sData.length));
            int i;
            double dLevel;
            while (isRecording) {
                //lesen vom recorder
                recorder.read(sData, 0, BufferElements2Rec);
                for(i = 0; i < BufferElements2Rec; i++){
                //   dLevel = AddSample(sData[i]);
               // dLevel = rms_to_level(getRMS());
                 //   Log.e("dlevel ist: ",String.valueOf(dLevel));
    // ADD CODE HERE
    // ...
    // dLevel kann nun weiterverarbeitet werden
    // z.B. in einem Textfeld ausgeben
    // oder in einem Progressbar anzeigen
            }
        }
        }
        /*
        public double AddSample(short sample) {
            double sum = 0.0D;
    // ältesten Wert aus der Summe entfernen:
             sum -= Math.pow((double)sample, 2);
    // neuen Wert der Summe hinzufügen:
            sum += Math.pow((double)sample, 2);
    // neuen Wert dem Puffer hinzufügen:
            sData[i] = sample;
    // Cursorposition inkrementieren:
            i++;
    // Cursorposition zurücksetzen, wenn das
    // Pufferende erreicht wurde:
            if(i >= BufferElements2Rec)
                i = 0;
        }


        public double getRMS() {
            return Math.sqrt(sum / (double) BufferElements2Rec);
        }
    */
    public double rms_to_level(double rms) {
        return Math.log10(rms / 32768.0);
    }
}

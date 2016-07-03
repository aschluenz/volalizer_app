package volalizer.volalizer.sound;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by andyschlunz on 03.07.16.
 */
public class SoundRecorder {

    private static String mFileName = null;
    private MediaRecorder mMediaRecorder = null;

    public SoundRecorder() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/Volalizer.3gp";
    }

    private void onRecord(boolean start){
        if(start){
            startRecording();
        }else{
            stopRecording();
        }
    }

    private void startRecording(){
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setOutputFile(mFileName);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("Sound Recording:", "prepare() failed");
        }

        mMediaRecorder.start();

    }

    private void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }


    public boolean doRecord() throws InterruptedException {
        onRecord(true);
        Thread.currentThread();
        Thread.sleep(20000);
        onRecord(false);

        return true;
    }

}

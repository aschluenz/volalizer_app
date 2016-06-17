package volalizer.volalizer.fragments;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;

import volalizer.volalizer.R;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class RecordFragment extends Fragment {

    private static String m_fileName = null;
    private RecordButton mRecordbutton = null;
    private MediaRecorder mMediaRecorder = null;
    private PlayButton mPlayButton = null;
    private MediaPlayer mMediaPlayer = null;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_record,container,false);

       /* LinearLayout ll = new LinearLayout(getActivity());
          mRecordbutton = new RecordButton(getActivity());
          mPlayButton = new PlayButton(getActivity());
        ll.addView(mRecordbutton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,0));
        ll.addView(mPlayButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,0));
        getActivity().setContentView(ll);
        */
        return v;
    }


    public RecordFragment(){
        m_fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        m_fileName += "/Audiotest.3gp";
    }


    private void onRecord(boolean start){
        if(start){
            startRecording();

        }else
            stopRecording();
    }

    private void stopRecording() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;

    }

    private void onPlay(boolean play){
        if(play){
            startPlay();
        }else
            stopPlay();
    }

    private void startPlay(){
        mMediaPlayer = new MediaPlayer();
        try{
            mMediaPlayer.setDataSource(m_fileName);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }catch (IOException e){
            Log.e("Aufiorecorder","Start Playing Failed");
        }
    }
    private void stopPlay(){
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    private void startRecording(){
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mMediaRecorder.setOutputFile(m_fileName);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            mMediaRecorder.prepare();
        }catch (IOException e){
            Log.e("Player", "Not playing");
        }
        mMediaRecorder.start();
    }

    class RecordButton extends Button {

        public boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context context) {
            super(context);
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {

        public boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlay(mStartPlaying);
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context context) {
            super(context);
            setOnClickListener(clicker);
        }
    }

}

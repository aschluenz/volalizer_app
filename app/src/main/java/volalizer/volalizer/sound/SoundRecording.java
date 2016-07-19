package volalizer.volalizer.sound;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import dmax.dialog.SpotsDialog;
import volalizer.volalizer.R;
import volalizer.volalizer.RecordDetailActivity;

/**
 * Created by andyschlunz on 03.07.16.
 */
public class SoundRecording extends AsyncTask<Void, Void, Void> {

    private AlertDialog dialog;
    private Context mContext;
    private boolean isIndoor;


    public SoundRecording(Context context,boolean isIndoor) {
        this.mContext = context;
        this.isIndoor = isIndoor;
    }

    @Override
    protected void onPreExecute() {
        dialog = new SpotsDialog(mContext, R.style.CustomRecordDialog);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        Intent i = new Intent(mContext, RecordDetailActivity.class);
        i.putExtra("showSaveBtn", true);
        mContext.startActivity(i);

    }

    @Override
    protected Void doInBackground(Void... params) {
        SoundRecorder soundRecorder = new SoundRecorder();
       // SoundAnalyser soundAnalyser = new SoundAnalyser();

        try {
            soundRecorder.doRecord();
         //   soundAnalyser.startAnalysing(isIndoor);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("SoundRecording", "I'm in doinbackground");
        return null;
    }
}

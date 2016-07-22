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

    private final String log = "SoundRecording class";

    private AlertDialog dialog;
    private Context mContext;
    private boolean isIndoor;
    private double dbValue;

    public SoundRecording(Context context,boolean isIndoor) {
        this.mContext = context;
        this.isIndoor = isIndoor;
        this.dbValue = 0.0;
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
        i.putExtra("dbValue", dbValue);
        mContext.startActivity(i);

    }

    @Override
    protected Void doInBackground(Void... params) {
        SoundRecorder soundRecorder = new SoundRecorder();
        try {
            this.dbValue = soundRecorder.doRecord();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}

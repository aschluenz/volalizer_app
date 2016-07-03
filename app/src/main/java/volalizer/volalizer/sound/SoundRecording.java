package volalizer.volalizer.sound;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import dmax.dialog.SpotsDialog;
import volalizer.volalizer.R;

/**
 * Created by andyschlunz on 03.07.16.
 */
public class SoundRecording extends AsyncTask<Void, Void, Void> {

    private AlertDialog dialog;
    private Context mContext;


    public SoundRecording(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new SpotsDialog(mContext, R.style.CustomRecordDialog);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {


        Log.e("SoundRecording", "I'm in doinbackground");
        return null;
    }
}

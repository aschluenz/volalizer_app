package volalizer.volalizer.fragments;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import volalizer.volalizer.R;
import volalizer.volalizer.Webview;
import volalizer.volalizer.sound.SoundRecording;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class RecordFragment extends Fragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = "RecordFragment";
    private SwitchCompat indoorSwitch;
    private ImageButton recBtn;
    private Button showMap;
    private static final int REQUEST_RECORD_AUDIO = 0;
    private View mLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_record, container, false);
        recBtn = (ImageButton) v.findViewById(R.id.btn_play);
        recBtn.setOnClickListener(this);
        indoorSwitch = (SwitchCompat) v.findViewById(R.id.switch_indoor);
        showMap = (Button) v.findViewById(R.id.openWebView_Btn);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Webview.class);
                getContext().startActivity(i);
            }
        });

        return v;
    }

    public void startRecording(){
        Log.e(TAG, "Audio Record permission has already been granted.");
        SoundRecording sp = new SoundRecording(getContext(), indoorSwitch.isChecked());
        sp.execute();
    }


    @Override
    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestAudioRecordPermission();
        }
        else {
         startRecording();
        }
    }

    private void requestAudioRecordPermission() {
        Log.i(TAG, "Record Audio permission has NOT been granted. Requesting permission.");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startRecording();
    }
}




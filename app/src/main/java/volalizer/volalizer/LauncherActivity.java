package volalizer.volalizer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class LauncherActivity extends AppCompatActivity {

    public static final String TAG = "LauncherActivity";
    private static final int REQUEST_READ_PHONE_STATE = 0;
    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestReadPhoneStatePermission();
        } else {
            Log.i(TAG, "Read Phone State permission has already been granted.");
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void requestReadPhoneStatePermission() {
        Log.i(TAG, "Read Phone State permission has NOT been granted. Requesting permission.");
     /*   if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            Snackbar.make(mLayout, R.string.permission_read_phone_state_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getParent(),
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .show();
        } else {

            */
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }
  //  }
}

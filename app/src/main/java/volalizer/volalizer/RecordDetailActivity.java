package volalizer.volalizer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;

public class RecordDetailActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 0;
    private View mLayout;
    private boolean showSaveBtn = false;
    private LocationManager lm;
    private Location mLocation;
    private String provider;


    private BarChart bar_chart;
    private Button save_btn;
    private EditText mComment_Text_View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        Bundle b = getIntent().getExtras();
        showSaveBtn = b.getBoolean("showSaveBtn");

        addLineChart();
        addTextFields();

        addsaveBtn(showSaveBtn);
        Log.e("ShowSaveBtn value:", String.valueOf(showSaveBtn));
    }

    private void addTextFields() {
        mComment_Text_View = (EditText) findViewById(R.id.Comment_editText);
    }

    private void addLineChart() {
        bar_chart = (BarChart) findViewById(R.id.chart_bar);


    }


    private void addsaveBtn(boolean isVisible){
        if(isVisible){
            save_btn = (Button) findViewById(R.id.save_btn);
            save_btn.setText("Save");
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getcurrentLocation();

                    //TODO send data to backend
                }
            });
        }
    }





    private Location getcurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = lm.getBestProvider(criteria, false);

        mLocation = lm.getLastKnownLocation(provider);

        return mLocation;
    }

    private void requestLocationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Snackbar.make(mLayout, R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getParent(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION },
                                    REQUEST_LOCATION);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_LOCATION);
        }
    }

}

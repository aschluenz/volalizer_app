package volalizer.volalizer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import volalizer.volalizer.network.OkHttpHandlerPostRecord;

public class RecordDetailActivity extends AppCompatActivity {

    private final String log = "Visualization";
    private static final int REQUEST_LOCATION = 0;
    private View mLayout;
    private boolean showSaveBtn = false;
    private LocationManager lm;
    private Location mLocation;
    private String provider;

    private BarChart barChart;
    private Button save_btn;
    private EditText mComment_Text_View;

    private float maxScale = 130;

    private double dbValue = 0.0;
    private boolean isIndoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(log, "Visualisierung wird gestartet...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        Bundle b = getIntent().getExtras();
        try {
            showSaveBtn = b.getBoolean("showSaveBtn");
            dbValue = b.getDouble("dbValue");
            isIndoor = b.getBoolean("isIndoor");
        } catch (RuntimeException e) {
            showSaveBtn = false;
        }
        drawBarChart();
        addTextFields();

        addsaveBtn(showSaveBtn);


        Log.e("ShowSaveBtn value:", String.valueOf(showSaveBtn));
        Log.e("DB Value: ", String.valueOf(dbValue));
    }

    private void addTextFields() {
        mComment_Text_View = (EditText) findViewById(R.id.Comment_editText);
    }

    private void drawBarChart() {
        barChart = (BarChart) findViewById(R.id.chart_bar);
        YAxis yl = barChart.getAxisLeft();
        yl.setEnabled(true);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisLineWidth(3.0f);
        yl.setAxisMaxValue(140f);
        yl.setAxisMinValue(0f);


        YAxis yr = barChart.getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);

        XAxis x = barChart.getXAxis();
        x.setTextSize(10f);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(true);
        x.setDrawLabels(false);

        List<BarEntry> barEntries = new ArrayList<BarEntry>();
        BarEntry entry = new BarEntry(0, (float) dbValue);
        barEntries.add(entry);

        BarDataSet bds = new BarDataSet(barEntries, "db");
        bds.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(bds);

        BarData bd = new BarData(dataSets);

        barChart.setData(bd);
        barChart.invalidate();
    }


    private void addsaveBtn(boolean isVisible) {
        if (isVisible) {
            save_btn = (Button) findViewById(R.id.save_btn);
            save_btn.setText("Save");
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Location location = getcurrentLocation();
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateTime = sf.format(Calendar.getInstance().getTime());

                    JSONObject record = new JSONObject();
                    try {
                        record.put("imei", getUserIMEI());
                        record.put("latitude", location.getLatitude());
                        record.put("longitude", location.getLongitude());
                        record.put("time", currentDateTime);
                        record.put("comment", String.valueOf(mComment_Text_View.getText()));
                        record.put("isIndoor", isIndoor);
                        record.put("dbValue", dbValue);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    OkHttpHandlerPostRecord post = new OkHttpHandlerPostRecord();
                    post.execute(record);
                    finish();
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

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION);
    }

    private String getUserIMEI() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


}

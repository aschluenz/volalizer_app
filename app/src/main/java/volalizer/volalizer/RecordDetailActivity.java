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
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
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

    private String time = "";
    private String address_String = "";
    private String comment_text = "";

    private BarChart barChart;
    private Button save_btn;
    private EditText mComment_Text_View;
    private TextView mLocationText;
    private TextView mDateText;




    private double dbValue = 0.0;
    private boolean isIndoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(log, "Visualisierung wird gestartet...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }

        /**
         *   i.putExtra("showSaveBtn", false); (/)
         i.putExtra("dbValue", mDataSet[getAdapterPosition()].getDB_VALUE()); (/)
         i.putExtra("isIndoor", mDataSet[getAdapterPosition()].getIS_INDOOR()); (/)
         i.putExtra("time", mDataSet[getAdapterPosition()].getTIME()); (/)
         i.putExtra("address", location.getText());
         i.putExtra("comment", mDataSet[getAdapterPosition()].getCOMMENT());
         */

        Bundle b = getIntent().getExtras();
        try {
            showSaveBtn = b.getBoolean("showSaveBtn");
            dbValue = b.getDouble("dbValue");
            isIndoor = b.getBoolean("isIndoor");
            if(!showSaveBtn){
                time = b.getString("time");
                address_String = b.getString("address");
                comment_text = b.getString("comment");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        drawBarChart();
        addTextFields();
        addsaveBtn(showSaveBtn);

        Log.e("ShowSaveBtn value:", String.valueOf(showSaveBtn));
        Log.e("DB Value: ", String.valueOf(dbValue));
    }

    private void addTextFields() {
        mComment_Text_View = (EditText) findViewById(R.id.Comment_editText);
        if(!showSaveBtn){
            mComment_Text_View.setText(comment_text);
            mComment_Text_View.setClickable(false);
            mComment_Text_View.setFocusable(false);
            mDateText = (TextView) findViewById(R.id.date_textview);
            mDateText.setText(time);
            mLocationText = (TextView) findViewById(R.id.location_textview);
            mLocationText.setText(address_String);

        }
    }

    private void drawBarChart() {
        barChart = (BarChart) findViewById(R.id.chart_bar);
        YAxis yl = barChart.getAxisLeft();
        yl.setEnabled(true);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMaxValue(140f);
        yl.setAxisMinValue(0f);
        yl.setTextSize(12f);

        YAxis yr = barChart.getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);

        XAxis x = barChart.getXAxis();
        x.setTextSize(20f);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(false);
        x.setDrawAxisLine(true);
        x.setDrawLabels(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        List<BarEntry> barEntries = new ArrayList<BarEntry>();
        BarEntry entry = new BarEntry(0, (float) dbValue);
        barEntries.add(entry);

        BarDataSet bds = new BarDataSet(barEntries, "db");
        bds.setAxisDependency(YAxis.AxisDependency.LEFT);
        bds.setColor(ColorTemplate.rgb(getColorCode()));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(bds);

        BarData bd = new BarData(dataSets);
        bd.setValueTextSize(20f);

        barChart.setData(bd);
        barChart.setDescription("in db");
        barChart.animateY(1500, Easing.EasingOption.EaseInOutQuart);
        barChart.invalidate();
    }

    private String getColorCode() {
        if (dbValue < 10)
            return "0DFF14";
        else if (dbValue < 20)
            return "4DE80C";
        else if (dbValue < 30)
            return "A0FF00";
        else if (dbValue < 40)
            return "DFE80D";
        else if (dbValue < 50)
            return "FFEB0D";
        else if (dbValue < 60)
            return "FFDF0D";
        else if (dbValue < 70)
            return "E8B60C";
        else if (dbValue < 80)
            return "FFAD00";
        else if (dbValue < 90)
            return "E88C0D";
        else if (dbValue < 100)
            return "FF780D";
        else if (dbValue < 110)
            return "FF5C0D";
        else if (dbValue < 120)
            return "E8350C";
        else
            return "FF0D00";
    }

    private void addsaveBtn(boolean isVisible) {
        if (isVisible) {
            save_btn = (Button) findViewById(R.id.save_btn);
            save_btn.setText("Save");
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLocation = getcurrentLocation();
                    Log.d("location:", String.valueOf(mLocation.getLatitude()));
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateTime = sf.format(Calendar.getInstance().getTime());
                    JSONObject record = new JSONObject();
                    try {
                        record.put("imei", getUserIMEI());
                        record.put("latitude", mLocation.getLatitude());
                        record.put("longitude", mLocation.getLongitude());
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
        }else{
            View button = findViewById(R.id.save_btn);
           button.setVisibility(View.GONE);
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

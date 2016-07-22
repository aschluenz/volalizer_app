package volalizer.volalizer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(log, "Visualisierung wird gestartet...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        Bundle b = getIntent().getExtras();
        try {
            showSaveBtn = b.getBoolean("showSaveBtn");
            dbValue = b.getDouble("dbValue");
        }catch (RuntimeException e){
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



    class postDataToServer extends AsyncTask<Void, Void, Void>{

        private Response response;
        private Request request;
        private final OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "TODO"); //TODO add json
        String url = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //ProgressBar pbar = new ProgressBar(getBaseContext(),null, android.R.attr.progressBarStyleLarge);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("HTTP POST: ", "onFailure() Request was: " + call.request());
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("response ", "onResponse(): " + response.body().string());
                }
            });
            return null;
        }

    }


    }

}

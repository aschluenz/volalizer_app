package volalizer.volalizer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private float maxScale = 130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        Bundle b = getIntent().getExtras();
        try {
            showSaveBtn = b.getBoolean("showSaveBtn");
        }catch (RuntimeException e){
            showSaveBtn = false;
        }

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
        bar_chart.setScaleX(maxScale);

        XAxis xAxis = bar_chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);


        //bar_chart.setData();
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

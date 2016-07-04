package volalizer.volalizer.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;

import volalizer.volalizer.MainActivity;
import volalizer.volalizer.R;
import volalizer.volalizer.managers.ListAdapter;
import volalizer.volalizer.models.Record;
import volalizer.volalizer.network.OkHttp;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class RecordedListFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = "RecordedListFragment";

    private static final int REQUEST_READ_PHONE_STATE = 0;

    private View mLayout;
    protected RecyclerView mRecyclerView;

    protected RecyclerView.LayoutManager mLayoutManager;

    protected ListAdapter mAdapter;
    private static String IMEI;

    protected Record[] mDataset;
    private static final int DATASET_COUNT = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestReadPhoneStatePermission();
        }else{
            Log.e(TAG, "Read Phone State permission has already been granted.");
            //do the magic here
            getUserIMEI();


        }
        // startWebService(getActivity().getIntent().getExtras().getString("EMEI"));

        initDataset();


    }

    private void getUserIMEI() {

        TelephonyManager tm = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();

Log.e(TAG, IMEI);
    }

    private void requestReadPhoneStatePermission() {
        Log.i(TAG, "Read Phone State permission has NOT been granted. Requesting permission.");
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
            Snackbar.make(mLayout, R.string.permission_read_phone_state_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    REQUEST_READ_PHONE_STATE);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }

    }

    private void startWebService(String EMEI) {
        OkHttp okhttp = new OkHttp();
        okhttp.execute(EMEI, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_recordedlist, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());


        int scrollPosition = 0;

        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        //mLayoutManager = new LinearLayoutManager(getActivity());
        // mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.scrollToPosition(scrollPosition);


        mAdapter = new ListAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void initDataset() {


        mDataset = new Record[DATASET_COUNT];
        char user = 'a';

        Record item = new Record("45,67",user, "element1", 45.676543, 56.678743, true, new Date());
        Record item2 = new Record("56,56",user, "element2", 45.676543, 56.678743, true, new Date());

        mDataset[0] = item;
        mDataset[1] = item2;


      String test = mDataset[0].getComment();
    }
}

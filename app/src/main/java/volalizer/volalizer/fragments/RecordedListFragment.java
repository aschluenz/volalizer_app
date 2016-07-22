package volalizer.volalizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import volalizer.volalizer.R;
import volalizer.volalizer.managers.ListAdapter;
import volalizer.volalizer.models.Record;
import volalizer.volalizer.network.OkHttpHandlerGetAllRecords;
import volalizer.volalizer.network.OkHttpHandlerGetAllRecords.AsyncResponse;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class RecordedListFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final String TAG = "RecordedListFragment";


    protected RecyclerView mRecyclerView;

    protected RecyclerView.LayoutManager mLayoutManager;

    protected ListAdapter mAdapter;
    private static String IMEI;

    protected Record[] mDataset = null;
    private static final int DATASET_COUNT = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }

    private String getUserIMEI() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();
        return IMEI;
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
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.scrollToPosition(scrollPosition);
        mAdapter = new ListAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void initDataset() {
        String mIMEI = getUserIMEI();
        OkHttpHandlerGetAllRecords all = (OkHttpHandlerGetAllRecords) new OkHttpHandlerGetAllRecords(new AsyncResponse() {
            @Override
            public void processFinish(Record[] output) {
                mDataset = output;
                Log.d("länge mdataset", String.valueOf(mDataset.length));
                mAdapter = new ListAdapter(mDataset);
                mRecyclerView.setAdapter(mAdapter);
            }
        }).execute(mIMEI);
    }
}

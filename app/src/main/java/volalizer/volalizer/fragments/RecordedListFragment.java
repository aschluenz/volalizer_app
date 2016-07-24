package volalizer.volalizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
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
public class RecordedListFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ListAdapter mAdapter;
    protected Record[] mDataSet = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataSet();
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
        mAdapter = new ListAdapter(mDataSet, getContext());
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private String getUserIMEI() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    private void initDataSet() {
        String mIMEI = getUserIMEI();
        OkHttpHandlerGetAllRecords all = (OkHttpHandlerGetAllRecords) new OkHttpHandlerGetAllRecords(new AsyncResponse() {
            @Override
            public void processFinish(Record[] output) {
                mDataSet = output;
                mAdapter = new ListAdapter(mDataSet, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }).execute(mIMEI);
    }
}

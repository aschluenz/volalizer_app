package volalizer.volalizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import volalizer.volalizer.MainActivity;
import volalizer.volalizer.R;
import volalizer.volalizer.managers.ListAdapter;
import volalizer.volalizer.models.Record;
import volalizer.volalizer.network.OkHttp;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class RecordedListFragment extends Fragment {


    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    protected RecyclerView mRecyclerView;

    protected RecyclerView.LayoutManager mLayoutManager;

    protected ListAdapter mAdapter;
    protected String IMEI;

    protected Record[] mDataset;
    private static final int DATASET_COUNT = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // startWebService(getActivity().getIntent().getExtras().getString("EMEI"));

        initDataset();


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

        mLayoutManager = new LinearLayoutManager(getActivity());
        // mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);


        mAdapter = new ListAdapter(mDataset);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }

    private void initDataset() {


        mDataset = new Record[DATASET_COUNT];
        char user = 'a';

        Record item = new Record(user, "element1", 45.676543, 56.678743, true, new Date());
        Record item2 = new Record(user, "element2", 45.676543, 56.678743, true, new Date());

        mDataset[0] = item;
        mDataset[1] = item2;


      String test = mDataset[0].getComment();
    }
}

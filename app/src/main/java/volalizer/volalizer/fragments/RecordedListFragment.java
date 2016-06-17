package volalizer.volalizer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import volalizer.volalizer.R;

/**
 * Created by andyschlunz on 11.06.16.
 */
public class RecordedListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_recordedlist,container,false);
        return v;
    }

}

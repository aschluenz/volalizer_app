package volalizer.volalizer.managers;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import volalizer.volalizer.R;
import volalizer.volalizer.RecordDetailActivity;
import volalizer.volalizer.models.Record;

/**
 * Created by andyschlunz on 19.06.16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context mContext;
    private static final String TAG = "ListAdapter";
    public static Record[] mDataSet;

    public ListAdapter(Record[] dataSet, Context context) {
        mDataSet = dataSet;
        mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dbValue.setText(String.valueOf(mDataSet[position].getDB_VALUE()) + " db");
        Address temp_address = getAddress(mDataSet[position].getLATITUDE(), mDataSet[position].getLONGITUDE());
        holder.location.setText(temp_address.getAddressLine(0) + ", " + temp_address.getLocality());
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null) {
            return 0;
        }
        return mDataSet.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView location;
        TextView dbValue;
        //ImageView Image;
        CardView cv;

        public ViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            location = (TextView) itemView.findViewById(R.id.location);
            dbValue = (TextView) itemView.findViewById(R.id.db_value);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), RecordDetailActivity.class);
                    i.putExtra("showSaveBtn", false);
                    i.putExtra("dbValue", mDataSet[getAdapterPosition()].getDB_VALUE());
                    i.putExtra("isIndoor", mDataSet[getAdapterPosition()].getIS_INDOOR());
                    //i.putExtra("record", mDataSet[getAdapterPosition()]);
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    private Address getAddress(Double mLat, Double mLong) {
        Geocoder geocoder = new Geocoder(mContext);
        List<Address> mAddress = null;
        try {
            mAddress = geocoder.getFromLocation(mLat, mLong, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mAddress.get(0);
    }

}




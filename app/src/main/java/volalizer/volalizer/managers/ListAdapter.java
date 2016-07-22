package volalizer.volalizer.managers;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import volalizer.volalizer.R;
import volalizer.volalizer.RecordDetailActivity;
import volalizer.volalizer.models.Record;

/**
 * Created by andyschlunz on 19.06.16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    public static Record[] mDataSet;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_list_item, parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
       // holder.location.setText(mDataSet[position].getAdress());
        holder.dbValue.setText(String.valueOf(mDataSet[position].getDB_VALUE()));
    }

    @Override
    public int getItemCount() {
        if(mDataSet == null){
            return 0;
        }
        return mDataSet.length;
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView location;
        TextView dbValue;
        ImageView Image;
        CardView cv;

        public ViewHolder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            //textView = (TextView) itemView.findViewById(R.id.textView);
            location = (TextView) itemView.findViewById(R.id.location);
            dbValue = (TextView) itemView.findViewById(R.id.db_value);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                    Intent i = new Intent(v.getContext(), RecordDetailActivity.class);
                   // i.putExtra("record_details",(android.os.Parcelable) getmDataSet(getAdapterPosition()));
                    v.getContext().startActivity(i);
                }
            });
        }
    }

    public ListAdapter(Record[] dataSet) {
        mDataSet = dataSet;
    }

    public static Record getmDataSetPosition(int position){
        return ListAdapter.mDataSet[position];
    }
}

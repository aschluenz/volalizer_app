package volalizer.volalizer.managers;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import volalizer.volalizer.MainActivity;
import volalizer.volalizer.R;
import volalizer.volalizer.RecordDetailActivity;
import volalizer.volalizer.models.Record;
import volalizer.volalizer.utils.GeocoderHelper;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by andyschlunz on 19.06.16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    private Record[] mDataSet;

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
        holder.dbValue.setText( mDataSet[position].getDbValue());
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView location;
        TextView dbValue;
        ImageView Image;
        CardView cv;


        public ViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            //textView = (TextView) itemView.findViewById(R.id.textView);
            location = (TextView) itemView.findViewById(R.id.location);
            dbValue = (TextView) itemView.findViewById(R.id.db_value);



            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                }
            });


        }

    }

    public ListAdapter(Record[] dataSet) {
        mDataSet = dataSet;
    }
}

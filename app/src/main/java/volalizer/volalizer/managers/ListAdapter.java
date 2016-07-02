package volalizer.volalizer.managers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import volalizer.volalizer.R;
import volalizer.volalizer.models.Record;

/**
 * Created by andyschlunz on 19.06.16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    private Record[] mDataSet;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_list_item, parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        holder.getTextView().setText(mDataSet[position].getComment());
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                }
            });
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
        public TextView getTextView() {
            return textView;
        }
    }

    public ListAdapter(Record[] dataSet) {
        mDataSet = dataSet;
    }
}

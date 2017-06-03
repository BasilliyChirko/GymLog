package basilliy.gymlog.presentation.exerciseList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import basilliy.gymlog.R;
import basilliy.gymlog.presentation.utils.RecyclerDragAndSwipe;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements RecyclerDragAndSwipe.Adapter {

    private ArrayList<String> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void addData(String text) {
        data.add(text);
    }

    public RecyclerAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.element_involved_muscle, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        RecyclerDragAndSwipe.swapData(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }
}

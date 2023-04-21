package is.hi.hbvg601.team16.sportdemon.ui.playtrackeractivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.R;

public class PlayTrackerRecyclerViewAdapter
        extends RecyclerView.Adapter<PlayTrackerRecyclerViewAdapter.exerciseViewHolder> {
    List<String> data;
    private final LayoutInflater inflater;
    private ItemClickListener mClickListener;

    public PlayTrackerRecyclerViewAdapter(Context context, List<String> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public exerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_tracker_list, parent, false);
        return new exerciseViewHolder(view);
    }

    @Override
    public void
    onBindViewHolder(final exerciseViewHolder viewHolder, final int position) {
        viewHolder.exercise.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class exerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView exercise;

        exerciseViewHolder(View itemView) {
            super(itemView);
            exercise = itemView.findViewById(R.id.exComboName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), getData());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(PlayTrackerRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, List<String> data);
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

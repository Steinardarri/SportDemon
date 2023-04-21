package is.hi.hbvg601.team16.sportdemon.ui.playtrackeractivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.R;

public class PlayTrackerRecyclerViewAdapter
        extends RecyclerView.Adapter<PlayTrackerRecyclerViewAdapter.rowViewHolder> {
    List<String> data;
    private final LayoutInflater inflater;
    private ItemClickListener mClickListener;
    private int selectedIndex = -1;

    public PlayTrackerRecyclerViewAdapter(Context context, List<String> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public rowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_tracker_list, parent, false);
        return new rowViewHolder(view);
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        notifyItemChanged(index);
    }

    @Override
    public void
    onBindViewHolder(final rowViewHolder viewHolder, final int position) {
        TextView row = viewHolder.mTrackerRow;
        row.setText(data.get(position));
        if (position == selectedIndex) {
            row.setBackgroundColor(Color.argb(63,19, 60, 85));
        } else {
            row.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class rowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mTrackerRow;

        rowViewHolder(View itemView) {
            super(itemView);
            mTrackerRow = itemView.findViewById(R.id.tracker_item);
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

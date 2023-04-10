package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.R;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public class WorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutsRecyclerViewAdapter.ViewHolder> {
    private List<Workout> data;
    private final LayoutInflater inflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public WorkoutsRecyclerViewAdapter(Context context, List<Workout> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_workout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String workoutTitle = data.get(position).getTitle();
        holder.workoutTextView.setText(workoutTitle);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return data.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView workoutTextView;

        ViewHolder(View itemView) {
            super(itemView);
            workoutTextView = itemView.findViewById(R.id.workoutTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), getData());
        }
    }

    // convenience method for getting data at click position
    public Workout getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, List<Workout> data);
    }

    public List<Workout> getData() {
        return data;
    }

    public void setData(List<Workout> data) {
        this.data = data;
    }
}

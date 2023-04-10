package is.hi.hbvg601.team16.sportdemon.ui.workoutactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import is.hi.hbvg601.team16.sportdemon.R;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.ExerciseCombo;

public class ExerciseComboRecyclerViewAdapter
        extends RecyclerView.Adapter<ExerciseComboRecyclerViewAdapter.exerciseComboViewHolder> {
    List<ExerciseCombo> data;
    private final LayoutInflater inflater;
    private ItemClickListener mClickListener;

    public ExerciseComboRecyclerViewAdapter(Context context, List<ExerciseCombo> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public exerciseComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_exercise_combo, parent, false);
        return new exerciseComboViewHolder(view);
    }

    @Override
    public void
    onBindViewHolder(final exerciseComboViewHolder viewHolder, final int position) {
        viewHolder.exComboName.setText(data.get(position).getTitle());
        viewHolder.exComboInfoString.setText(data.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class exerciseComboViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView exComboName;
        TextView exComboInfoString;

        exerciseComboViewHolder(View itemView) {
            super(itemView);
            exComboName = itemView.findViewById(R.id.exComboName);
            exComboInfoString = itemView.findViewById(R.id.exComboInfoString);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), getData());
        }
    }

    // convenience method for getting data at click position
    public ExerciseCombo getItem(int id) {
        return data.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ExerciseComboRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, List<ExerciseCombo> data);
    }

    public List<ExerciseCombo> getData() {
        return data;
    }

    public void setData(List<ExerciseCombo> data) {
        this.data = data;
    }
}
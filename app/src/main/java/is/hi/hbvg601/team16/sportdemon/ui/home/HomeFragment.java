package is.hi.hbvg601.team16.sportdemon.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbvg601.team16.sportdemon.R;
import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistences.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistences.entities.Workout;

public class HomeFragment extends Fragment implements WorkoutsRecyclerViewAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    private WorkoutsRecyclerViewAdapter adapter;

    private final User user = createDummyData();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Setja upp RecyclerView fyrir workouts
        List<Workout> workouts = user.getMyWorkouts();
        List<String> titles = new ArrayList<>();
        for (Workout w: workouts) {
            titles.add(w.getTitle());
        }

        RecyclerView recyclerView = binding.workoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WorkoutsRecyclerViewAdapter(getContext(), titles);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "Þú valdir " + adapter.getItem(position) + " á röð " + position, Toast.LENGTH_SHORT).show();
    }

    private User createDummyData() {
        User u = new User("gervimadur","lykilord", "gm@hi.is");

        String[] workoutTitles = {"Kettlebell" , "Monday Lifting", "HIIT Running",
                "Calisthenics", "Yoga"};
        List<Workout> workouts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Workout w = new Workout(workoutTitles[i],0,"");
            workouts.add(w);
        }

        u.setMyWorkouts(workouts);
        return u;
    }
}
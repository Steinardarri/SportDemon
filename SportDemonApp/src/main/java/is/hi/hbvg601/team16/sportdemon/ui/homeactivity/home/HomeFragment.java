package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public class HomeFragment extends Fragment implements WorkoutsRecyclerViewAdapter.ItemClickListener {

    private FragmentHomeBinding mBinding;
    private WorkoutsRecyclerViewAdapter mAdapter;

    private final User mUser = createDummyData();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Setja upp RecyclerView fyrir workouts
        List<Workout> workouts = mUser.getWorkoutList();
        List<String> titles = new ArrayList<>();
        for (Workout w: workouts) {
            titles.add(w.getTitle());
        }

        RecyclerView recyclerView = mBinding.workoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WorkoutsRecyclerViewAdapter(getContext(), titles);
        mAdapter.setClickListener(this);
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "Þú valdir " + mAdapter.getItem(position) + " á röð " + position, Toast.LENGTH_SHORT).show();
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

        u.setWorkoutList(workouts);
        return u;
    }
}
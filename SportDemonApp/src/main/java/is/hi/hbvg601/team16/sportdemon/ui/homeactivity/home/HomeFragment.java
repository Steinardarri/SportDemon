package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbvg601.team16.sportdemon.SignupActivity;
import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private WorkoutsRecyclerViewAdapter mAdapter;

    // Intent code
//    private static final int SIGNUP_SUCCESS = 1;

    private User mUser = new User();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = // TODO: Implement Custom ViewModels
//                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Setja upp RecyclerView fyrir workouts
        List<Workout> workouts = mUser.getWorkoutList();
        List<String> titles = new ArrayList<>();
        if (workouts != null && !workouts.isEmpty()) {
            for (Workout w: workouts) {
                titles.add(w.getTitle());
            }
        }

        RecyclerView recyclerView = mBinding.workoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WorkoutsRecyclerViewAdapter(getContext(), titles);
        mAdapter.setClickListener(
                (View v, int position) ->
                        Toast.makeText(getContext(),
                                "Þú valdir "+mAdapter.getItem(position)+" á röð "+position,
                                Toast.LENGTH_SHORT
                        ).show()
        );
        recyclerView.setAdapter(mAdapter);

        Button signupBtn = mBinding.homeSignupButton;
        signupBtn.setOnClickListener(v ->
                signupResultLauncher.launch(new Intent(getActivity(), SignupActivity.class))
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private final ActivityResultLauncher<Intent> signupResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // TODO: Hugsanlega logga beint inn eftir að hafa búið til account
                    // Annars gera ekkert og skilja eftir autt

                    assert data != null;
                    mUser = (User) data.getSerializableExtra("USER");
                    createDummyData(this.mUser);
                    mBinding.textUserName.setText(mUser.getUsername());
                    mBinding.textUserEmail.setText(mUser.getEmail());
                    refreshList();
                }
            }
    );

    private void createDummyData(User u) {

        String[] workoutTitles = {"Kettlebell" , "Monday Lifting", "HIIT Running",
                "Calisthenics", "Yoga"};
        List<Workout> workouts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Workout w = new Workout(workoutTitles[i],"");
            workouts.add(w);
        }

        u.setWorkoutList(workouts);
    }

    private void refreshList() {
        WorkoutsRecyclerViewAdapter adapter =
                (WorkoutsRecyclerViewAdapter) mBinding.workoutRecyclerView.getAdapter();
        assert adapter != null;
        List<String> titles = new ArrayList<>();
        for (Workout w: mUser.getWorkoutList()) {
            titles.add(w.getTitle());
        }
        adapter.setData(titles);
        mBinding.workoutRecyclerView.setAdapter(adapter);
    }
}
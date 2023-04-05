package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import is.hi.hbvg601.team16.sportdemon.HomeActivity;
import is.hi.hbvg601.team16.sportdemon.LoginActivity;
import is.hi.hbvg601.team16.sportdemon.SignupActivity;
import is.hi.hbvg601.team16.sportdemon.WorkoutActivity;
import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private WorkoutsRecyclerViewAdapter mAdapter;

    // Intent code
    private static final int RESULT_SUCCESS = -1;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = // TODO: Implement Custom ViewModels
//                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        User mUser = ((HomeActivity) requireActivity()).getSportUser(); // Sækir aðal current user
                                                                        // frá Home activity

        // Setja upp RecyclerView fyrir workouts
        RecyclerView recyclerView = mBinding.workoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Workout> workoutList;
        if(mUser != null) {
            workoutList = mUser.getWorkoutList();
        } else {
            workoutList = new ArrayList<>();
        }
        mAdapter = new WorkoutsRecyclerViewAdapter(getContext(), workoutList);
        mAdapter.setClickListener(
                (View v, int position, List<Workout> data) -> {
                    Intent i = new Intent(getActivity(), WorkoutActivity.class);
                    i.putExtra("WORKOUT", data.get(position));
                    workoutResultLauncher.launch(i);
                });
        recyclerView.setAdapter(mAdapter);

        ImageButton addWorkoutBtn = mBinding.addWorkoutButton;
        addWorkoutBtn.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), WorkoutActivity.class);
            i.putExtra("WORKOUT", new Workout());
            workoutResultLauncher.launch(i);
        });

        Button signupBtn = mBinding.homeSignupButton;
        signupBtn.setOnClickListener(v ->
                signupResultLauncher.launch(new Intent(getActivity(), SignupActivity.class))
        );

        Button loginBtn = mBinding.homeLoginButton;
        loginBtn.setOnClickListener(v ->
                loginResultLauncher.launch(new Intent(getActivity(), LoginActivity.class))
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        mAdapter = null;
    }

    private final ActivityResultLauncher<Intent> workoutResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    refreshList();
                }
            }
    );

    private final ActivityResultLauncher<Intent> signupResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    // TODO: Hugsanlega logga beint inn eftir að hafa búið til account
                    // Annars gera ekkert og skilja eftir autt
                }
            }
    );

    private final ActivityResultLauncher<Intent> loginResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();

                    assert data != null;
                    User resultUser = (User) data.getSerializableExtra("USER");
                    mBinding.textUserName.setText(resultUser.getUsername());
                    mBinding.textUserEmail.setText(resultUser.getEmail());
                    ((HomeActivity) requireActivity()).setSportUser(resultUser);
                    // Vista nýjan user sem aðal
                    refreshList();
                }
            }
    );

    private void refreshList() {
        WorkoutsRecyclerViewAdapter adapter =
                (WorkoutsRecyclerViewAdapter) mBinding.workoutRecyclerView.getAdapter();
        assert adapter != null;
        User mUser = ((HomeActivity) requireActivity()).getSportUser();
        adapter.setData(mUser.getWorkoutList());
        mBinding.workoutRecyclerView.setAdapter(adapter);
    }
}
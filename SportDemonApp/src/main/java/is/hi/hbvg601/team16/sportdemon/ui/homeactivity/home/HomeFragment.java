package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dmax.dialog.SpotsDialog;
import is.hi.hbvg601.team16.sportdemon.LoginActivity;
import is.hi.hbvg601.team16.sportdemon.R;
import is.hi.hbvg601.team16.sportdemon.SignupActivity;
import is.hi.hbvg601.team16.sportdemon.WorkoutActivity;
import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.UserService;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.HomeServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.UserServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private WorkoutsRecyclerViewAdapter mAdapter;

    private HomeService mHomeService;
    private WorkoutService mWorkoutService;
    private UserService mUserService;

    // Intent code
    private static final int RESULT_SUCCESS = -1;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = // TODO: Implement Custom ViewModels
//                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mHomeService = new HomeServiceImplementation(nmAPI);
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);
        this.mUserService = new UserServiceImplementation(nmAPI);

        User user = mHomeService.getCurrentUser(getContext());

        // Setja upp RecyclerView fyrir workouts
        RecyclerView recyclerView = mBinding.workoutRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Workout> workoutList;
        if (user != null) {
            mBinding.textUserName.setText(user.getUsername());
            mBinding.textUserEmail.setText(user.getEmail());
            mBinding.addWorkoutButton.setVisibility(View.VISIBLE);
            mBinding.homeLoginButton.setText(getResources().getString(R.string.logout));

            workoutList = user.getWorkoutList();
        } else {
            mBinding.addWorkoutButton.setVisibility(View.INVISIBLE);
            workoutList = new ArrayList<>();
        }
        mAdapter = new WorkoutsRecyclerViewAdapter(getContext(), workoutList);
        mAdapter.setClickListener(
                (View v, int position, List<Workout> data) -> {
                    mHomeService.setCurrentWorkout(data.get(position), getContext());
                    Intent i = new Intent(getActivity(), WorkoutActivity.class);
                    workoutResultLauncher.launch(i);
                });
        recyclerView.setAdapter(mAdapter);

        mBinding.addWorkoutButton.setOnClickListener(v -> {
            User currentUser = mHomeService.getCurrentUser(getContext());

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View vAlert = inflater.inflate(R.layout.dialog_create_workout, null);
            builder.setView(vAlert);
            builder.setTitle("Create Workout");

            EditText nameEdit = vAlert.findViewById(R.id.createWorkoutName);
            EditText descEdit = vAlert.findViewById(R.id.createWorkoutDesc);

            // Set up the buttons
            builder.setPositiveButton("Add", (dialog, which) -> {
                String name = nameEdit.getText().toString();
                String desc = descEdit.getText().toString();
                List<String> uWorkoutTitles = currentUser.getWorkoutList().stream()
                        .map(Workout::getTitle)
                        .collect(Collectors.toList());

                if(name.equals("")) {
                    Toast.makeText(getActivity(),
                            "Please give the workout a name",
                            Toast.LENGTH_LONG
                    ).show();
                } else if(uWorkoutTitles.contains(name)) {
                    Toast.makeText(getActivity(),
                            "A workout already has that name",
                            Toast.LENGTH_LONG
                    ).show();
                } else {
                    Workout newWorkout = new Workout(name, desc);
                    newWorkout.setUser_id(currentUser.getId());

                    SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Setting up new Workout");
                    loadingDialog.show();
                    Call<Workout> callSync = mWorkoutService.saveWorkout(newWorkout);
                    callSync.enqueue(new Callback<Workout>() {
                        @Override
                        public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
                            if (response.isSuccessful()) {
                                // Get response
                                try {
                                    mHomeService.setCurrentWorkout(response.body(), getContext());
                                    Intent i = new Intent(getActivity(), WorkoutActivity.class);
                                    loadingDialog.dismiss();
                                    workoutResultLauncher.launch(i);
                                } catch (Exception e) {
                                    // UI
                                    loadingDialog.dismiss();
                                    Toast.makeText(getContext(),
                                            e.toString(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } else {
                                Toast.makeText(getContext(),
                                        response.message(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                loadingDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(),
                                    t.toString(),
                                    Toast.LENGTH_LONG
                            ).show();
                            loadingDialog.dismiss();
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        mBinding.homeSignupButton.setOnClickListener(v ->
                signupResultLauncher.launch(new Intent(getActivity(), SignupActivity.class))
        );

        mBinding.homeLoginButton.setOnClickListener(v -> {
            if (mHomeService.getCurrentUser(getContext()) == null) {
                // Log in
                loginResultLauncher.launch(new Intent(getActivity(), LoginActivity.class));
            } else {
                // Log out
                SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Logging out");
                loadingDialog.show();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    mHomeService.setCurrentUser(null, getContext());
                    mHomeService.setCurrentWorkout(null, getContext());

                    mBinding.textUserName.setText("");
                    mBinding.textUserEmail.setText("");
                    mBinding.homeLoginButton.setText(getResources().getString(R.string.login_form_title));
                    mBinding.addWorkoutButton.setVisibility(View.INVISIBLE);
                    refreshList();
                    loadingDialog.dismiss();
                },1200);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        mAdapter = null;
        mHomeService = null;
    }

    private final ActivityResultLauncher<Intent> workoutResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                }
                refreshList();
            }
    );

    private final ActivityResultLauncher<Intent> signupResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    // TODO: Hugsanlega logga beint inn eftir að hafa búið til account
                    // Annars gera ekkert og skilja eftir autt
                }
            }
    );

    private final ActivityResultLauncher<Intent> loginResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    assert data != null;
                    User resultUser = (User) data.getSerializableExtra("USER");
                    // TODO: loggaður user hverfur þegar skipt er um view, td Journal.
                    // Þarf betri útfærslu

                    mBinding.textUserName.setText(resultUser.getUsername());
                    mBinding.textUserEmail.setText(resultUser.getEmail());
                    mBinding.addWorkoutButton.setVisibility(View.VISIBLE);

                    // Vista nýjan user sem aðal
                    mHomeService.setCurrentUser(resultUser, getContext());
                    refreshList();
                }
            }
    );

    private void refreshList() {
        WorkoutsRecyclerViewAdapter adapter =
                (WorkoutsRecyclerViewAdapter) mBinding.workoutRecyclerView.getAdapter();
        assert adapter != null;
        List<Workout> workoutList;
        User user = mHomeService.getCurrentUser(getContext());
        if (user != null) {
            workoutList = user.getWorkoutList();
        } else {
            workoutList = new ArrayList<>();
        }
        adapter.setData(workoutList);
        mBinding.workoutRecyclerView.setAdapter(adapter);
    }

    private void saveToServer() {
        // Líklegast óþarfi, einu gögnin sem breytast hjá User eru breytt annarsstaðar
        // þe. Workout og WorkoutResult, og eins lengi og current user í SportDemon.java
        // er uppfærður þá er það nóg
        User user = mHomeService.getCurrentUser(getContext());
        Call<User> callSync = mUserService.editAccount(user);

        SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Saving to server");
        loadingDialog.show();
        callSync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    // UI
                    Toast.makeText(getContext(),
                            "Saving successful",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),
                        t.toString(),
                        Toast.LENGTH_SHORT
                ).show();
                loadingDialog.dismiss();
            }
        });
    }
}

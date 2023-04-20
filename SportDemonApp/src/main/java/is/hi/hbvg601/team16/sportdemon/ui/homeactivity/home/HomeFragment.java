package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import is.hi.hbvg601.team16.sportdemon.WorkoutActivity;
import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.HomeServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;

    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerViewAdapter mAdapter;

    private HomeService mHomeService;
    private WorkoutService mWorkoutService;

    // Intent code
    private static final int RESULT_SUCCESS = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = // TODO: Implement Custom ViewModels
//                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        NetworkManagerAPI nmAPI = new NetworkManagerAPI();
        this.mHomeService = new HomeServiceImplementation(nmAPI);
        this.mWorkoutService = new WorkoutServiceImplementation(nmAPI);

        User user = mHomeService.getCurrentUser(getContext());

        // Setja upp RecyclerView fyrir workouts
        this.mRecyclerView = mBinding.workoutRecyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Workout> workoutList;
        if (user != null) {
            mBinding.textUserName.setText(user.getUsername());
            mBinding.textUserEmail.setText(user.getEmail());
            mBinding.addWorkoutButton.setVisibility(View.VISIBLE);
            mBinding.removeWorkoutButton.setVisibility(View.VISIBLE);
            mBinding.homeLoginButton.setText(getResources().getString(R.string.logout));

            workoutList = user.getWorkoutList();
        } else {
            mBinding.addWorkoutButton.setVisibility(View.INVISIBLE);
            mBinding.removeWorkoutButton.setVisibility(View.INVISIBLE);
            workoutList = new ArrayList<>();
        }
        mAdapter = new WorkoutsRecyclerViewAdapter(getContext(), workoutList);
        mAdapter.setClickListener(
                (View v, int position, List<Workout> data) -> {
                    mHomeService.setCurrentWorkout(data.get(position), getContext());
                    Intent i = new Intent(getActivity(), WorkoutActivity.class);
                    workoutResultLauncher.launch(i);
                });
        mRecyclerView.setAdapter(mAdapter);

        mBinding.addWorkoutButton.setOnClickListener(v -> {
            User currentUser = mHomeService.getCurrentUser(getContext());

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View vAlert = inflater.inflate(R.layout.dialog_create_workout, null);
            builder.setView(vAlert);
            builder.setTitle("Create Workout");

            EditText nameEdit = vAlert.findViewById(R.id.createWorkoutName);
            EditText descEdit = vAlert.findViewById(R.id.createWorkoutDesc);
            EditText restEdit = vAlert.findViewById(R.id.createWorkoutECRest);

            builder.setPositiveButton("Add", (dialog, which) -> {
                String name = nameEdit.getText().toString();
                String desc = descEdit.getText().toString();
                int rest = Integer.parseInt(restEdit.getText().toString());

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
                    Workout newWorkout = new Workout(name, desc, rest);
                    newWorkout.setUser(currentUser.getId());

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
                                    mHomeService.addWorkoutToUser(response.body(), getContext());
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
                                        response.code()+" - "+ response,
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

        mBinding.removeWorkoutButton.setOnClickListener( v -> removeOnList(v,false));

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
                    mBinding.removeWorkoutButton.setVisibility(View.INVISIBLE);
                    removeOnList(mBinding.removeWorkoutButton, true);
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
        mRecyclerView = null;
        mBinding = null;
        mAdapter = null;
        mHomeService = null;
        mWorkoutService = null;
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

    private final ActivityResultLauncher<Intent> loginResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_SUCCESS) {
                    Intent data = result.getData();
                    assert data != null;

                    User resultUser = (User) data.getSerializableExtra("USER");

                    mBinding.textUserName.setText(resultUser.getUsername());
                    mBinding.textUserEmail.setText(resultUser.getEmail());
                    mBinding.addWorkoutButton.setVisibility(View.VISIBLE);
                    mBinding.homeLoginButton.setText(getResources().getString(R.string.logout));

                    // Vista nýjan user sem aðal
                    mHomeService.setCurrentUser(resultUser, getContext());
                    refreshList();
                }
            }
    );

    private void removeOnList(View v, boolean off) {
        if (off || v.isActivated()) {
            v.setActivated(false);
            mAdapter.setClickListener(
                    (View v2, int position, List<Workout> data) -> {
                        mHomeService.setCurrentWorkout(data.get(position), getContext());
                        Intent i = new Intent(getActivity(), WorkoutActivity.class);
                        workoutResultLauncher.launch(i);
                    });
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setBackgroundColor(Color.TRANSPARENT);
        } else {
            v.setActivated(true);
            mAdapter.setClickListener(
                    (View v2, int position, List<Workout> data) -> {
                        Workout w = data.get(position);

                        androidx.appcompat.app.AlertDialog.Builder alert =
                                new androidx.appcompat.app.AlertDialog.Builder(requireContext());
                        alert.setTitle("Delete \"" + w.getTitle() + "\"?");
                        alert.setMessage("Are you sure you want to delete?");

                        alert.setPositiveButton("Yes", (dialog, which) -> {
                            SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Removing Exercise");
                            loadingDialog.show();

                            Call<Void> callSync = mWorkoutService.deleteWorkout(w);
                            callSync.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        // Get response
                                        try {
                                            // Local
                                            mHomeService.removeWorkoutFromUser(w, getContext());

                                            Toast.makeText(getContext(),
                                                    "Workout removed successfully",
                                                    Toast.LENGTH_LONG
                                            ).show();

                                            refreshList();

                                            loadingDialog.dismiss();
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            // UI
                                            Toast.makeText(getContext(),
                                                    e.toString(),
                                                    Toast.LENGTH_LONG
                                            ).show();
                                            loadingDialog.dismiss();
                                            dialog.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(getContext(),
                                                response.code()+" - "+ response,
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        loadingDialog.dismiss();
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                    Toast.makeText(getContext(),
                                            t.toString(),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    loadingDialog.dismiss();
                                    dialog.dismiss();
                                }
                            });
                        });
                        alert.setNegativeButton("NO", (dialog, which) -> {
                            // close dialog
                            dialog.cancel();
                        });
                        alert.show();
                    });

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setBackgroundColor(Color.argb(63,255,0,0));
        }
    }

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

}

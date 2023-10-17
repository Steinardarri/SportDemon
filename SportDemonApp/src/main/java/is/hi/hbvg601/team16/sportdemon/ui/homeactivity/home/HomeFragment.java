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
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import is.hi.hbvg601.team16.sportdemon.LoginActivity;
import is.hi.hbvg601.team16.sportdemon.R;
import is.hi.hbvg601.team16.sportdemon.WorkoutActivity;
import is.hi.hbvg601.team16.sportdemon.databinding.FragmentHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.WorkoutResult;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.HomeServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.WorkoutServiceImplementation;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;

    private RecyclerView mRecyclerView;
    private WorkoutsRecyclerViewAdapter mAdapter;

    private HomeService mHomeService;
    private WorkoutService mWorkoutService;

    // Intent code
    private static final int RESULT_SUCCESS = -1;
    private static final int RESULT_WORKOUT_FINISHED = 12;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        HomeViewModel homeViewModel = // TODO: Implement Custom ViewModels
//                new ViewModelProvider(this).get(HomeViewModel.class);

        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = mBinding.getRoot();

        this.mHomeService = new HomeServiceImplementation(getContext());
        this.mWorkoutService = new WorkoutServiceImplementation(getContext());

        SpotsDialog homeLoadingDialog = new SpotsDialog(getContext(), "Loading");

        // Setja upp RecyclerView fyrir workouts

        this.mRecyclerView = mBinding.workoutRecyclerView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mBinding.addWorkoutButton.setVisibility(View.VISIBLE);
        mBinding.removeWorkoutButton.setVisibility(View.VISIBLE);

        mWorkoutService.getAllWorkouts()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Workout>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        homeLoadingDialog.show();
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Workout> workouts) {
                        mAdapter = new WorkoutsRecyclerViewAdapter(getContext(), workouts);
                        mAdapter.setClickListener(
                                (View v, int position, List<Workout> data) -> {
                                    mHomeService.setCurrentWorkout(data.get(position));
                                    Intent i = new Intent(getActivity(), WorkoutActivity.class);
                                    workoutResultLauncher.launch(i);
                                });
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(),
                                e.toString(),
                                Toast.LENGTH_LONG
                        ).show();
                        homeLoadingDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        homeLoadingDialog.dismiss();
                    }
                });

        mBinding.addWorkoutButton.setOnClickListener(v -> {
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

                List<String> uWorkoutTitles = mAdapter.getData()
                        .stream()
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

                    SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Setting up new Workout");
                    loadingDialog.show();

                    mWorkoutService.saveWorkout(newWorkout)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(workout -> {
                                mHomeService.setCurrentWorkout(workout);
                                mHomeService.addWorkoutToUser(workout);
                                Intent i = new Intent(getActivity(), WorkoutActivity.class);
                                loadingDialog.dismiss();
                                workoutResultLauncher.launch(i);
                            })
                            .doOnError(error -> {
                                Toast.makeText(getContext(),
                                        error.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                loadingDialog.dismiss();
                            })
                            .subscribe();

//                    Call<Workout> callSync = mWorkoutService.saveWorkout(newWorkout);
//                    callSync.enqueue(new Callback<Workout>() {
//                        @Override
//                        public void onResponse(@NonNull Call<Workout> call, @NonNull Response<Workout> response) {
//                            if (response.isSuccessful()) {
//                                // Get response
//                                try {
//                                    mHomeService.setCurrentWorkout(response.body());
//                                    mHomeService.addWorkoutToUser(response.body());
//                                    Intent i = new Intent(getActivity(), WorkoutActivity.class);
//                                    loadingDialog.dismiss();
//                                    workoutResultLauncher.launch(i);
//                                } catch (Exception e) {
//                                    // UI
//                                    loadingDialog.dismiss();
//                                    Toast.makeText(getContext(),
//                                            e.toString(),
//                                            Toast.LENGTH_LONG
//                                    ).show();
//                                }
//                            } else {
//                                Toast.makeText(getContext(),
//                                        response.code()+" - "+ response,
//                                        Toast.LENGTH_SHORT
//                                ).show();
//                                loadingDialog.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Call<Workout> call, @NonNull Throwable t) {
//                            Toast.makeText(getContext(),
//                                    t.toString(),
//                                    Toast.LENGTH_LONG
//                            ).show();
//                            loadingDialog.dismiss();
//                        }
//                    });
                }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });

        mBinding.removeWorkoutButton.setOnClickListener( v -> removeOnList(v,false));

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
                if (result.getResultCode() == RESULT_WORKOUT_FINISHED) {
                    Intent data = result.getData();
                    assert data != null : "No data given";

                    WorkoutResult wr = (WorkoutResult) data.getSerializableExtra("WORKOUTRESULT");

                    SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Saving Workout Result");
                    loadingDialog.show();

                    mWorkoutService.saveWorkoutResult(wr)
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(workoutResult -> {
                                mHomeService.addWorkoutResultToUser(workoutResult);
                                loadingDialog.dismiss();
                                Toast.makeText(getContext(),
                                        "Workout Result Saved",
                                        Toast.LENGTH_LONG
                                ).show();
                            })
                            .doOnError(error -> {
                                Toast.makeText(getContext(),
                                        error.getMessage(),
                                        Toast.LENGTH_SHORT
                                ).show();
                                loadingDialog.dismiss();
                            })
                            .subscribe();

//                    Call<WorkoutResult> callSync = mWorkoutService.saveWorkoutResult(wr);
//                    callSync.enqueue(new Callback<WorkoutResult>() {
//                        @Override
//                        public void onResponse(@NonNull Call<WorkoutResult> call, @NonNull Response<WorkoutResult> response) {
//                            if (response.isSuccessful()) {
//                                // Get response
////                                try {
//                                    WorkoutResult workoutResult = response.body();
//                                    mHomeService.addWorkoutResultToUser(workoutResult);
//
//                                    loadingDialog.dismiss();
//                                    Toast.makeText(getContext(),
//                                            "Workout Result Saved",
//                                            Toast.LENGTH_LONG
//                                    ).show();
////                                } catch (Exception e) {
//////                                    // UI
//////                                    loadingDialog.dismiss();
//////                                    Toast.makeText(getContext(),
//////                                            e.toString(),
//////                                            Toast.LENGTH_LONG
//////                                    ).show();
////                                }
//                            } else {
////                                Toast.makeText(getContext(),
////                                        response.code()+" - "+ response,
////                                        Toast.LENGTH_SHORT
////                                ).show();
//                                loadingDialog.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Call<WorkoutResult> call, @NonNull Throwable t) {
////                            Toast.makeText(getContext(),
////                                    t.toString(),
////                                    Toast.LENGTH_LONG
////                            ).show();
//                            loadingDialog.dismiss();
//                        }
//                    });
                }

//                refreshList();
            }
    );

    private void removeOnList(View v, boolean off) {
        if (off || v.isActivated()) {
            v.setActivated(false);
            mAdapter.setClickListener(
                    (View v2, int position, List<Workout> data) -> {
                        mHomeService.setCurrentWorkout(data.get(position));
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

                        AlertDialog.Builder alert =
                                new AlertDialog.Builder(requireContext());
                        alert.setTitle("Delete \"" + w.getTitle() + "\"?");
                        alert.setMessage("Are you sure you want to delete?");

                        alert.setPositiveButton("Yes", (dialog, which) -> {
                            SpotsDialog loadingDialog = new SpotsDialog(getContext(), "Removing Exercise");
                            loadingDialog.show();

                            mWorkoutService.deleteWorkout(w)
                                    .subscribeOn(Schedulers.io())
                                    .doOnComplete(() -> {
                                        // Local
                                        mHomeService.removeWorkoutFromUser(w);
                                        Toast.makeText(getContext(),
                                                "Workout removed successfully",
                                                Toast.LENGTH_LONG
                                        ).show();
//                                            refreshList();
                                        loadingDialog.dismiss();
                                        dialog.dismiss();
                                    })
                                    .doOnError(error -> {
                                        Toast.makeText(getContext(),
                                                error.getMessage(),
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        loadingDialog.dismiss();
                                        dialog.dismiss();
                                    })
                                    .subscribe();

//                            Call<Void> callSync = mWorkoutService.deleteWorkout(w);
//                            callSync.enqueue(new Callback<Void>() {
//                                @Override
//                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
//                                    if (response.isSuccessful()) {
//                                        // Get response
//                                        try {
//                                            // Local
//                                            mHomeService.removeWorkoutFromUser(w);
//
//                                            Toast.makeText(getContext(),
//                                                    "Workout removed successfully",
//                                                    Toast.LENGTH_LONG
//                                            ).show();
//
////                                            refreshList();
//
//                                            loadingDialog.dismiss();
//                                            dialog.dismiss();
//                                        } catch (Exception e) {
//                                            // UI
//                                            Toast.makeText(getContext(),
//                                                    e.toString(),
//                                                    Toast.LENGTH_LONG
//                                            ).show();
//                                            loadingDialog.dismiss();
//                                            dialog.dismiss();
//                                        }
//                                    } else {
//                                        Toast.makeText(getContext(),
//                                                response.code()+" - "+ response,
//                                                Toast.LENGTH_SHORT
//                                        ).show();
//                                        loadingDialog.dismiss();
//                                        dialog.dismiss();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
//                                    Toast.makeText(getContext(),
//                                            t.toString(),
//                                            Toast.LENGTH_LONG
//                                    ).show();
//                                    loadingDialog.dismiss();
//                                    dialog.dismiss();
//                                }
//                            });
                        });
                        alert.setNegativeButton("NO", (dialog, which) -> {
                            // close dialog
                            dialog.cancel();
                        });
                        alert.show();
                    });

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setBackgroundColor(Color.argb(31,255,0,0));
        }
    }

//    private void refreshList() {
//        // Ætti ekki að þurfa ef flowable virkar rétt
//        WorkoutsRecyclerViewAdapter adapter =
//                (WorkoutsRecyclerViewAdapter) mBinding.workoutRecyclerView.getAdapter();
//        assert adapter != null;
//        mWorkoutService.getAllWorkouts()
//        adapter.setData(workoutList);
//        mBinding.workoutRecyclerView.setAdapter(adapter);
//    }

}

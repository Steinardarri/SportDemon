package is.hi.hbvg601.team16.sportdemon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import is.hi.hbvg601.team16.sportdemon.databinding.ActivityHomeBinding;
import is.hi.hbvg601.team16.sportdemon.databinding.ActivityWorkoutBinding;
import is.hi.hbvg601.team16.sportdemon.persistence.entities.User;
import is.hi.hbvg601.team16.sportdemon.services.HomeService;
import is.hi.hbvg601.team16.sportdemon.services.WorkoutService;
import is.hi.hbvg601.team16.sportdemon.services.implementations.HomeServiceImplementation;
import is.hi.hbvg601.team16.sportdemon.services.implementations.NetworkManagerAPI;

public class WorkoutActivity extends AppCompatActivity {
    @SuppressWarnings("FieldCanBeLocal")
    private ActivityWorkoutBinding binding;

    public WorkoutService mWorkoutService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


}
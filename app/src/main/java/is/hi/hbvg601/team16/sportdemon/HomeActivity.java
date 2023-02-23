package is.hi.hbvg601.team16.sportdemon;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.*;

import is.hi.hbvg601.team16.sportdemon.databinding.ActivityHomeBinding;
import is.hi.hbvg601.team16.sportdemon.persistences.entities.User;
import is.hi.hbvg601.team16.sportdemon.persistences.entities.Workout;
import is.hi.hbvg601.team16.sportdemon.ui.home.WorkoutsRecyclerViewAdapter;

public class HomeActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_add_exercise, R.id.navigation_journal)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}
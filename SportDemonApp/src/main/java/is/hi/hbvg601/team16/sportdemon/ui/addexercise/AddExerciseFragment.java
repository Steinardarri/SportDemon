package is.hi.hbvg601.team16.sportdemon.ui.addexercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import is.hi.hbvg601.team16.sportdemon.databinding.FragmentAddExerciseBinding;

public class AddExerciseFragment extends Fragment {

    private FragmentAddExerciseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddExerciseViewModel addExerciseViewModel =
                new ViewModelProvider(this).get(AddExerciseViewModel.class);

        binding = FragmentAddExerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddExercise;
        addExerciseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
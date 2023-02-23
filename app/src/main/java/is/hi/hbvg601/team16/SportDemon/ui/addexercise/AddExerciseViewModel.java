package is.hi.hbvg601.team16.sportdemon.ui.addexercise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddExerciseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddExerciseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hér kemur yfirlit yfir æfingar");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
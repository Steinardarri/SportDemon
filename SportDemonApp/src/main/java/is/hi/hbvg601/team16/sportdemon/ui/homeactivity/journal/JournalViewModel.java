package is.hi.hbvg601.team16.sportdemon.ui.homeactivity.journal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JournalViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public JournalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hér kemur Journal dagatal; yfirlit yfir kláraðar æfingar");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
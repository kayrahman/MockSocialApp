package neel.com.mocksocialapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;


import java.util.List;

import neel.com.mocksocialapp.db.UserRepository;
import neel.com.mocksocialapp.model.User;

public class UserViewModel extends AndroidViewModel {

    private Context mContext;
    private UserRepository mUserRepository;
    private LiveData<List<User>> mListLiveData = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = new UserRepository(application);
        mListLiveData = mUserRepository.getListLiveData();

    }



    public void fetchMovieListAndStore(){
        mUserRepository.getUserList();
    }


    public LiveData<List<User>> getListLiveData() {
        return mListLiveData;
    }
}

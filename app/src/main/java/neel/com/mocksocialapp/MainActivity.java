package neel.com.mocksocialapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import neel.com.mocksocialapp.ViewModel.UserViewModel;
import neel.com.mocksocialapp.adapter.UserListAdapter;
import neel.com.mocksocialapp.model.OnUserListItemClicked;
import neel.com.mocksocialapp.model.User;
import neel.com.mocksocialapp.ui.UserGalleryActivity;

import static neel.com.mocksocialapp.ui.UserGalleryActivity.INDEX_USER_DETAIL;


public class MainActivity extends AppCompatActivity implements OnUserListItemClicked {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mUserListRv;
    private TextView mEmptyTv;

    private UserListAdapter listAdapter;

    private OnUserListItemClicked mOnUserListItemClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOnUserListItemClicked = this;


        updateDisplay();


        mEmptyTv = findViewById(R.id.tv_ac_main_list_empty_list);
        mEmptyTv.setVisibility(View.GONE);

        mUserListRv = findViewById(R.id.rv_ac_main_user_list);



    }

    private void updateDisplay() {

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

                mUserListRv.setHasFixedSize(true);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                mUserListRv.setLayoutManager(linearLayoutManager);

                Log.i(TAG, "ON_RESPONSE" + "LIVE DATA");


                listAdapter = new UserListAdapter(users, MainActivity.this,mOnUserListItemClicked);
                mUserListRv.setAdapter(listAdapter);



            }
        });

    }



    @Override
    public void onUserListItemClicked(User user) {

        //take user to gallery activity with user bundle

        Intent intent = new Intent(MainActivity.this, UserGalleryActivity.class);
        intent.putExtra(INDEX_USER_DETAIL,user);
        startActivity(intent);


    }


    private boolean isNetworkAvailable(){
        ConnectivityManager manager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        boolean isAvailable=false;

        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

}

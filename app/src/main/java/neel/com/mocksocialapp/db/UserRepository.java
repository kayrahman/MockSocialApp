package neel.com.mocksocialapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import neel.com.mocksocialapp.api.Service;
import neel.com.mocksocialapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepository {

    private static final String TAG = UserRepository.class.getSimpleName();

    private Context mContext;
    private AppDatabase mDatabase;
    private LiveData<List<User>> mUserList = new MutableLiveData<>();

    public UserRepository(Context context) {
        mContext = context;
        mDatabase = AppDatabase.getInstance(context);
        getUserList();

    }

    public void getUserList() {

        Service.getApiResponse().getApiResponse()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code() == HttpURLConnection.HTTP_OK) {

                            //Log.i(TAG, "ON_RESPONSE" + response.body().toString());
                            Log.i(TAG, "ON_RESPONSE" + "HTTP_OK");

                            String api_response = response.body();

                            insertUserDetailIntoRoom(api_response);



                        }else{
                            Log.i(TAG, "ON_RESPONSE" + "SOME ERROR");

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                        Log.i(TAG, "ON_RESPONSE" + "FAILURE");

                    }
                });


    }

    private void insertUserDetailIntoRoom(final String api_response) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                try {
                    JSONObject json_users =new JSONObject(api_response);

                    Log.i(TAG, "ON_RESPONSE" + json_users.length());

                    mDatabase.userDao().deleteUserList();


                    Iterator<String> iter=json_users.keys();
                    while(iter.hasNext()) {
                        String key = iter.next();

                        //retrieve user object from jsonobject
                        JSONObject json_user = json_users.getJSONObject(key);

                        fetchUserDetailFromJson(json_user);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

                Toast.makeText(mContext, "Room Insert Successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable e) {

                Toast.makeText(mContext, "Room Insert Error", Toast.LENGTH_SHORT).show();
            }
        });



    }


    public LiveData<List<User>> getListLiveData(){

        mUserList = mDatabase.userDao().loadAllUser();

        return mUserList;
    }




    private void fetchUserDetailFromJson(JSONObject json_user) throws JSONException {

        String email = json_user.getString("email");
        String about = json_user.getString("about");
        String address = json_user.getString("address");
        String age = json_user.getString("age");
        String phone = json_user.getString("phone");
        String picture = json_user.getString("picture");
        String id = json_user.getString("_id");

        //name
        JSONObject full_name = json_user.getJSONObject("name");
        String first_name = full_name.getString("first");
        String last_name = full_name.getString("last");

        //friends

        ArrayList<User.Friend> friendArrayList = new ArrayList<>();
        JSONArray friends = json_user.getJSONArray("friends");
        for(int i=0;i<friends.length();i++){
            JSONObject json_friend = friends.getJSONObject(i);

            String friend_id = json_friend.getString("id");
            String friend_name = json_friend.getString("name");

            User.Friend friend = new User.Friend(friend_id,friend_name);
            friendArrayList.add(friend);

        }


        //gallery

        ArrayList<String> image_url_list = new ArrayList<>();
        JSONArray gallery_json_array = json_user.getJSONArray("gallery");
        for(int i=0;i<gallery_json_array.length();i++){

            String image_url=gallery_json_array.getString(i);

            image_url_list.add(image_url);

        }


        final User user = new User(id,about,address,age,email,phone,picture,
                first_name,last_name,friendArrayList,image_url_list);


        mDatabase.userDao().insertUser(user);

    }

}

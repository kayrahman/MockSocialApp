package neel.com.mocksocialapp.api;

import java.util.List;

import neel.com.mocksocialapp.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface UserApi {



    public static final String BASE_URL ="https://mock-database-dba0a.firebaseio.com";

    interface Auth{

        @GET("/users.json")
        Call<String> getApiResponse(
        );




    }



}

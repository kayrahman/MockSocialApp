package neel.com.mocksocialapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static neel.com.mocksocialapp.api.UserApi.BASE_URL;


public class Service {

    public static UserApi.Auth getUserApi(){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApi.Auth.class);

    }


    public static UserApi.Auth getApiResponse(){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(UserApi.Auth.class);

    }

}

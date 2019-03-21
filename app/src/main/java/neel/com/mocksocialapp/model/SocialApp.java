package neel.com.mocksocialapp.model;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

public class SocialApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

/*

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
*/


    }
}

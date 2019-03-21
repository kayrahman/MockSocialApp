package neel.com.mocksocialapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import neel.com.mocksocialapp.R;
import neel.com.mocksocialapp.adapter.GalleryAdapter;
import neel.com.mocksocialapp.adapter.UserListAdapter;
import neel.com.mocksocialapp.model.OnUserGalleryItemClick;
import neel.com.mocksocialapp.model.User;

import static neel.com.mocksocialapp.ui.GalleryPreviewActivity.IMAGE_POSITION;
import static neel.com.mocksocialapp.ui.GalleryPreviewActivity.IMAGE_URLS;

public class UserGalleryActivity extends AppCompatActivity implements OnUserGalleryItemClick {

    public static final String INDEX_USER_DETAIL ="user_detail";
    private RecyclerView mUserGalleryRv;

    private ArrayList<String> mImageurls =new ArrayList<>();

    private User mUser;

    private CircleImageView mUserCiv;
    private TextView mUserNameTv,mUserAboutTv;
    private OnUserGalleryItemClick mOnUserGalleryItemClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gallery);

        mOnUserGalleryItemClick = this;

        Intent intent = getIntent();
        mUser = intent.getParcelableExtra(INDEX_USER_DETAIL);

        //init views
        mUserCiv = findViewById(R.id.civ_ac_user_gallery_user_image);
        mUserNameTv = findViewById(R.id.tv_ac_user_gallery_user_name);
        mUserAboutTv = findViewById(R.id.tv_ac_user_gallery_user_about);
        mUserGalleryRv = findViewById(R.id.rv_ac_user_gallery);

        //init fields
        mUserNameTv.setText(mUser.getFirst_name()+" "+mUser.getLast_name());
        mUserAboutTv.setText(mUser.getAbout());
        Picasso.with(this)
                .load(mUser.getPicture())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(mUserCiv, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                        Picasso.with(getApplicationContext())
                                .load(mUser.getPicture())
                                .placeholder(R.drawable.male)
                                .into(mUserCiv);

                    }
                });


        //for gallery recyclerview

        DisplayMetrics displayMetrics=getResources().getDisplayMetrics();
        float dpWidth= displayMetrics.widthPixels/displayMetrics.density;

        int numOfColumns=(int)(dpWidth/120);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,numOfColumns);
        mUserGalleryRv.setLayoutManager(layoutManager);

        mUserGalleryRv.setHasFixedSize(true);

        GalleryAdapter adapter = new GalleryAdapter(mUser.getGallery_urls(), this,mOnUserGalleryItemClick);
        mUserGalleryRv.setAdapter(adapter);




    }


    @Override
    public void onUserGalleryItemClick(int index) {

        Intent intent = new Intent(UserGalleryActivity.this,GalleryPreviewActivity.class);
        intent.putExtra(IMAGE_POSITION,index);
        intent.putStringArrayListExtra(IMAGE_URLS,mUser.getGallery_urls());
        startActivity(intent);


    }
}

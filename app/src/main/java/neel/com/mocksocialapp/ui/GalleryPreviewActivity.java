package neel.com.mocksocialapp.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import neel.com.mocksocialapp.R;

public class GalleryPreviewActivity extends AppCompatActivity {

    public static final String IMAGE_URLS = "image_urls";
    public static final String IMAGE_POSITION = "position";

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private ArrayList<String> mImageUrls;
    private int current_position = 0;
    private TextView dots[];
    private LinearLayout dotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_preview);

        Intent intent = getIntent();
        mImageUrls = intent.getStringArrayListExtra(IMAGE_URLS);
        current_position = intent.getIntExtra(IMAGE_POSITION,0);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        viewPager.setCurrentItem(current_position);

        addBottomDots(current_position);



    }


    public class ViewPagerAdapter extends PagerAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter(Context context) {
            mContext = context;
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = layoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = itemView.findViewById(R.id.imageview);

           // imageView.setImageResource(mImageUrls[position]);

            Picasso.with(itemView.getContext())
                    .load(mImageUrls.get(position))
                    .placeholder(R.drawable.image_place_holder_large)
                    .into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public int getCount() {
            return mImageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v=(View)object;
            container.removeView(v);
        }
    }


    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener(){


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addBottomDots(position);

          /*  addBottomDots(position);
            if(position==layouts.length-1){
                next.setText("PROCEED");
                skip.setVisibility(View.GONE);
            }else{

                next.setText("NEXT");
                skip.setVisibility(View.VISIBLE);

            }*/

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void addBottomDots(int position){

        dots=new TextView[mImageUrls.size()];
        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++){

            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(getResources().getColor( R.color.dot_inactive1));
            dotsLayout.addView(dots[i]);

        }

        if(dots.length>0){

            dots[position].setTextColor(getResources().getColor( R.color.dot_light_active1));

        }
    }


}

package neel.com.mocksocialapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import neel.com.mocksocialapp.R;
import neel.com.mocksocialapp.model.OnUserGalleryItemClick;
import neel.com.mocksocialapp.model.OnUserListItemClicked;
import neel.com.mocksocialapp.model.User;

/**
 * Created by neel on 23/12/2015.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{

    private ArrayList<String> mImageUrls;
    private Context mContext;
    private OnUserGalleryItemClick mOnUserGalleryItemClick;



    public GalleryAdapter(ArrayList<String> images, Context context,OnUserGalleryItemClick onClick) {
        mImageUrls = images;
        mContext = context;
        mOnUserGalleryItemClick = onClick;

    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gallery,null,false);
        GalleryViewHolder vw = new GalleryViewHolder(v);

        return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {

        holder.bindViewHolder(position);

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements OnClickListener{


        private ImageView mGalleryImage;
        private int mIndex;

        public GalleryViewHolder(View itemView) {
            super(itemView);

            mGalleryImage = itemView.findViewById(R.id.iv_list_item_gallery_image);

            itemView.setOnClickListener(this);

        }


        public void bindViewHolder(final int position){
            mIndex =position;



            Picasso.with(itemView.getContext())
                    .load(mImageUrls.get(position))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.image_place_holder_small)
                    .into(mGalleryImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(itemView.getContext())
                                    .load(mImageUrls.get(position))
                                    .placeholder(R.drawable.image_place_holder_small)
                                    .into(mGalleryImage);

                        }
                    });


        }

        @Override
        public void onClick(View view) {

            mOnUserGalleryItemClick.onUserGalleryItemClick(mIndex);

        }
    }


}

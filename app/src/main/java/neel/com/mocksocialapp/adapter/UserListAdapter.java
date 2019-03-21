package neel.com.mocksocialapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import neel.com.mocksocialapp.R;
import neel.com.mocksocialapp.model.OnUserListItemClicked;
import neel.com.mocksocialapp.model.User;

/**
 * Created by neel on 23/12/2015.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    private List<User> mUsers;
    private Context mContext;
    private OnUserListItemClicked mOnUserListItemClicked;

    public UserListAdapter(List<User> users, Context context,OnUserListItemClicked onUserListItemClicked) {
        mUsers = users;
        mContext = context;
        mOnUserListItemClicked = onUserListItemClicked;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user,null,false);
        UserViewHolder vw = new UserViewHolder(v);

        return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.bindViewHolder(position);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mUserNameTv,mUserPhoneTv;
        private CircleImageView mUserCiv;
        private int mIndex;

        public UserViewHolder(View itemView) {
            super(itemView);

            mUserNameTv = itemView.findViewById(R.id.tv_list_item_user_name);
            mUserPhoneTv = itemView.findViewById(R.id.tv_list_item_user_phone);
            mUserCiv = itemView.findViewById(R.id.civ_list_item_user_image);

            itemView.setOnClickListener(this);

        }


        public void bindViewHolder(final int position){
            mIndex =position;
            mUserNameTv.setText(mUsers.get(position).getFirst_name()+" "+mUsers.get(position).getLast_name());
            mUserPhoneTv.setText(mUsers.get(position).getPhone());

            Picasso.with(itemView.getContext())
                    .load(mUsers.get(position).getPicture())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.male)
                    .into(mUserCiv, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Log.i("PICASSO","ONERROR");

                            Picasso.with(itemView.getContext())
                                    .load(mUsers.get(position).getPicture())
                                    .placeholder(R.drawable.male)
                                    .into(mUserCiv);
                        }
                    });


           /* Picasso.with(itemView.getContext())
                    .load(mUsers.get(position).getPicture())
                    .placeholder(R.drawable.male)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mUserCiv, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso.with(itemView.getContext())
                                    .load(mUsers.get(position).getPicture())
                                    .placeholder(R.drawable.male)
                                    .into(mUserCiv);

                        }
                    });*/


        }

        @Override
        public void onClick(View view) {

            mOnUserListItemClicked.onUserListItemClicked(mUsers.get(mIndex));

          //  Toast.makeText(mContext, mUsers.get(mIndex).getFriends().get(0).name, Toast.LENGTH_SHORT).show();

        }
    }


}

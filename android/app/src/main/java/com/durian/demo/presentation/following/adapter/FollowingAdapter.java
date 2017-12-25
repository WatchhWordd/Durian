package com.durian.demo.presentation.following.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.durian.demo.R;
import com.durian.demo.data.net.bean.UserInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/14
 */

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder> {

    private View view;
    private Context context;
    private ArrayList<UserInfo> reposInfos;

    public FollowingAdapter(Context context, ArrayList<UserInfo> reposInfos) {
        this.context = context;
        this.reposInfos = reposInfos;
    }

    @Override
    public FollowingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_following_info, parent, false);
        return new FollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowingViewHolder holder, int position) {
        if (holder != null) {
            holder.followingName.setText(reposInfos.get(position).getName());
            Glide.with(context)
                    .load(reposInfos.get(position).getAvatarUrl())
                    .placeholder(R.drawable.ic_perm_identity_white_24dp)
                    .error(R.drawable.ic_perm_identity_white_24dp)
                    .into(holder.avaterView);
            holder.followerLoginName.setText(reposInfos.get(position).getLogin());
            if (reposInfos.get(position).getBio() != null) {
                holder.userBlog.setText(reposInfos.get(position).getBio());
            }
            holder.companyName.setText(reposInfos.get(position).getCompany());
            holder.locationName.setText(reposInfos.get(position).getLocation());
        }
    }

    @Override
    public int getItemCount() {
        return reposInfos.size();
    }

    public static class FollowingViewHolder extends RecyclerView.ViewHolder {

        private ImageView avaterView;
        private TextView followingName;
        private TextView followerLoginName;
        private TextView userBlog;
        private TextView companyName;
        private TextView locationName;

        public FollowingViewHolder(View itemView) {
            super(itemView);
            avaterView = itemView.findViewById(R.id.id_user_avatar);
            followingName = itemView.findViewById(R.id.id_following_name);
            followerLoginName = itemView.findViewById(R.id.id_following_login_name);
            userBlog = itemView.findViewById(R.id.id_user_bio);
            companyName = itemView.findViewById(R.id.id_user_company_name);
            locationName = itemView.findViewById(R.id.id_user_location_name);
        }
    }
}

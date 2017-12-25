package com.durian.demo.presentation.followers.adapter;

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

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder> {

    private View view;
    private Context context;
    private ArrayList<UserInfo> reposInfos;

    public FollowersAdapter(Context context, ArrayList<UserInfo> reposInfos) {
        this.context = context;
        this.reposInfos = reposInfos;
    }

    @Override
    public FollowersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_followers_info, parent, false);
        return new FollowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowersViewHolder holder, int position) {
        if (holder != null) {
            holder.followerName.setText(reposInfos.get(position).getName());
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

    public static class FollowersViewHolder extends RecyclerView.ViewHolder {
        private ImageView avaterView;
        private TextView followerName;
        private TextView followerLoginName;
        private TextView userBlog;
        private TextView companyName;
        private TextView locationName;

        public FollowersViewHolder(View itemView) {
            super(itemView);
            avaterView = itemView.findViewById(R.id.id_user_avatar);
            followerName = itemView.findViewById(R.id.id_follower_name);
            followerLoginName = itemView.findViewById(R.id.id_follower_login_name);
            userBlog = itemView.findViewById(R.id.id_user_bio);
            companyName = itemView.findViewById(R.id.id_user_company_name);
            locationName = itemView.findViewById(R.id.id_user_location_name);
        }
    }
}
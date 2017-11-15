package com.durian.demo.presentation.stars.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.durian.demo.R;
import com.durian.demo.data.net.bean.ReposInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/14
 */

public class StarsAdapter extends RecyclerView.Adapter<StarsAdapter.StarsViewHolder> {

    private View view;
    private Context context;
    private ArrayList<ReposInfo> reposInfos;

    public StarsAdapter(Context context, ArrayList<ReposInfo> reposInfos) {
        this.context = context;
        this.reposInfos = reposInfos;
    }

    @Override
    public StarsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_starred_repo, parent, false);
        }
        return new StarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarsViewHolder holder, int position) {

        if (holder != null) {
            holder.titleView.setText(reposInfos.get(position).getName());
            holder.contentView.setText(reposInfos.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return reposInfos.size();
    }

    public static class StarsViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView contentView;
        private ImageView iconStars;
        private ImageView iconRadio;

        public StarsViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.id_stars_repo_title);
            contentView = itemView.findViewById(R.id.id_stars_repo_desc);
            iconStars = itemView.findViewById(R.id.id_icon_star);
            iconRadio = itemView.findViewById(R.id.id_icon_radio);
        }
    }
}

package com.durian.demo.presentation.overview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durian.demo.R;
import com.durian.demo.data.net.bean.ReposInfo;

import java.util.ArrayList;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/15
 */

public class OverRepoAdapter extends RecyclerView.Adapter<OverRepoAdapter.OverRepoViewHolder> {

    private View view;
    private Context context;
    private ArrayList<ReposInfo> reposInfos;

    public OverRepoAdapter(Context context, ArrayList<ReposInfo> reposInfos) {
        this.context = context;
        this.reposInfos = reposInfos;
    }

    @Override
    public OverRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_popular_repo,
                    null, false);
        }
        return new OverRepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OverRepoViewHolder holder, int position) {
        holder.repo_title
                .setText(reposInfos.get(position).getName());
        holder.repo_desc
                .setText(reposInfos.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return reposInfos.size();
    }

    static class OverRepoViewHolder extends RecyclerView.ViewHolder {

        private TextView repo_title;
        private TextView repo_desc;

        public OverRepoViewHolder(View itemView) {
            super(itemView);
            repo_title = itemView.findViewById(R.id.id_over_repo_title);
            repo_desc = itemView.findViewById(R.id.id_over_repo_desc);
        }
    }
}

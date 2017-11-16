package com.durian.demo.presentation.repositories.adpter;

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
 * @date 2017/11/13
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {

    private static final int ITEM_VIEW_TYPE_NORMAL = 0;
    private Context context;
    private ArrayList<ReposInfo> reposInfoArrayList;

    public RepositoriesAdapter(Context context, ArrayList<ReposInfo> reposInfoArrayList) {
        this.context = context;
        this.reposInfoArrayList = reposInfoArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == ITEM_VIEW_TYPE_NORMAL) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_repo, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder != null) {
            holder.repoTitle.setText(reposInfoArrayList.get(position).getName());
            holder.repoDesc.setText(reposInfoArrayList.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return reposInfoArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_NORMAL;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView repoTitle;
        private TextView repoDesc;
        private ImageView starView;
        private ImageView radioView;

        public ViewHolder(View itemView) {
            super(itemView);
            repoTitle = itemView.findViewById(R.id.id_repo_title);
            repoDesc = itemView.findViewById(R.id.id_repo_desc);
            starView = itemView.findViewById(R.id.id_icon_star);
            radioView = itemView.findViewById(R.id.id_icon_radio);
        }
    }
}

package com.durian.demo.presentation.overview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhangyb
 * @description
 * @date 2017/11/15
 */

public class OverEventAdapter extends RecyclerView.Adapter<OverEventAdapter.OverEventViewHolder> {

    @Override
    public OverEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(OverEventViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class OverEventViewHolder extends RecyclerView.ViewHolder {
        public OverEventViewHolder(View itemView) {
            super(itemView);
        }
    }
}

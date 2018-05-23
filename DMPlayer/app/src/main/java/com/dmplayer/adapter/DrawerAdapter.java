/*
 * This is the source code of DMPLayer for Android v. 1.0.0.
 * You should have received a copy of the license in this archive (see LICENSE).
 * Copyright @Dibakar_Mistry, 2015.
 */
package com.dmplayer.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.dmplayer.R;
import com.dmplayer.models.DrawerItem;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    private ArrayList<DrawerItem> drawerItems;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case在这种情况下，每个数据项只是一个字符串。

        public ViewHolder(View view) {
            super(view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)提供一个合适的构造函数（取决于数据集的类型）
    public DrawerAdapter(ArrayList<DrawerItem> drawerItems) {
        this.drawerItems = drawerItems;
    }

    // Create new views (invoked by the layout manager)创建新视图（由布局管理器调用）
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view创建新视图
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);
        // set the view's size, margins, paddings and layout parameters设置视图的大小、边距、填充和布局参数

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)替换视图的内容，由布局管理器调用
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //在这个位置从数据集获取元素
        //用该元素替换视图的内容
        final TextView title = (TextView) holder.itemView.findViewById(R.id.textViewDrawerItemTitle);
        ImageView icon = (ImageView) holder.itemView.findViewById(R.id.imageViewDrawerIcon);

        FrameLayout item_divider = (FrameLayout) holder.itemView.findViewById(R.id.item_divider);
        item_divider.setVisibility((position == 3) ? View.VISIBLE : View.GONE);

        title.setText(drawerItems.get(position).getTitle());//标题
        icon.setImageDrawable(drawerItems.get(position).getIcon());//图标

//        icon.setVisibility((position < 3) ? View.VISIBLE : View.GONE);设置图标可见
    }

    // Return the size of your dataset (invoked by the layout manager)返回数据集的大小（由布局管理器调用）
    @Override
    public int getItemCount() {
        return drawerItems.size();
    }
}



package com.nlinks.nlframe.widget;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.nlinks.nlframe.R;

import java.util.List;

public class ListPopupView extends PartShadowPopupView {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private OnListClickListener mClickListener;

    public interface OnListClickListener {

        void onClick(int position);
    }

    public ListPopupView(@NonNull Context context, List<String> list, OnListClickListener clickListener) {
        super(context);
        this.mList = list;
        this.mClickListener = clickListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_list;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mRecyclerView = findViewById(R.id.popup_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final EasyAdapter<String> commonAdapter = new EasyAdapter<String>(mList, android.R.layout.simple_list_item_1) {

            @Override
            protected void bind(@NonNull ViewHolder holder, @NonNull String data, int position) {
                holder.setText(android.R.id.text1, data);
                holder.itemView.setOnClickListener(v -> {
                            mClickListener.onClick(position);
                            dismiss();
                        }
                );
            }

        };
        mRecyclerView.setAdapter(commonAdapter);
    }
}

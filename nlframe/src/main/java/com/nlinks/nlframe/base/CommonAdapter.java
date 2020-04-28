package com.nlinks.nlframe.base;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 单一布局通用数据适配器
 *
 * @author Administrator
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    public List<T> mDataList;
    public Context mContext;
    public Activity mActivity;
    private SparseArray<CommonViewHolder> mViewHolder = new SparseArray<>();
    public OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CommonAdapter(Context context, List<T> dataList) {
        this.mDataList = dataList;
        this.mContext = context;
    }

    public CommonAdapter(Context context, Activity activity, List<T> dataList) {
        this.mContext = context;
        this.mActivity = activity;
        this.mDataList = dataList;
    }

    /**
     * 获取列表控件的视图id(由子类负责完成)
     */
    public abstract int getLayoutId(int viewType);

    /**
     * 更新itemView视图(由子类负责完成)
     */
    public abstract void convert(@NotNull CommonViewHolder holder, @NotNull T t, @NotNull int position);

    public T getItem(int position) {
        if (mDataList == null || position < 0 || mDataList.size() <= position) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        CommonViewHolder holder = CommonViewHolder.getViewHolder(parent, layoutId);

        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        T itemModel = getItem(position);
        mViewHolder.put(position, holder);
        //更新itemView视图
        convert(holder, itemModel, position);
        holder.itemView.setOnClickListener(v -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void destroyAdapter() {
        if (mDataList != null) {
            mDataList.clear();
        }
        mDataList = null;
    }

    public CommonViewHolder getCommonViewHolder(int position) {
        return mViewHolder.get(position);
    }

    public void addMoreItem(List<T> newData) {
        mDataList.addAll(newData);
        notifyDataSetChanged();
    }
}

package com.nlinks.nlframe.widget.popup

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxj.xpopup.core.BottomPopupView
import com.nlinks.nlframe.R
import kotlinx.android.synthetic.main.popub_ios_bottom_view.view.*

/**
 * 项目名称：security-guard-android
 * 类描述：TOOD
 * 创建时间：2020/4/10 16:04
 * @author gweibin@linewell.com
 */
class IOSBottomPopupView(context: Context, data: MutableList<String>, listener: OnClickListener) :
    BottomPopupView(context) {

    private var mContext = context
    private var mData = data
    private var mListener = listener

    interface OnClickListener {

        fun onItemClick(position: Int)
    }

    override fun getImplLayoutId() = R.layout.popub_ios_bottom_view

    override fun onCreate() {
        super.onCreate()
        val adapter = PopupListAdapter(mContext, mData)
        ios_bottom_rv_list.layoutManager = LinearLayoutManager(mContext)
        ios_bottom_rv_list.adapter = adapter
        adapter.setOnItemClickListener { _, position ->
            mListener.onItemClick(position)
            dismiss()
        }
        popup_bottom_tv_cancel.setOnClickListener {
            dismiss()
        }
    }

}
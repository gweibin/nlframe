package com.nlinks.nlframe.widget.popup

import android.content.Context
import android.widget.TextView
import com.nlinks.nlframe.R
import com.nlinks.nlframe.base.CommonAdapter
import com.nlinks.nlframe.base.CommonViewHolder


/**
 * 项目名称：security-guard-android
 * 类描述：TOOD
 * 创建时间：2020/4/10 16:20
 * @author gweibin@linewell.com
 */
class PopupListAdapter(context: Context?, dataList: List<String>) :
    CommonAdapter<String>(context, dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.popub_bottom_list

    override fun convert(holder: CommonViewHolder, t: String, position: Int) {
        val tv = holder.getView<TextView>(R.id.popup_bottom_tv)
        tv.text = t
    }

}
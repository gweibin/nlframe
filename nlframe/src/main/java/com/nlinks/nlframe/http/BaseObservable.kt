package com.nlinks.nlframe.http

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 项目名称：minielectric-android4
 * 类描述：Observable 封装整个数据流产生作用的操作
 * 创建人：gweibin@linewell.com
 * 创建时间：2018/1/8 10:00
 */

class BaseObservable<T> : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}

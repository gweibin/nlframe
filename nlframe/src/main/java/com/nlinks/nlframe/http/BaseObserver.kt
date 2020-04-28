package com.nlinks.nlframe.http

import android.content.Context
import android.net.ParseException
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 项目名称：minielectric-android
 * 类描述：封装观察者，在这边处理网络请求
 * 创建人：gweibin@linewell.com
 * 创建时间：2018/1/6 11:33
 */

abstract class BaseObserver<T>(private val context: Context?) : Observer<HttpResult<T>> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(result: HttpResult<T>) {
        try {
            if (result.status == 2) {
                onHandleError(ServiceConfig.NOT_LOGIN, "未登录")
            } else if (result.status == 1 && null != result.content) {
                onHandleSuccess(result.content)
            } else if (null == result.message) {
                onHandleError(result.code, "返回数据为空")
            } else {
                onHandleError(result.code, result.message)
            }
        } catch (e: Exception) {
            onHandleError(601, e.toString())
        }
    }

    override fun onError(e: Throwable) {
        if (e is UnknownHostException || e is ConnectException) {
            onHandleError(ServiceConfig.NO_NETWORK, "网络不可用")
        } else if (e is SocketTimeoutException) {
            onHandleError(ServiceConfig.TIME_OUT, "网络连接超时")
        } else if (e is JsonParseException || e is ParseException || e is JSONException || e is JsonIOException) {
            onHandleError(ServiceConfig.PARSING_ERROR, "数据解析错误")
        } else {
            onHandleError(ServiceConfig.UNKNOWN_ERROR, "未知错误")
        }
    }

    override fun onComplete() {
    }

    abstract fun onHandleSuccess(data: T)

    open fun onHandleError(code: Int, message: String) {
        ToastUtils.showShort(message)
    }
}

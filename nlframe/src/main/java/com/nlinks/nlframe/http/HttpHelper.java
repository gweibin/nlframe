package com.nlinks.nlframe.http;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 项目名称：Minielectric
 * 类描述：TOOD
 * 创建时间：2018/6/27 11:31
 *
 * @author gweibin@linewell.com
 */
public class HttpHelper {

    private static final int DEFAULT_TIMEOUT = 15;
    private volatile static HttpHelper httpHelper;
    private Retrofit mRetrofit;

    private HttpHelper() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //添加拦截器
        if (AppUtils.isAppDebug()) {
            okHttpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    //获得请求信息，此处如有需要可以添加headers信息
                    Request request = chain.request();
                    //添加Cookie信息
                    request.newBuilder().addHeader("Cookie", "aaaa");
                    ////打印请求信息
                    LogUtils.i("url:" + request.url());
                    LogUtils.i("method:" + request.method());
                    LogUtils.i("request-body:" + request.body());
                    ////记录请求耗时
                    long startNs = System.nanoTime();
                    Response response;
                    try {
                        //发送请求，获得相应，
                        response = chain.proceed(request);
                    } catch (Exception e) {
                        throw e;
                    }
                    long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
                    //打印请求耗时
                    LogUtils.i("耗时:" + tookMs + "ms");
                    //使用response获得headers(),可以更新本地Cookie。
                    LogUtils.i("headers==========");
                    Headers headers = response.headers();
                    LogUtils.i(headers.toString());

                    //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
                    ResponseBody responseBody = response.body();

                    //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
                    BufferedSource source = responseBody.source();
                    // Buffer the entire body.
                    source.request(Long.MAX_VALUE);
                    //获得返回的数据
                    Buffer buffer = source.buffer();
                    //使用前clone()下，避免直接消耗
                    String result = buffer.clone().readString(Charset.forName("UTF-8"));
                    LogUtils.i("result:" + result);
                    return response;
                }
            });
        }
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                //对http请求结果进行统一的预处理
                .addConverterFactory(GsonConverterFactory.create())
                //对rxjava提供支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ServiceConfig.baseUrl)
                .build();
    }


    private static Retrofit getRetrofit() {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper.mRetrofit;
    }

    public static <T> T create(final Class<T> service) {
        return getRetrofit().create(service);
    }

    public static <T> T reCreate(final Class<T> service) {
        return reBuildRetrofit().create(service);
    }

    public static Retrofit reBuildRetrofit() {
        httpHelper = new HttpHelper();
        return httpHelper.mRetrofit;
    }

}

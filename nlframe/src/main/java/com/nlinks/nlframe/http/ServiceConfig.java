package com.nlinks.nlframe.http;

/**
 * Created by sl on 2016/10/12.
 */

public class ServiceConfig {

    /**
     * 服务地址
     */
    public static String baseUrl;


    /**
     * 服务返回的状态码
     */
    public static final int NOT_LOGIN = 100;
    public static final int UNKNOWN_ERROR = 600;
    public static final int NO_NETWORK = 601;
    public static final int TIME_OUT = 602;
    public static final int PARSING_ERROR = 603;

    public void init(String serviceUrl) {
        this.baseUrl = serviceUrl;
    }
}

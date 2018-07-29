package cn.kevinwu.lib_pay_core.core;

import android.app.Application;

public class MultiPayContext {
    private Application context;//全局上下文
    private String wxPayEntityPath;

    private PayResultCallBack payResultCallBack;

    private String extraData;

    private MultiPayContext() {

    }

    private static class Single {
        static MultiPayContext instance = new MultiPayContext();
    }

    public static MultiPayContext getInstance() {
        return Single.instance;
    }

    protected void renewSingleStatus(Application application) {
        this.context = application;
        this.payResultCallBack = null;
    }

    public void setPayResultCallBack(PayResultCallBack payResultCallBack) {
        this.payResultCallBack = payResultCallBack;
    }

    public void setWxPayEntityPath(String wxPayEntityPath) {
        this.wxPayEntityPath = wxPayEntityPath;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getExtraData() {
        return extraData;
    }

    public PayResultCallBack getPayResultCallBack() {
        return payResultCallBack;
    }

    public String getWxPayEntityPath() {
        return wxPayEntityPath;
    }

    public Application getContext() {
        return context;
    }
}

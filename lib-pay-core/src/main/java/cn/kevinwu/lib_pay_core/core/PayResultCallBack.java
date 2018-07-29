package cn.kevinwu.lib_pay_core.core;

public interface PayResultCallBack {
    void onSuccess();//支付成功

    void onError(int errorCode);//支付错误

    void onCancel();//支付取消
}

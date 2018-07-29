package cn.kevinwu.lib_pay_wxpay;

public class WXPayStatusCode {
    public static final int WX_SUPPORT = 0;
    public static final int WX_NO_INSTALL = 1;//未安装微信
    public static final int WX_VERSION_INVALID = 2;//微信版本过低
    public static final int WX_PAY_PARAM_INVALID = 3;//参数错误
    public static final int WX_PAY_ERROR = 4;//微信支付错误

    public static final int WX_PAY_APPID_NULL = 5;//未设置appid
}

package cn.kevinwu.lib_pay_pingxx;

public class PingxxStatusCode {
    public static final int PINGXX_PAY_PARAM_INVALID = 1;//ping++参数错误
    public static final int PINGXX_PAY_FAIL = 2;//ping++支付错误
    public static final int PINGXX_PAY_INVALID = 3;//ping++支付插件未安装
    public static final int PINGXX_PAY_UNKNOWN = 4;//ping++进程异常被杀死错误
}

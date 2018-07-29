package cn.kevinwu.lib_pay_core.paytype;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;
import cn.kevinwu.lib_pay_core.core.PayResultCallBack;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsPayType<T> {
    protected String paramStr;
    protected Map<String, String> paramMap;
    protected boolean isSandbox = false;//是否使用沙箱

    protected String extraData;//额外参数，如微信appid，微信支付特有

    @SuppressWarnings("unchecked")
    public T setAllParamStr(String paramStr) {
        this.paramStr = paramStr;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setParam(String key, String value) {
        if (paramMap == null) {
            //如果对应paramMap为null则先初始化
            paramMap = new HashMap<>();
        }
        paramMap.put(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setAllParamMap(Map<String, String> paramMap) {
        if (this.paramMap == null) this.paramMap = new HashMap<>();
        this.paramMap.clear();
        this.paramMap.putAll(paramMap);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setSandbox(boolean isUseSandbox) {
        this.isSandbox = isUseSandbox;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setExtraData(String extraData) {
        this.extraData = extraData;
        MultiPayContext.getInstance().setExtraData(extraData);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public void execute(PayResultCallBack payResultCallBack) {
        MultiPayContext.getInstance().setPayResultCallBack(payResultCallBack);
        startTypePay();
    }

    protected abstract void startTypePay();
}

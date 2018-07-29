package cn.kevinwu.lib_pay_alipay;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;

import java.util.Map;

public class AliPayHandler extends Handler {

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 1: {
                Map<String, String> result = (Map<String, String>) msg.obj;
                if (result != null) {
                    String resultStatus = result.get("resultStatus");
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //sdk成功回调
                        if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                            MultiPayContext.getInstance().getPayResultCallBack().onSuccess();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                            MultiPayContext.getInstance().getPayResultCallBack().onCancel();
                    } else {
                        if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                            MultiPayContext.getInstance().getPayResultCallBack().onError(AliPayStatusCode.ALI_PAY_ERROR);
                    }
                }
                break;
            }
            default:
                break;
        }
    }
}

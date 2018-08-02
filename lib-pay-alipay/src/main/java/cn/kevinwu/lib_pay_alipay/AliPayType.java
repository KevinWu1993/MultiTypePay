package cn.kevinwu.lib_pay_alipay;

import android.content.Intent;

import com.alipay.sdk.app.EnvUtils;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;
import cn.kevinwu.lib_pay_core.paytype.AbsPayType;

public class AliPayType extends AbsPayType<AliPayType> {

    @Override
    protected void startTypePay() {
        if (isSandbox) EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        else EnvUtils.setEnv(EnvUtils.EnvEnum.ONLINE);
        if (paramStr != null) {
            Intent intent = new Intent(MultiPayContext.getInstance().getContext(), AliPayLoadActivity.class);
            intent.putExtra("paramString", paramStr);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MultiPayContext.getInstance().getContext().startActivity(intent);
        } else {
            MultiPayContext.getInstance().getPayResultCallBack().onError(AliPayStatusCode.ALI_PAY_PARAM_INVALID);
        }
    }
}

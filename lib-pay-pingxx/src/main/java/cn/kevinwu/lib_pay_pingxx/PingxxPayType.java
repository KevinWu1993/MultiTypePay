package cn.kevinwu.lib_pay_pingxx;

import android.content.Intent;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;
import cn.kevinwu.lib_pay_core.paytype.AbsPayType;
import com.pingplusplus.android.PaymentActivity;

public class PingxxPayType extends AbsPayType<PingxxPayType>{

    @Override
    protected void startTypePay() {
        //设置微信支付回调activity
        MultiPayContext.getInstance().setWxPayEntityPath(PaymentActivity.class.getName());
        if (paramStr != null) {
            Intent intent = new Intent(MultiPayContext.getInstance().getContext(), PingxxPayActivity.class);
            intent.putExtra("paramString", paramStr);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MultiPayContext.getInstance().getContext().startActivity(intent);
        } else {
            MultiPayContext.getInstance().getPayResultCallBack().onError(PingxxStatusCode.PINGXX_PAY_PARAM_INVALID);
        }
    }

}

package cn.kevinwu.lib_pay_pingxx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pingplusplus.android.Pingpp;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;

/**
 * 此Activity作为ping++支付承载Activity
 */
public class PingxxPayActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String paramString = getIntent().getStringExtra("paramString");
        Pingpp.createPayment(this, paramString);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
            switch (result) {
                case "success":
                    MultiPayContext.getInstance().getPayResultCallBack().onSuccess();
                    break;
                case "fail":
                    MultiPayContext.getInstance().getPayResultCallBack().onError(PingxxStatusCode.PINGXX_PAY_FAIL);
                    break;
                case "cancel":
                    MultiPayContext.getInstance().getPayResultCallBack().onCancel();
                    break;
                case "invalid":
                    MultiPayContext.getInstance().getPayResultCallBack().onError(PingxxStatusCode.PINGXX_PAY_INVALID);
                    break;
                case "unknown":
                    MultiPayContext.getInstance().getPayResultCallBack().onError(PingxxStatusCode.PINGXX_PAY_UNKNOWN);
                    break;
                default:
                    break;
            }
            this.finish();
        }
    }
}

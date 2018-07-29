package cn.kevinwu.lib_pay_alipay;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import cn.kevinwu.lib_pay_core.core.MultiPayContext;

import java.util.Map;

/**
 * 此Activity作为支付宝支付承载Activity
 */
public class AliPayLoadActivity extends Activity {
    private AliPayHandler aliPayHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aliPayHandler = new AliPayHandler();
        final String paramString = getIntent().getStringExtra("paramString");
        if(!TextUtils.isEmpty(paramString)){
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(AliPayLoadActivity.this);
                    Map<String, String> result = alipay.payV2(paramString, true);
                    Log.i("msp", result.toString());
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    aliPayHandler.sendMessage(msg);
                    finishActivity();
                }
            };
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }else{
            MultiPayContext.getInstance().getPayResultCallBack().onError(AliPayStatusCode.ALI_PAY_PARAM_INVALID);
            finishActivity();
        }
    }

    private void finishActivity(){
        this.finish();
    }
}

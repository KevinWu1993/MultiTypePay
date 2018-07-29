package cn.kevinwu.lib_pay_wxpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(MultiPayContext.getInstance().getContext(),
                MultiPayContext.getInstance().getExtraData());
        api.handleIntent(this.getIntent(), this);
        this.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, resp.errCode + "");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //如果微信支付返回errCode为0代表成功，其他代表失败
            if (resp.errCode == 0) {
                if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                    MultiPayContext.getInstance().getPayResultCallBack().onSuccess();
            } else {
                if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                    MultiPayContext.getInstance().getPayResultCallBack().onError(WXPayStatusCode.WX_PAY_ERROR);
            }
        }
    }
}
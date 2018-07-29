package cn.kevinwu.lib_pay_wxpay;

import android.text.TextUtils;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;
import cn.kevinwu.lib_pay_core.paytype.AbsPayType;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class WXPayTpye extends AbsPayType<WXPayTpye> {
    private IWXAPI iwxapi;

    @Override
    protected void startTypePay() {
        if (extraData == null) {
            //回调appid错误
            MultiPayContext.getInstance().getPayResultCallBack().onError(WXPayStatusCode.WX_PAY_APPID_NULL);
            return;
        }
        iwxapi = WXAPIFactory.createWXAPI(MultiPayContext.getInstance().getContext(),
                MultiPayContext.getInstance().getExtraData());
        MultiPayContext.getInstance().setWxPayEntityPath(WXPayEntryActivity.class.getName());
        if (paramMap != null) {
            payWithMap();
        } else if (paramStr != null) {
            payWithStr();
        } else {
            if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                MultiPayContext.getInstance().getPayResultCallBack().onError(WXPayStatusCode.WX_PAY_PARAM_INVALID);
        }
    }

    private void payWithMap() {
        //检查微信支付支持情况
        int checkSign = checkSupport();
        if (checkSign == WXPayStatusCode.WX_SUPPORT) {
            //设置参数并发起支付
            if (paramMap != null) {
                PayReq payreq = new PayReq();
                payreq.appId = paramMap.get(WXPayParam.appId);
                payreq.partnerId = paramMap.get(WXPayParam.partnerId);
                payreq.prepayId = paramMap.get(WXPayParam.prepayId);
                payreq.nonceStr = paramMap.get(WXPayParam.nonceStr);
                payreq.timeStamp = paramMap.get(WXPayParam.timeStamp);
                payreq.packageValue = paramMap.get(WXPayParam.packageValue);
                payreq.sign = paramMap.get(WXPayParam.sign);
                payreq.extData = paramMap.get(WXPayParam.extData);
                iwxapi.sendReq(payreq);
            } else {
                if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                    MultiPayContext.getInstance().getPayResultCallBack().onError(WXPayStatusCode.WX_PAY_PARAM_INVALID);
            }
        } else {
            if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                MultiPayContext.getInstance().getPayResultCallBack().onError(checkSign);
        }
    }

    private void payWithStr() {
        //检查微信支付支持情况
        int checkSign = checkSupport();
        if (checkSign == WXPayStatusCode.WX_SUPPORT) {
            //设置参数并发起支付
            try {
                JSONObject param = new JSONObject(paramStr);
                String appid = param.optString(WXPayParam.appId);
                String partnerid = param.optString(WXPayParam.partnerId);
                String prepayid = param.optString(WXPayParam.prepayId);
                String noncestr = param.optString(WXPayParam.nonceStr);
                String timestamp = param.optString(WXPayParam.timeStamp);
                String packagevalue = param.optString(WXPayParam.packageValue);
                String sign = param.optString(WXPayParam.sign);
                String extdata = param.optString(WXPayParam.extData);
                if (TextUtils.isEmpty(appid)
                        || TextUtils.isEmpty(partnerid)
                        || TextUtils.isEmpty(prepayid)
                        || TextUtils.isEmpty(noncestr)
                        || TextUtils.isEmpty(timestamp)
                        || TextUtils.isEmpty(packagevalue)
                        || TextUtils.isEmpty(sign)
                        || TextUtils.isEmpty(extdata)
                        ) {
                    if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                        MultiPayContext.getInstance().getPayResultCallBack().onError(WXPayStatusCode.WX_PAY_PARAM_INVALID);
                    return;
                }
                PayReq payreq = new PayReq();
                payreq.appId = appid;
                payreq.partnerId = partnerid;
                payreq.prepayId = prepayid;
                payreq.nonceStr = noncestr;
                payreq.timeStamp = timestamp;
                payreq.packageValue = packagevalue;
                payreq.sign = sign;
                payreq.extData = extdata;
                iwxapi.sendReq(payreq);
            } catch (JSONException e) {
                if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                    MultiPayContext.getInstance().getPayResultCallBack().onError(WXPayStatusCode.WX_PAY_PARAM_INVALID);
            }
        } else {
            if (MultiPayContext.getInstance().getPayResultCallBack() != null)
                MultiPayContext.getInstance().getPayResultCallBack().onError(checkSign);
        }
    }

    private int checkSupport() {
        if (!iwxapi.isWXAppInstalled()) {
            return WXPayStatusCode.WX_NO_INSTALL;
        } else if (!iwxapi.isWXAppSupportAPI()) {
            return WXPayStatusCode.WX_VERSION_INVALID;
        } else {
            return WXPayStatusCode.WX_SUPPORT;
        }
    }
}

package cn.kevinwu.lib_pay_core.wxpayhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import cn.kevinwu.lib_pay_core.core.MultiPayContext;

public class WXPayTransitActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String wxEntityClassPath = MultiPayContext.getInstance().getWxPayEntityPath();
        if(!TextUtils.isEmpty(wxEntityClassPath)){
            try {
                intent.setClass(MultiPayContext.getInstance().getContext(),
                        Class.forName(wxEntityClassPath));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.finish();
    }
}

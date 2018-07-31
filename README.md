# MultiTypePay

一个聚合微信、支付宝、Ping++支付的框架，可根据需求扩展支付方式。

## MultiTypePay使用文档

### 一、引入

1.工程module的build.gradle，添加核心依赖

``` grovvy
dependencies {
	implementation 'cn.kevinwu:lib-pay-alipay:1.0.0'
}
```
2.第三方支付模块，按需添加
``` grovvy
dependencies {
	implementation 'cn.kevinwu:lib-pay-wxpay:1.0.0'//使用app原生微信支付时添加
   	implementation 'cn.kevinwu:lib-pay-alipay:1.0.0'//使用支付宝支付时添加
    	implementation 'cn.kevinwu:lib-pay-pingxx:1.0.0'//使用ping++支付时添加
}
```

3.支付基础依赖，基于第二步基础按需添加

``` grovvy
dependencies {
    	implementation 'cn.kevinwu:lib-paylib-wxpay:1.0.0'//使用到微信支付时添加，不管原生或ping++
    	implementation 'cn.kevinwu:lib-paylib-alipay:1.0.0'//使用到支付宝支付时添加，不管原生或ping++
}
```
4.如果使用微信支付，需要在工程module的AndroidManifast.xml中配置微信支付辅助中转Activity：

``` xml
<activity
            android:name="com.fengbee.lib_pay_core.wxpayhelper.WXPayTransitActivity"
            android:exported="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            android:launchMode="singleInstance"/>
```

同时需要配置`activity-alias`把原有的`WXPayEntryActivity`转向`WXPayTransitActivity`:

``` xml
<activity-alias
            android:name="{应用的包名}.wxapi.WXPayEntryActivity"
            android:exported="true"
            android:targetActivity="com.fengbee.lib_pay_core.wxpayhelper.WXPayTransitActivity" />
```

5.权限配置，Android 6.0以上外置存储读写权限请动态申请
``` xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

### 二、如何使用

使用方式如下：

``` java
try {
                                    MultiTypePay.pay(App.AppContext, AliPayType.class)
                                            .setParam("key1", "value1")
                                            .setParam("key2", "value2")
                                            .execute(new PayResultCallBack() {
                                                @Override
                                                public void onSuccess() {
                                                    
                                                }

                                                @Override
                                                public void onError(int errorCode) {
                                                  
                                                }

                                                @Override
                                                public void onCancel() {

                                                }
                                            });
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                }
```

其中相应的SDK中封装了官方SDK的参数，如微信支付中使用实例如下：

``` java
try {
                                    MultiTypePay.pay(App.AppContext, WXPayTpye.class)
                                            .setExtraData("wxe123456789abcdefc")
                                            .setParam(WXPayParam.appId, resp.getResponse().getAppid())
                                            .setParam(WXPayParam.partnerId, resp.getResponse().getPartnerid())
                                            .setParam(WXPayParam.prepayId, resp.getResponse().getPrepayid())
                                            .setParam(WXPayParam.nonceStr, resp.getResponse().getNoncestr())
                                            .setParam(WXPayParam.timeStamp, resp.getResponse().getTimestamp())
                                            .setParam(WXPayParam.packageValue, resp.getResponse().getPkg())
                                            .setParam(WXPayParam.sign, resp.getResponse().getSign())
                                            .setParam(WXPayParam.extData, "app data")
                                            .execute(new PayResultCallBack() {
                                                @Override
                                                public void onSuccess() {
                                                    //这里可以做一些支付成功后的操作
                                                }

                                                @Override
                                                public void onError(int errorCode) {
                                                    if(errorCode == WXPayStatusCode.WX_NO_INSTALL){
                                                        CompatToast.makeText(App.AppContext, "请先安装微信客户端").show();
                                                    }else if(errorCode == WXPayStatusCode.WX_VERSION_INVALID){
                                                        CompatToast.makeText(App.AppContext, "当前微信版本不支持此功能，请更新微信").show();
                                                    }
                                                   //可以做一些支付错误的操作，上述提示只是举个例子
                                                }

                                                @Override
                                                public void onCancel() {
							//这里可以做一些用户取消支付的操作
                                                }
                                            });
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                }
```

### 三、setter方法说明

| 方法 | 说明 |
| --- | --- |
| setAllParamStr | 设置参数字符串，如支付宝支付服务端直接返回一个字符串的话可以通过这个方法直接设置参数 |
| setParam | 设置支付调用参数 | 
| setAllParamMap | 直接将参数以Map<String, String>形式设置进去，这个方法调用后会导致在其前面调用的setParam失效 |
| setSandbox | 是否开启沙箱，默认不开启，目前沙箱开启只支持支付宝支付 |
| setExtraData | 额外信息，比如微信支付中的appid，是通过这个方法设置的 |

### 四、回调说明

`PayResultCallBack`作为支付中状态回调，包括三种状态的回调，分别是：

onSuccess:支付成功
onError:支付错误，携带相应错误码
onCancel:取消支付

回调状态码对应表如下：

1.微信支付

``` java
    public static final int WX_SUPPORT = 0;//当前支持微信支付
    public static final int WX_NO_INSTALL = 1;//未安装微信
    public static final int WX_VERSION_INVALID = 2;//微信版本过低
    public static final int WX_PAY_PARAM_INVALID = 3;//参数错误
    public static final int WX_PAY_ERROR = 4;//微信支付错误
    public static final int WX_PAY_APPID_NULL = 5;//未设置appid
```
2.支付宝支付
``` java
    public static final int ALI_PAY_PARAM_INVALID = 1;//支付宝参数错误
    public static final int ALI_PAY_ERROR = 2;//支付宝支付错误
```

3.ping++支付
``` java
    public static final int PINGXX_PAY_PARAM_INVALID = 1;//ping++参数错误
    public static final int PINGXX_PAY_FAIL = 2;//ping++支付错误
    public static final int PINGXX_PAY_INVALID = 3;//ping++支付插件未安装
    public static final int PINGXX_PAY_UNKNOWN = 4;//ping++进程异常被杀死错误
```

### 五、如何扩展支付方式

MultiTypePay只包含了微信、支付宝、ping++支付方式，如果需要别的支付如银联、qq钱包支付等均可依赖core进行扩展。

只需要集成AbsPayType实现相应的PayType，并在startTypePay()方法中实现具体的支付逻辑即可，具体可以参考微信和支付宝支付的实现，如有疑问也欢迎随时交流。

### 六、混淆规则

待补充







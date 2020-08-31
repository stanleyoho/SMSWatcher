package com.playplus.app.smswatcher.smsObserverLib;

import android.os.Handler;
import android.os.Message;

/**
 * 短信处理
 *
 * @author 江钰锋 0152
 * @version [版本号, 2015年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SmsHandler extends Handler {

    private SmsResponseCallback mCallback;

    /***
     * 短信过滤器
     */
    private SmsFilter smsFilter;

    public SmsHandler(SmsResponseCallback callback) {
        this.mCallback = callback;
    }

    public SmsHandler(SmsResponseCallback callback, SmsFilter smsFilter) {
        this(callback);
        this.smsFilter = smsFilter;
    }

    /***
     * 设置短信过滤器
     * @param smsFilter 短信过滤器
     */
    public void setSmsFilter(SmsFilter smsFilter) {
        this.smsFilter = smsFilter;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == SmsObserver.MSG_RECEIVED_CODE) {
            String[] smsInfos = (String[]) msg.obj;
            if (smsInfos != null && smsInfos.length == 4 && mCallback != null) {
                if (smsFilter == null) {
                    smsFilter = new DefaultSmsFilter();
                }
                String messageId = smsInfos[0];
                String address = smsInfos[1];
                String message = smsInfos[2];
                String createTime = smsInfos[3];
                mCallback.onCallbackSmsContent(messageId,address,smsFilter.filter(address, message),createTime);
            }
        }
    }
}

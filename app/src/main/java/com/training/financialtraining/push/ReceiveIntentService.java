package com.training.financialtraining.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.training.financialtraining.MainActivity;
import com.training.financialtraining.R;

/**
 * Created by Administrator on 2018/7/17.
 */

public class ReceiveIntentService extends GTIntentService {
    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
//        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
//        Log.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        Log.e(TAG, "onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
                + "\ncid = " + cid);
        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.e(TAG, "receiver payload = " + data);
            GeTuiPushBean geTuiPushBean = new Gson().fromJson(data, GeTuiPushBean.class);
            showNotification(geTuiPushBean);
        }
    }

    private void showNotification(GeTuiPushBean bean){
//        GeTuiPushBean.PayloadBean payload1 = bean.getPayload();
//        int type = payload1.getType();

        //获取NotificationManager实例
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle(bean.getTitle())
                //设置通知内容
                .setContentText(bean.getContent())
                .setAutoCancel(true)
                .setTicker(bean.getContent())
                .setDefaults(Notification.DEFAULT_VIBRATE)
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        .setWhen(System.currentTimeMillis());

//        if(type == 1){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            builder.setContentIntent(pendingIntent);
//        }

        //通过builder.build()方法生成Notification对象,并发送通知,id=1
//        notifyManager.notify((int)(Math.random()*100), builder.build());
        notifyManager.notify(1, builder.build());

    }
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }
}

package mobile.netmera.com.fcmmulti;


import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.netmera.Netmera;

import org.greenrobot.eventbus.EventBus;

public class NgMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        if (Netmera.isNetmeraRemoteMessage(remoteMessage)) {
//            Log.i("NM MESSAGE", "NM MESSAGE RECEIVED");
//            Netmera.onNetmeraPushMessageReceived(remoteMessage);
//        } else {
//            EventBus.getDefault().post(new MessageEvent(remoteMessage.getNotification().getBody()));
//            Log.i("FCM MESSAGE", "FCM MESSAGE :: " + remoteMessage.getNotification().getBody());
//        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.i("DEFAULT TOKEN", "Default Token :: " + token);

        FirebaseApp secondaryApp = FirebaseApp.getInstance("secondary");
        FirebaseInstanceId.getInstance(secondaryApp).getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Log.e("Secondary Token", "Secondary Firebase Token  :: " + instanceIdResult.getToken());
//                Netmera.onNetmeraNewToken(instanceIdResult.getToken());
            }
        });

    }
}

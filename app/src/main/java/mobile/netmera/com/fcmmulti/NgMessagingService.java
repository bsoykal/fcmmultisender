package mobile.netmera.com.fcmmulti;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.netmera.Netmera;

import org.greenrobot.eventbus.EventBus;

public class NgMessagingService extends FirebaseMessagingService {
static final String TAG = "NGMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Netmera.isNetmeraRemoteMessage(remoteMessage)) {
            Log.i("NM MESSAGE", "NM MESSAGE RECEIVED");
            Netmera.onNetmeraPushMessageReceived(remoteMessage);
        } else {
            EventBus.getDefault().post(new MessageEvent(remoteMessage.getNotification().getBody()));
            Log.i("FCM MESSAGE", "FCM MESSAGE :: " + remoteMessage.getNotification().getBody());
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        Log.i("DEFAULT TOKEN", "Default Token :: " + token);

        FirebaseApp secondaryApp = FirebaseApp.getInstance("secondary");
        FirebaseInstallations.getInstance(secondaryApp).getToken(false).addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<InstallationTokenResult> task) {
                if (!task.isSuccessful()){
                    Log.e(TAG, "onComplete: task is not successful" );
                }
                else{
                    Log.e("Secondary Token", "Secondary Firebase Token  :: " + task.getResult().getToken());
                    Netmera.onNetmeraNewToken(task.getResult().getToken());
                }
            }
        });


    }
}

package mobile.netmera.com.fcmmulti;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.netmera.Netmera;

public class App extends Application {

    private final String TAG = "FCMMulti APP";

    @Override
    public void onCreate() {
        super.onCreate();
        PropertiesUtil.init(this, false);
        initLegacyData();
        Netmera.logging(true);

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("1:561248270199:android:4548658facdfe54f") // Required for Analytics.
//                .setApiKey("AIzaSyDXqFsZMoeFefVFSe9U8P2B9PEvNKP2ldk") // Required for Auth.
//                .build();
//        FirebaseApp.initializeApp(this /* Context */, options, "secondary");
//        FirebaseApp secondaryApp = FirebaseApp.getInstance("secondary");

//        Netmera.init(this,PropertiesUtil.gcmSenderId,null,secondaryApp);

        Netmera.init(this,PropertiesUtil.gcmSenderId,null);

        setNetmeraApiKeyIfHasBeenProvidedBefore();

        Log.i(TAG,"onCreate was called");

    }

    @SuppressLint("ApplySharedPref")
    private void initLegacyData() {
        getSharedPreferences("com.google.android.gcm", Context.MODE_PRIVATE).edit().putString("regId", "legacyRegistrationId").commit();
        getSharedPreferences("generalSettings", Context.MODE_PRIVATE).edit().putString("appIIDStr", "legacyInstallationId").commit();
    }

    private void setNetmeraApiKeyIfHasBeenProvidedBefore() {
        final SharedPreferences pref = getSharedPreferences("ng_prop", MODE_PRIVATE);
        String baseUrl = pref.getString("ek313", null);
        String apiKey = pref.getString("313ke", null);
        if (!TextUtils.isEmpty(baseUrl) && !TextUtils.isEmpty(apiKey)) {
            Netmera.setBaseUrl(baseUrl);
            Netmera.setApiKey(apiKey);
        }
    }


}

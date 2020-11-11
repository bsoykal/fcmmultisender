package mobile.netmera.com.fcmmulti;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
=======

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
>>>>>>> 7450a317e81a228917e670c1fdfc2cbd6fde0b29
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.installations.InstallationTokenResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.netmera.Netmera;
import com.netmera.NetmeraUser;
import com.netmera.events.NetmeraEventBannerOpen;
import com.netmera.events.commerce.NetmeraEventCartView;
import com.netmera.events.commerce.NetmeraProduct;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
<<<<<<< HEAD
=======
import java.util.TimeZone;

>>>>>>> 7450a317e81a228917e670c1fdfc2cbd6fde0b29

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;

    private TextView messages;
    private AppCompatButton updateUser;
    private AppCompatButton fcm_custom_event;
    private AppCompatButton show_img;
    private AppCompatImageView test_img;

    private boolean downloaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);

        Netmera.requestPermissionsForLocation();

        setApiInfo();

<<<<<<< HEAD


        messages = (TextView)findViewById(R.id.messages);
        updateUser = (AppCompatButton)findViewById(R.id.updateUser);
        fcm_custom_event = (AppCompatButton)findViewById(R.id.fcm_custom_event);
        show_img = (AppCompatButton)findViewById(R.id.show_img);
=======
        messages = (TextView) findViewById(R.id.messages);
        updateUser = (AppCompatButton) findViewById(R.id.updateUser);
        fcm_custom_event = (AppCompatButton) findViewById(R.id.fcm_custom_event);
        show_img = (AppCompatButton) findViewById(R.id.show_img);
        start_second_activity = (AppCompatButton) findViewById(R.id.start_second_activity);
>>>>>>> 7450a317e81a228917e670c1fdfc2cbd6fde0b29

        test_img = (AppCompatImageView) findViewById(R.id.test_img);

        updateUser.setOnClickListener(this);
        fcm_custom_event.setOnClickListener(this);
        show_img.setOnClickListener(this);
<<<<<<< HEAD
=======
        start_second_activity.setOnClickListener(this);

>>>>>>> 7450a317e81a228917e670c1fdfc2cbd6fde0b29
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        String oldMessage = messages.getText().toString();
        messages.setText(oldMessage + "\n " + messageEvent.getMessage());
    }


    @Override
    public void onStart() {
        super.onStart();
        Netmera.enablePopupPresentation();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void setApiInfo() {
        final SharedPreferences pref = getSharedPreferences("ng_prop", MODE_PRIVATE);
        String baseUrl = pref.getString("ek313", null);
        String apiKey = pref.getString("313ke", null);
        if (!TextUtils.isEmpty(baseUrl) && !TextUtils.isEmpty(apiKey)) {
            Netmera.setBaseUrl(baseUrl);
            Netmera.setApiKey(apiKey);
            return;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_properties, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextBaseUrl = (EditText) dialogView.findViewById(R.id.edit_text_base_url);

        final EditText editTextApiKey = (EditText) dialogView.findViewById(R.id.edit_text_api_key);

        dialogBuilder.setTitle("Netmera Properties");
        dialogBuilder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String baseUrl = editTextBaseUrl.getText().toString();
                String apiKey = editTextApiKey.getText().toString();

                Netmera.setBaseUrl(baseUrl);
                Netmera.setApiKey(apiKey);

                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ek313", baseUrl);
                editor.putString("313ke", apiKey);
                editor.commit();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String baseUrl = editTextBaseUrl.getText().toString();
                        String apiKey = editTextApiKey.getText().toString();
                        if (baseUrl.equals("a") && apiKey.equals("a")) {
                            editTextApiKey.setText(PropertiesUtil.netmeraApiKey);
                            editTextBaseUrl.setText(PropertiesUtil.netmeraUrl);
                        }
                        return true;
                    }
                });
                positiveButton.setEnabled(false);

                editTextApiKey.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 0 && editTextBaseUrl.getText().length() > 0) {
                            positiveButton.setEnabled(true);
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
                editTextBaseUrl.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 0 && editTextApiKey.getText().length() > 0) {
                            positiveButton.setEnabled(true);
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
            }
        });
      //  alertDialog.show();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.updateUser) {
            Date birthday = new Date();
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            calendar.set(1992,06,20,0,0,0);
            birthday.setTime(calendar.getTimeInMillis());
            Log.i("TAG","Date :: "+birthday.toString());
            NetmeraUser user = new NetmeraUser();
            user.setUserId("soykalburak");
            user.setName("Burak");
            user.setSurname("Soykal");
            user.setEmail("soykalburak@gmail.com");
            user.setDateOfBirth(birthday);

            Netmera.updateUser(user);
<<<<<<< HEAD
        }else if(v.getId()==R.id.fcm_custom_event){
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {


                if (!task.isSuccessful()){
                    Log.e("burakurus", "onComplete: Primary task is not successful" );
                }
                else{
                    Log.e("Primary Token", "Primary Firebase Token  :: " + task.getResult());

                }
                }
            });

            FirebaseApp secondaryApp = FirebaseApp.getInstance("secondary");
        FirebaseInstallations.getInstance(secondaryApp).getToken(false).addOnCompleteListener(new OnCompleteListener<InstallationTokenResult>() {
            @Override
            public void onComplete(@NonNull Task<InstallationTokenResult> task) {
                if (!task.isSuccessful()){
                    Log.e("burakurus", "onComplete: Secondary task is not successful" );
                }
                else{
                    Log.e("Secondary Token", "Secondary Firebase Token  :: " + task.getResult().getToken());

                }
            }
        });

            int nextRandom = new Random().nextInt(3)+1;
            FirebaseAnalytics.getInstance(this).logEvent("RandomEvent1to3_"+nextRandom,null );
=======
        } else if (v.getId() == R.id.fcm_custom_event) {
            int nextRandom = new Random().nextInt(3) + 1;
            FirebaseAnalytics.getInstance(this).logEvent("RandomEvent1to3_" + nextRandom, null);
        } else if (v.getId() == R.id.show_img) {
            if (!downloaded) {
                GlideApp.with(this)
                        .load("https://images.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg")
                        .fitCenter()
                        .into(test_img);

                downloaded = true;
            } else {
                test_img.setImageDrawable(null);
                downloaded = false;
            }
        } else if (v.getId() == R.id.start_second_activity) {
            startActivity(new Intent(this, SecondaryApiKeyTestActivity.class));
>>>>>>> 7450a317e81a228917e670c1fdfc2cbd6fde0b29
        }
    }
}

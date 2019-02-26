package mobile.netmera.com.fcmmulti;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.netmera.Netmera;
import com.netmera.NetmeraUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAnalytics mFirebaseAnalytics;

    private TextView messages;
    private AppCompatButton updateUser;
    private AppCompatButton fcm_custom_event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);

        setApiInfo();

        messages = (TextView)findViewById(R.id.messages);
        updateUser = (AppCompatButton)findViewById(R.id.updateUser);
        fcm_custom_event = (AppCompatButton)findViewById(R.id.fcm_custom_event);
        updateUser.setOnClickListener(this);
        fcm_custom_event.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        String oldMessage = messages.getText().toString();
        messages.setText( oldMessage + "\n " +messageEvent.getMessage());
    }


    @Override
    public void onStart() {
        super.onStart();
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
            @Override public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogBuilder.setCancelable(false);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialog) {
                final Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override public boolean onLongClick(View v) {
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
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override public void afterTextChanged(Editable s) {
                        if (s.length() > 0 && editTextBaseUrl.getText().length() > 0) {
                            positiveButton.setEnabled(true);
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
                editTextBaseUrl.addTextChangedListener(new TextWatcher() {
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override public void afterTextChanged(Editable s) {
                        if (s.length() > 0 && editTextApiKey.getText().length() > 0) {
                            positiveButton.setEnabled(true);
                        } else {
                            positiveButton.setEnabled(false);
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.updateUser){
            NetmeraUser user = new NetmeraUser();
            user.setUserId("soykalburak");
            user.setName("Burak");
            user.setSurname("Soykal");
            user.setEmail("soykalburak@gmail.com");
            Netmera.updateUser(user);
        }else if(v.getId()==R.id.fcm_custom_event){
            int nextRandom = new Random().nextInt(3)+1;
            FirebaseAnalytics.getInstance(this).logEvent("RandomEvent1to3_"+nextRandom,null );
        }
    }
}

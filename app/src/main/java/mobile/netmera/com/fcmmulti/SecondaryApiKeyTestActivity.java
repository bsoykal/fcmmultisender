package mobile.netmera.com.fcmmulti;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.netmera.Netmera;

import org.w3c.dom.Text;

public class SecondaryApiKeyTestActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatEditText api_key_et;
    private AppCompatButton set_api_key_btn;

    private final static String secondaryApiKey ="CSKsXEK-7vzfdSv_jOIdZ4X7jyAXC_7KwuseRy6B3nGrblkMqIqLJdiVr_TGau1lBEO4rFZofHQ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_api_key_test);

        api_key_et = (AppCompatEditText) findViewById(R.id.api_key_et);
        set_api_key_btn = (AppCompatButton) findViewById(R.id.set_api_key_btn);

        api_key_et.setText(secondaryApiKey);

        set_api_key_btn.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.set_api_key_btn){
            String api_key = api_key_et.getText().toString();

            if(!TextUtils.isEmpty(api_key)){
                Netmera.init(this,PropertiesUtil.gcmSenderId,api_key);
            }

        }
    }
}

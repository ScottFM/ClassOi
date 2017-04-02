package com.example.scott.classoi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button send;//, sendViaIntent;
    SmsManager smsManager;
    EditText edtName, edtMessage, edtPhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        //these extra precautions are new mandatory for Android Studio after KitKat
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //If permission has not been granted already, ask for it.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] arrayOfPermissions, int[] arrayOfResults) {
        //Function: After permission has been requested, this function will process the results from manifest
        switch (requestCode) {
            case 123:
                if (arrayOfResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted: Send SMS", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Permission denied: Send SMS", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void initialize(){
        send = (Button) findViewById(R.id.btnSend);
        send.setOnClickListener(this);

        //sendViaIntent = (Button) findViewById(R.id.btnSendIntent);
        //sendViaIntent.setOnClickListener(this);

        edtName = (EditText) findViewById(R.id.edtName);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        edtPhoneNum = (EditText) findViewById(R.id.edtPhoneNum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                String name = edtName.getText().toString();
                String msg = "Oi: " + edtMessage.getText().toString();
                String number = "+1" + edtPhoneNum.getText().toString();

                smsManager = SmsManager.getDefault();   //accesses your phone's currently working default sms manager for texting
                smsManager.sendTextMessage(number, null, msg, null, null);
                //as it is now, has a security error.
                //code must be written in the manifest to grant permission
                break;


            //This is the functionality for populating the default sms messenger with some text
            /*
            case R.id.btnSendIntent:
                String smsPackageName = Telephony.Sms.getDefaultSmsPackage(this);
                //Error becuase our current API is 15, althought this line requires minimum 19
                //This line asks for the default package. If you don't include it, and your device has multiple
                    //apps to handle the send, it will ask you which one to use.

                Intent sendIntent = new Intent(Intent.ACTION_SEND); //looking for any content provider out there that handles sends
                sendIntent.setPackage(smsPackageName);  //Deal with the specific default package we accessed already
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "oi");

                startActivity(sendIntent);

                break;
                */

        }
    }
}

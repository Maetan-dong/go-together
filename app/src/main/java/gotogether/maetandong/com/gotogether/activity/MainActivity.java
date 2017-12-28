package gotogether.maetandong.com.gotogether.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gotogether.maetandong.com.gotogether.HttpUtil;
import gotogether.maetandong.com.gotogether.R;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final int NOTIFY_ID = 100;
    private static final String YES_ACTION = "gotogether.maetandong.com.gotogether.YES_ACTION";
    private static final String NO_ACTION = "gotogether.maetandong.com.gotogether.NO_ACTION";

    NotificationManager mNotificationManager;
    Button mButtonSendMessage;
    EditText mEditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSendMessage = findViewById(R.id.button_sendMessage);
        mEditMessage = findViewById(R.id.edit_message);

        mButtonSendMessage.setOnClickListener(this);


        findViewById(R.id.button_notify).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showActionButtonsNotification();
            }
        });

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        processIntentAction(getIntent());
    }

    private Intent getNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    private void showActionButtonsNotification() {
        Intent yesIntent = getNotificationIntent();
        yesIntent.setAction(YES_ACTION);

        Intent noIntent = getNotificationIntent();
        noIntent.setAction(NO_ACTION);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setContentIntent(PendingIntent.getActivity(this,0,getNotificationIntent(), PendingIntent.FLAG_CANCEL_CURRENT))
                .setSmallIcon(R.drawable.ic_message)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_yes,
                        getString(R.string.yes),
                        PendingIntent.getActivity(this, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                ))
                .addAction(new NotificationCompat.Action(
                        R.drawable.ic_yes,
                        getString(R.string.no),
                        PendingIntent.getActivity(this, 0, noIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                ));

        mNotificationManager.notify(NOTIFY_ID, notification.build());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntentAction(intent);
        super.onNewIntent(intent);
    }

    private void processIntentAction(Intent intent) {
        if(intent.getAction() != null) {
            switch (intent.getAction()) {
                case YES_ACTION:
                    Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show();
                    break;
                case NO_ACTION:
                    Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_sendMessage:
                try {
                    new HttpUtil(mEditMessage.getText().toString()).run();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }
}
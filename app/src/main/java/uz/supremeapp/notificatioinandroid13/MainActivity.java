package uz.supremeapp.notificatioinandroid13;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {
    private Button button1;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder mBuilder;
private TextView textView;
    @Override
    protected void onCreate(Bundle savedIntsance) {
        super.onCreate(savedIntsance);
        setContentView(R.layout.main);
        permission();

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotificationChannel();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

                int notifyId = 0;
                mBuilder = new androidx.core.app.NotificationCompat.Builder(MainActivity.this, "id").setSmallIcon(R.drawable.ic_android_black_24dp).setContentTitle("Example Notification").setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_android_black_24dp)).setContentText("Created by Bobur @sketchlearn").setContentIntent(pendingIntent);

                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (notificationManager != null) {
                    if (notificationManager.areNotificationsEnabled()) {
                        notificationManager.notify(notifyId, mBuilder.build());
                    } else {
                        Toast.makeText(MainActivity.this, "Permission is not granted", Toast.LENGTH_SHORT).show();
                        permission();
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            textView = findViewById(R.id.textView);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                textView.setText("Permission granted");
            } else {
                textView.setText("Permission not granted");
            }
        }
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId2 = "id";
            String channelName2 = "Notification";

            NotificationChannel channel = new NotificationChannel(channelId2, channelName2, NotificationManager.IMPORTANCE_HIGH);

            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.enableVibration(true);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            } else {

            }
        }
    }

    public void permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1000);
        }
    }
}
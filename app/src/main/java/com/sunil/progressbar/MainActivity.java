package com.sunil.progressbar;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,View.OnClickListener
{
    Button JButton, Jnotify;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize =0;
    // String msg = "Download finished";
    EditText Jname,Jsurname,Jbosy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Jname = (EditText) findViewById(R.id.name);
        Jsurname = (EditText) findViewById(R.id.surname);
        Jbosy = (EditText) findViewById(R.id.body);
        Jnotify = (Button) findViewById(R.id.notify);
        JButton = (Button) findViewById(R.id.button);

        //// notification
        Jnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Jname.getText().toString();
                String subject = Jsurname.getText().toString();
                String body = Jbosy.getText().toString();

                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify = new Notification(R.mipmap.ic_launcher,title,System.currentTimeMillis());
                PendingIntent pending = PendingIntent.getActivity(getApplicationContext(),0,new Intent(),0);

                notify.setLatestEventInfo(getApplicationContext(),subject,body,pending);
                notify.defaults = Notification.DEFAULT_ALL;
                notif.notify(0,notify);
            }
        });

        //// progress bar
        JButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("File Downloading,,,,,,,,");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                progressBarStatus = 0;

                fileSize = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressBarStatus <100){
                            progressBarStatus = downloadFile();

                            try{
                                Thread.sleep(1000);
                            }

                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            progressBarbHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                        if (progressBarStatus >= 100){
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progressBar.dismiss();

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Download finished", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();

            }
        });
    }

    public int downloadFile(){
        while (fileSize <=1000000) {
            fileSize++;

            if (fileSize == 10000) {
                return 10;
            } else if (fileSize == 20000) {
                return 20;
            } else if (fileSize == 30000) {
                return 30;
            } else if (fileSize == 400000) {
                return 40;
            } else if (fileSize == 500000) {
                return 50;
            } else if (fileSize == 700000) {
                return 70;
            } else if (fileSize == 800000) {
                return 80;
            }
        }

        return 100;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}

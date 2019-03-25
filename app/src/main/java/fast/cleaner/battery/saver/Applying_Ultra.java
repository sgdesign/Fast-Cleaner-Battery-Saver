package fast.cleaner.battery.saver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intagpc.myapplication.R;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

/**
 * Created by intag pc on 2/21/2017.
 */

public class Applying_Ultra extends Activity {



    //// activate ultra powersaving mode by closing system services

    DecoView arcView;
    TextView ist,sec,thir,fou,completion,fif;
    ImageView istpic,secpic,thirpic,foupic,fifthpic;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applying_ultra);

        ist=(TextView) findViewById(R.id.ist);
        sec=(TextView) findViewById(R.id.sec);
        thir=(TextView) findViewById(R.id.thi);
        fou=(TextView) findViewById(R.id.fou);
        fif=(TextView) findViewById(R.id.fif);
        istpic=(ImageView) findViewById(R.id.istpic);
        secpic=(ImageView) findViewById(R.id.secpic);
        thirpic=(ImageView) findViewById(R.id.thipic);
        foupic=(ImageView) findViewById(R.id.foupic);
        foupic=(ImageView) findViewById(R.id.foupic);
        fifthpic =(ImageView) findViewById(R.id.fifthpic);
        completion=(TextView) findViewById(R.id.completion);

        sharedpreferences = getSharedPreferences("was", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();




        arcView = (DecoView) findViewById(R.id.dynamicArcView2);

//        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
//                .setRange(0, 100, 0)
//                .setInterpolator(new AccelerateInterpolator())
//                .build());

        arcView.addSeries(new SeriesItem.Builder(Color.parseColor("#27282D"))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#27282D"))
                .setRange(0, 100, 0)
                .setLineWidth(10f)
                .build();

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor("#FFFFFF"))
                .setRange(0, 100, 0)
                .setLineWidth(10f)
                .build();
//
//        int series1Index = arcView.addSeries(seriesItem1);
        int series1Index2 = arcView.addSeries(seriesItem2);

        seriesItem2.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float v, float v1) {


                Float obj = new Float(v1);
                int i = obj.intValue();
                completion.setText(i+"%");

                if(v1>=10 && v1<40)
                {
                    ist.setTextColor(Color.parseColor("#FFFFFF"));
                    istpic.setImageResource(R.drawable.circle_white);

                }
                else if(v1>=40 && v1<65)
                {
                    sec.setTextColor(Color.parseColor("#FFFFFF"));
                    secpic.setImageResource(R.drawable.circle_white);
                }
                else if(v1>=65 && v1<80)
                {
                    thir.setTextColor(Color.parseColor("#FFFFFF"));
                    thirpic.setImageResource(R.drawable.circle_white);
                }
                else if(v1>=80 && v1<90)
                {
                    fou.setTextColor(Color.parseColor("#FFFFFF"));
                    foupic.setImageResource(R.drawable.circle_white);
                }
                else if(v1>=90 && v1<100)
                {
                    fif.setTextColor(Color.parseColor("#FFFFFF"));
                    fifthpic.setImageResource(R.drawable.circle_white);
                }


            }

            @Override
            public void onSeriesItemDisplayProgress(float v) {

            }
        });


        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent decoEvent) {
//                        bottom.setText("");
//                        top.setText("");
//                        centree.setText("Optimizing...");

                    }

                    @Override
                    public void onEventEnd(DecoEvent decoEvent) {

                    }

                })
                .build());

        arcView.addEvent(new DecoEvent.Builder(100).setIndex(series1Index2).setDelay(1000).setListener(new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent decoEvent) {
//                bottom.setText("");
//                top.setText("");
//                centree.setText("Optimizing...");
            }

            @Override
            public void onEventEnd(DecoEvent decoEvent) {
//                bottom.setText("Found");
//                top.setText("Storage");
//                Random ran3 = new Random();
//                ramperct.setText(ran3.nextInt(40) + 20+"%");





                check=1;
                youDesirePermissionCode(Applying_Ultra.this);







                enablesall();
//                editor.putString("mode", "2");
//                editor.commit();
            }
        }).build());
    }

    public void enablesall()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }

        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);


        boolean wifiEnabled = wifiManager.isWifiEnabled();
        if(wifiEnabled)
        {
            wifiManager.setWifiEnabled(false);
        }

//        setAutoOrientationEnabled(getApplicationContext(), false);
//
//        Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);
//
//        ContentResolver.setMasterSyncAutomatically(false);


    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }



    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && Settings.System.canWrite(this)){
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success");


//            Toast.makeText(getApplicationContext(),"onActivityResult",Toast.LENGTH_LONG).show();
            //do your code
            PowerSaving_Complition.setAutoOrientationEnabled(getApplicationContext(), false);

            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);

            ContentResolver.setMasterSyncAutomatically(false);


            Intent i=new Intent(Applying_Ultra.this,BatterySaver_Black.class);
            startActivity(i);
            finish();
        }
    }


    public void youDesirePermissionCode(Activity context){
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
//            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
//            setAutoOrientationEnabled(context, false);
        }
        if (permission) {
            //do your code
            PowerSaving_Complition.setAutoOrientationEnabled(getApplicationContext(), false);

            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);

            ContentResolver.setMasterSyncAutomatically(false);


            Intent i=new Intent(Applying_Ultra.this,BatterySaver_Black.class);
            startActivity(i);
            finish();

        }  else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, 1);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code

//            Toast.makeText(getApplicationContext(),"onRequestPermissionsResult",Toast.LENGTH_LONG).show();

            PowerSaving_Complition.setAutoOrientationEnabled(getApplicationContext(), false);

            Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);

            ContentResolver.setMasterSyncAutomatically(false);


            Intent i=new Intent(Applying_Ultra.this,BatterySaver_Black.class);
            startActivity(i);
            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(check==1)
        {
            try
            {
                PowerSaving_Complition.setAutoOrientationEnabled(getApplicationContext(), false);

                Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 20);

                ContentResolver.setMasterSyncAutomatically(false);

            }
            catch(Exception e)
            {
                Intent i=new Intent(Applying_Ultra.this,BatterySaver_Black.class);
                startActivity(i);
                finish();
            }

            Intent i=new Intent(Applying_Ultra.this,BatterySaver_Black.class);
            startActivity(i);
            finish();
        }
    }
}

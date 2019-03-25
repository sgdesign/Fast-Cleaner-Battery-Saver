package fast.cleaner.battery.saver.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fast.cleaner.battery.saver.Classes.Apps;
import fast.cleaner.battery.saver.Cpu_Scanner;
import fast.cleaner.battery.saver.MainActivity;
import com.example.intagpc.myapplication.R;
import fast.cleaner.battery.saver.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by intag pc on 2/12/2017.
 */

public class CPUCooler extends Fragment {

    TextView batterytemp, showmain, showsec, nooverheating;
    float temp;
    ImageView coolbutton, tempimg;
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;
    public static List<Apps> apps;
    List<Apps> apps2;
    int check = 0;

    BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                int level = intent.getIntExtra("level", 0);
                temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;

                batterytemp.setText(temp + "°C");

                if (temp >= 30.0) {

                    apps = new ArrayList<>();
                    apps2 = new ArrayList<>();
                    tempimg.setImageResource(R.drawable.red_cooler);
                    showmain.setText("OVERHEATED");
                    showmain.setTextColor(Color.parseColor("#F22938"));
                    showsec.setText("Apps are causing problem hit cool down");
                    nooverheating.setText("");

                    coolbutton.setImageResource(R.drawable.cool_button_blue);
                    coolbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent i = new Intent(getActivity(), Cpu_Scanner.class);
                            startActivity(i);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

//                        getActivity().unregisterReceiver(batteryReceiver);
                                    nooverheating.setText("Currently No App causing Overheating");
                                    showmain.setText("NORMAL");
                                    showmain.setTextColor(Color.parseColor("#24D149"));
                                    showsec.setText("CPU Temperature is GOOD");
                                    showsec.setTextColor(Color.parseColor("#24D149"));
                                    coolbutton.setImageResource(R.drawable.cooled);
                                    tempimg.setImageResource(R.drawable.blue_cooler);
                                    batterytemp.setText("25.3" + "°C");
                                    recyclerView.setAdapter(null);

                                }
                            }, 2000);


                            coolbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                Toast.makeText(getActivity(), "CPU Temperature is Already Normal", Toast.LENGTH_SHORT).show();

                                    LayoutInflater inflater = getLayoutInflater(getArguments());
                                    View layout = inflater.inflate(R.layout.my_toast, null);

                                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                                    TextView text = (TextView) layout.findViewById(R.id.textView1);
                                    text.setText("CPU Temperature is Already Normal.");

                                    Toast toast = new Toast(getActivity());
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();
                                }
                            });
                        }
                    });

                    if (Build.VERSION.SDK_INT < 23) {

                        showsec.setTextAppearance(context, android.R.style.TextAppearance_Medium);
                        showsec.setTextColor(Color.parseColor("#F22938"));

                    } else {

                        showsec.setTextAppearance(android.R.style.TextAppearance_Small);
                        showsec.setTextColor(Color.parseColor("#F22938"));
                    }


                    recyclerView.setItemAnimator(new SlideInLeftAnimator());
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

                    recyclerView.getItemAnimator().setAddDuration(10000);

                    mAdapter = new RecyclerAdapter(apps);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
                    recyclerView.computeHorizontalScrollExtent();
                    recyclerView.setAdapter(mAdapter);
                    getAllICONS();
//                recyclerView.getItemAnimator().setRemoveDuration(1000);
//                recyclerView.getItemAnimator().setMoveDuration(1000);
//                recyclerView.getItemAnimator().setChangeDuration(1000);

                }
            }
            catch(Exception e)
            {

            }


        }
    };
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cpu_cooler, container, false);

        try {

            getActivity().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


            tempimg = (ImageView) view.findViewById(R.id.tempimg);
            showmain = (TextView) view.findViewById(R.id.showmain);
            showsec = (TextView) view.findViewById(R.id.showsec);
            coolbutton = (ImageView) view.findViewById(R.id.coolbutton);
            nooverheating = (TextView) view.findViewById(R.id.nooverheating);

            showmain.setText("NORMAL");
            showsec.setText("CPU Temperature is GOOD");
            nooverheating.setText("Currently No App causing Overheating");
            coolbutton.setImageResource(R.drawable.cooled);
            coolbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getLayoutInflater(getArguments());
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
                    text.setText("CPU Temperature is Already Normal.");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            });

            tempimg.setImageResource(R.drawable.blue_cooler);
            batterytemp = (TextView) view.findViewById(R.id.batterytemp);

            Log.e("Temperrature", temp + "");
        } catch (Exception e) {

        }


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {

            getActivity().unregisterReceiver(batteryReceiver);
        } catch (Exception e) {

        }
    }


    public void getAllICONS() {

        PackageManager pm = getActivity().getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        if (packages != null) {
            for (int k = 0; k < packages.size(); k++) {
                // String pkgName = app.getPackageName();
                String packageName = packages.get(k).packageName;
                Log.e("packageName-->", "" + packageName);

                if (!packageName.equals("fast.cleaner.battery.saver")) {

//                String size = packages.get(k).metaData.size()+"";
//                Log.e("Size-->", "" + packageName);
                    Drawable ico = null;
                    try {
                        String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                        Apps app = new Apps();

//                    app.setSize("" + pName);

                        File file = new File(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA).publicSourceDir);
                        long size = file.length();

                        Log.e("SIZE", size / 1000000 + "");
                        app.setSize(size / 1000000 + 20 + "MB");

                        ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                        app.setImage(ico = getActivity().getPackageManager().getApplicationIcon(packages.get(k).packageName));
                        getActivity().getPackageManager();
                        Log.e("ico-->", "" + ico);

                        if (((a.flags & ApplicationInfo.FLAG_SYSTEM) == 0)) {
//                        System.out.println(">>>>>>packages is system package"+pi.packageName);

                            if (check <= 5) {
                                check++;
                                apps.add(app);
                            } else {
                                getActivity().unregisterReceiver(batteryReceiver);
//                            batterytemp.setText("25.3" + "°C");
                                break;
                            }

                        }
                        mAdapter.notifyDataSetChanged();


                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("ERROR", "Unable to find icon for package '"
                                + packageName + "': " + e.getMessage());
                    }
                    // icons.put(processes.get(k).topActivity.getPackageName(),ico);


                }
            }

        }

        if (apps.size() > 1) {
            mAdapter = new RecyclerAdapter(apps);
            mAdapter.notifyDataSetChanged();
        }


    }


    @Override
    public boolean getUserVisibleHint() {

        MainActivity.name.setText("CPU Cooler");
        return getUserVisibleHint();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {
            MainActivity.name.setText("CPU Cooler");

        } else {

        }
    }
}

package volalizer.volalizer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;

import volalizer.volalizer.managers.ViewPagerAdapter;
import volalizer.volalizer.utils.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


    int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence[] Titles;
    int numbOfTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForPermissions(this);

        Titles = getResources().getStringArray(R.array.tab_names);

        numbOfTabs = Integer.parseInt(getResources().getString(R.string.tab_num));

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numbOfTabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); //fix Tab size

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabs.setViewPager(pager);
    }

    private void checkForPermissions(Context context) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);

        }
    }

}
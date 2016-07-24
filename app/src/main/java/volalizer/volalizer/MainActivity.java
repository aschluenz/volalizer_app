package volalizer.volalizer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import volalizer.volalizer.managers.ViewPagerAdapter;
import volalizer.volalizer.utils.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence[] Titles;
    private int numbOfTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}
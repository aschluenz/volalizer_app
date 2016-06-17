package volalizer.volalizer;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import volalizer.volalizer.managers.ViewPagerAdapter;
import volalizer.volalizer.utils.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence[] Titles;
    int numbOfTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharSequence mtitles[] = getResources().getStringArray(R.array.tab_names);
        Titles = mtitles;

        int mNumOfTabs = Integer.parseInt(getResources().getString(R.string.tab_num));
        numbOfTabs = mNumOfTabs;

        //create Toolbar
      //  toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);
        //create viewPagerAdapter
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

/*
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
}
 */
}
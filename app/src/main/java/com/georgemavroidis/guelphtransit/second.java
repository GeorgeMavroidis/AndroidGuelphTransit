package com.georgemavroidis.guelphtransit;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.georgemavroidis.guelphtransit.R;

public class second extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Weekday", "Saturday", "Sunday" };

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//
      // Initilization
      viewPager = (ViewPager) findViewById(R.id.pager);
      actionBar = getActionBar();
      mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

      viewPager.setAdapter(mAdapter);
      actionBar.setHomeButtonEnabled(true);
      actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
      actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff990000")));

      // Adding Tabs
      for (String tab_name : tabs) {
          actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));

      }
      actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff990000")));
      /**
       * on swiping the viewpager make respective tab selected
       * */
      viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

          @Override
          public void onPageSelected(int position) {
              // on changing the page
              // make respected tab selected
              actionBar.setSelectedNavigationItem(position);
          }

          @Override
          public void onPageScrolled(int arg0, float arg1, int arg2) {

          }

          @Override
          public void onPageScrollStateChanged(int arg0) {

          }
      });
  }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    private class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            String main = getIntent().getExtras().getString("main");
            switch (index) {
                case 0:
                    // Top Rated fragment activity
                    return time_view.newInstance(main, 0, getIntent().getExtras().getInt("position"));
                case 1:
                    // Games fragment activity
                    return time_view.newInstance(main ,1, getIntent().getExtras().getInt("position"));
                case 2:
                    // Movies fragment activity
                    return time_view.newInstance(main, 2, getIntent().getExtras().getInt("position"));
            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 3;
        }


  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        String data = getIntent().getExtras().getString("type");
        String main = getIntent().getExtras().getString("main");
        setTitle(main +" - " +data);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

  }

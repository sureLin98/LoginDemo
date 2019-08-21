package com.test.android;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    List<View> viewList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        toolbar.setTitle("主界面");
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);

        View view1 = getLayoutInflater().inflate(R.layout.layout_1, null);
        View view2 = getLayoutInflater().inflate(R.layout.layout_2, null);
        View view3 = getLayoutInflater().inflate(R.layout.layout_3, null);
        View view4 = getLayoutInflater().inflate(R.layout.layout_4, null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(adapter);

        final BottomNavigationView navigationView = findViewById(R.id.bottomnavigationview);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.interface_1:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.interface_2:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.interface_3:
                        viewPager.setCurrentItem(2);
                        return true;
                    case R.id.interface_4:
                        viewPager.setCurrentItem(3);
                        return true;

                }
                return false;
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        navigationView.setSelectedItemId(R.id.interface_1);
                        break;
                    case 1:
                        navigationView.setSelectedItemId(R.id.interface_2);
                        break;
                    case 2:
                        navigationView.setSelectedItemId(R.id.interface_3);
                        break;
                    case 3:
                        navigationView.setSelectedItemId(R.id.interface_4);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

}

package com.rupam.notesnavigationbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

class PagerAdapter extends FragmentPagerAdapter {
    int tabsCount;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior, int tabCount) {
        super(fm, behavior);
        this.tabsCount=tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new UrmiFragment();
            case 1: return new TusiFragment();
            case 2: return new AradhyaFragment();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle drawerToggle;

    BottomNavigationView bottomNavigationView;

    TabLayout tabLayout;
    TabItem urmi, tusi, aradhaya;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView=findViewById(R.id.navigation_View);
        drawerLayout=findViewById(R.id.drawer_Layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer_menu_selector_method(item);
                return false;
            }
        });

        drawerToggle=new ActionBarDrawerToggle(this, drawerLayout,R.string.start,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.inflateHeaderView(R.layout.nav_drawer_header_layout);
        bottomNavigationView=findViewById(R.id.bottom_nav_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                bottom_nav_menu_selector(item);
                return false;
            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.view_Pager);
        urmi=findViewById(R.id.urmi_tab);
        tusi=findViewById(R.id.tusi_tab);
        aradhaya=findViewById(R.id.aradhyaa_tab);

        pagerAdapter=new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
    void myFragmentSelector(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_Layout, fragment);
        fragmentTransaction.commit();
    }
    void drawer_menu_selector_method(MenuItem item){
        switch (item.getItemId()){
            case R.id.silde_nav_home:
                myFragmentSelector(new HomeFragment());
                break;
            case R.id.silde_nav_profile:
                myFragmentSelector(new ProfileFragment());
                break;
            case R.id.silde_nav_chat:
                myFragmentSelector(new ChatFragment());
                break;
        }
    }
    void bottom_nav_menu_selector(MenuItem item){
        switch (item.getItemId()){
            case R.id.rupam:
                myFragmentSelector(new RupamFragment());
                break;
            case R.id.ganguly:
                myFragmentSelector(new GangulyFragment());
                break;
            case R.id.rintu:
                myFragmentSelector(new RintuFragment());
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) return  true;
        return super.onOptionsItemSelected(item);
    }
}
package com.example.user.homework;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import static com.example.user.homework.RecycleViewFragment.*;

public class Bottom_Nav_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView mTextMessage;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private final int RECYCLE_IS_OPEN = 131;
    private final int VIEW_PAGER_IS_OPEN = 221;
    private final int HOME_PAGE_IS_OPEN = 341;
    private int CheckState;
    private Fragment fragment = new RecycleViewFragment();
    private MenuItem AZ_Sort;
    private MenuItem DownSort;
    private MenuItem UpSort;
    private boolean IS_AZ_SORTED = false;

    private BottomNavigationView
            .OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView
            .OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (CheckState != HOME_PAGE_IS_OPEN) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragm, new Home_Fragment())
                                .addToBackStack("lack")
                                .commit();
                        AZ_Sort.setVisible(false);
                        DownSort.setVisible(false);
                        UpSort.setVisible(false);
                    } else {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragm, new Home_Fragment())
                                .commit();
                    }
                    CheckState = HOME_PAGE_IS_OPEN;
                    return true;
                case R.id.navigation_dashboard:
                    if (CheckState != RECYCLE_IS_OPEN) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragm, fragment).addToBackStack("vack")
                                .commit();
                        AZ_Sort.setVisible(true);
                        DownSort.setVisible(true);
                    } else fragmentManager.beginTransaction()
                            .replace(R.id.fragm, fragment).commit();
                    CheckState = RECYCLE_IS_OPEN;
                    return true;
                case R.id.navigation_notifications:
                    if (CheckState != VIEW_PAGER_IS_OPEN) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragm, new VPFragment()).addToBackStack("sacl").commit();
                    } else
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragm, new VPFragment()).commit();
                    AZ_Sort.setVisible(false);
                    DownSort.setVisible(false);
                    UpSort.setVisible(false);
                    CheckState = VIEW_PAGER_IS_OPEN;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom__nav_);
        toolbar = findViewById(R.id.toolbar_actionbar);
        setActionBar(toolbar);
        CheckState = HOME_PAGE_IS_OPEN;
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager.beginTransaction()
                .replace(R.id.fragm, new Home_Fragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tollbar_menu, menu);
        AZ_Sort = menu.findItem(R.id.action_AZ_sort);
        DownSort = menu.findItem(R.id.downSort);
        UpSort = menu.findItem(R.id.upSort);
        AZ_Sort.setVisible(false);
        DownSort.setVisible(false);
        UpSort.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_AZ_sort: {
                if (!IS_AZ_SORTED) {
                    adapter.UpdateUp(Player.COMPARE_BY_NAME);
                    IS_AZ_SORTED = true;
                } else {
                    adapter.UpdateDown(Player.COMPARE_BY_NAME);
                    IS_AZ_SORTED = false;
                }
                return true;
            }
            case R.id.upSort: {
                adapter.UpdateUp(Player.COMPARE_BY_HEIGHT);
                DownSort.setVisible(true);
                UpSort.setVisible(false);
                return true;
            }
            case R.id.downSort: {
                adapter.UpdateDown(Player.COMPARE_BY_HEIGHT);
                DownSort.setVisible(false);
                UpSort.setVisible(true);
                return true;
            }
        }
        return false;
    }
}

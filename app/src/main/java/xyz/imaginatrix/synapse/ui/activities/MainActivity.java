package xyz.imaginatrix.synapse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.imaginatrix.synapse.R;
import xyz.imaginatrix.synapse.ui.fragments.AboutFragment;
import xyz.imaginatrix.synapse.ui.fragments.BookmarksFragment;
import xyz.imaginatrix.synapse.ui.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NAV_HOME = 0;
    private static final int NAV_BOOKMARKS = 1;
    private static final int NAV_ABOUT = 2;

    @BindView(R.id.main_appbar) AppBarLayout appBarLayout;
    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.main_viewPager) ViewPager viewPager;
    @BindView(R.id.main_nav_bar) BottomNavigationView navigationBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override public void onPageSelected(int position) {
                switch (position) {
                    case NAV_HOME:
                        navigationBar.setSelectedItemId(R.id.nav_home);
                        break;
                    case NAV_BOOKMARKS:
                        navigationBar.setSelectedItemId(R.id.nav_bookmarks);
                        break;
//                    case NAV_SETTINGS:
//                        navigationBar.setSelectedItemId(R.id.nav_settings);
//                        break;
                    case NAV_ABOUT:
                        navigationBar.setSelectedItemId(R.id.nav_about);
                        break;
                }
            }

            @Override public void onPageScrollStateChanged(int state) { }
        });

        navigationBar.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    viewPager.setCurrentItem(NAV_HOME, true);
                    return true;
                case R.id.nav_bookmarks:
                    viewPager.setCurrentItem(NAV_BOOKMARKS, true);
                    return true;
//                case R.id.nav_settings:
//                    viewPager.setCurrentItem(NAV_SETTINGS, true);
//                    return true;
                case R.id.nav_about:
                    viewPager.setCurrentItem(NAV_ABOUT, true);
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {

        public MainPagerAdapter(FragmentManager fm) { super(fm); }

        @Override public Fragment getItem(int position) {
            switch (position) {
                case NAV_HOME:
                    return HomeFragment.newInstance();
                case NAV_BOOKMARKS:
                    return BookmarksFragment.newInstance();
//                case NAV_SETTINGS:
//                    return SettingsFragment.newInstance();
                case NAV_ABOUT:
                    return AboutFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override public int getCount() { return 3; }
    }
}

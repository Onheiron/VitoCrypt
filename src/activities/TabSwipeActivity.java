package activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
 
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
 
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
 
public abstract class TabSwipeActivity extends SherlockFragmentActivity {
 
    private ViewPager mViewPager;
    private TabsAdapter adapter;
    private static Fragment[] fragments = new Fragment[4];
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mViewPager = new ViewPager(this);
        adapter = new TabsAdapter(this, mViewPager);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(adapter);
        mViewPager.setId( 0x7F04FFF0 );
        super.onCreate(savedInstanceState);
        setContentView(mViewPager);
    }
 
    protected void addTab(int titleRes, Class<?> fragmentClass) { adapter.addTab(getString(titleRes), fragmentClass); }
    protected void addTab(CharSequence title, Class<?> fragmentClass) { adapter.addTab(title, fragmentClass); }
 
    private static class TabsAdapter extends FragmentStatePagerAdapter implements TabListener, ViewPager.OnPageChangeListener {
 
        private final SherlockFragmentActivity mActivity;
        private final ActionBar mActionBar;
        private final ViewPager mPager;
        private List<Class<?>> mTabs = new ArrayList<Class<?>>();
 
        public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            this.mActivity = activity;
            this.mActionBar = activity.getSupportActionBar();
            this.mPager = pager;
            mActionBar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
        }
 
        public void addTab(CharSequence title, Class<?> fragmentClass) {
            Tab tab = mActionBar.newTab();
            tab.setText(title);
            tab.setTabListener( this );
            tab.setTag(fragmentClass);
            mTabs.add(fragmentClass);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }
 
        @Override
        public Fragment getItem(int position) {
            Fragment f = (Fragment) Fragment.instantiate( mActivity, mTabs.get(position).getName());
            fragments[position] = f;
            return f;
        }
 
        @Override
        public int getCount() { return mTabs.size(); }
        public void onPageScrollStateChanged(int arg0) {}
        public void onPageScrolled(int arg0, float arg1, int arg2) {}
 
        public void onPageSelected(int position) { mActionBar.setSelectedNavigationItem(position); }
 
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            Class<?> currentFragmentClass = (Class<?>) tab.getTag();
            for (int i = 0; i < mTabs.size(); i++) {
                if (mTabs.get(i) == currentFragmentClass) {
                    mPager.setCurrentItem(i);
                    if(fragments[i] != null) ((GenericFragment)fragments[i]).onSelect();
                }
            }
        }
 
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        	File scrap = new File(Environment.getExternalStorageDirectory() + "/VitoCrypt/TMP/");
        	for(File file: scrap.listFiles()) file.delete();
        }
        
        public void onTabReselected(Tab tab, FragmentTransaction ft) {}
    }
}

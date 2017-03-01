package kj.hanwei.com.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import kj.hanwei.com.R;
import kj.hanwei.com.fragment.BaseFragment;
import kj.hanwei.com.head.HeadControlPanel;
import kj.hanwei.com.tab.BottomControlPanel;
import kj.hanwei.com.tab.Constant;
import kj.hanwei.com.tab.TabView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomControlPanel.BottomPanelCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //侧滑的按钮
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupViews();

        initUI();

        fragmentManager = getFragmentManager();
        setDefaultFirstFragment(Constant.FRAGMENT_FLAG_MESSAGE);
    }

    private void setDefaultFirstFragment(String tag){
        setTabSelection(tag);
        bottomPanel.defaultBtnChecked();
    }

    /**设置选中的Tag
     * @param tag
     */
    public  void setTabSelection(String tag) {
        // 开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();

        switchFragment(tag);

    }
    public static String currFragTag = "";
    /**切换fragment
     * @param tag
     */
    private  void switchFragment(String tag){
        if(TextUtils.equals(tag, currFragTag)){
            return;
        }
        //把上一个fragment detach掉
        if(currFragTag != null && !currFragTag.equals("")){
            detachFragment(getFragment(currFragTag));
        }
        attachFragment(R.id.fragment_content, getFragment(tag), tag);
        commitTransactions(tag);
    }

    private void attachFragment(int layout, Fragment f, String tag){
        if(f != null){
            if(f.isDetached()){
                ensureTransaction();
                fragmentTransaction.attach(f);

            }else if(!f.isAdded()){
                ensureTransaction();
                fragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private void commitTransactions(String tag){
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            currFragTag = tag;
            fragmentTransaction = null;
        }
    }

    private void detachFragment(Fragment f){

        if(f != null && !f.isDetached()){
            ensureTransaction();
            fragmentTransaction.detach(f);
        }
    }

    private Fragment getFragment(String tag){

        Fragment f = fragmentManager.findFragmentByTag(tag);

        if(f == null){
            Toast.makeText(getApplicationContext(), "fragment = null tag = " + tag, Toast.LENGTH_SHORT).show();
            f = BaseFragment.newInstance(getApplicationContext(), tag);
        }
        return f;
    }

    private FragmentTransaction ensureTransaction( ){
        if(fragmentTransaction == null){
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }
        return fragmentTransaction;

    }
    private FragmentTransaction fragmentTransaction = null;
    private FragmentManager fragmentManager = null;
    BottomControlPanel bottomPanel = null;
    HeadControlPanel headPanel = null;
    private void initUI() {

        bottomPanel = (BottomControlPanel)findViewById(R.id.bottom_layout);
        if(bottomPanel != null){
            bottomPanel.initBottomPanel();
            bottomPanel.setBottomCallback(this);
        }
        headPanel = (HeadControlPanel)findViewById(R.id.head_layout);
        if(headPanel != null){
            headPanel.initHeadPanel();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(MainActivity.this,"待开发...",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(MainActivity.this,"待开发...",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_manage) {
            Toast.makeText(MainActivity.this,"待开发...",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("image/*");//分享图片   一般传一个url来加载图片
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
            intent.putExtra(Intent.EXTRA_TEXT, "分享给你一个好网站:http://www.apkbus.com/space-uid-784586.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "好友分享"));
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this,"待开发...",Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private TabView mTabView;
    private TextView mTitleTextView;
    /** 当前状态 */
    private int mCurrentTabIndex = 1;
    private Fragment mCurrentFragment;
    /**
     * 布局数据
     */
    private void setupViews() {


    }

    @Override
    public void onBottomPanelClick(int itemId) {

        String tag = "";
        if((itemId & Constant.BTN_FLAG_MESSAGE) != 0){
            tag = Constant.FRAGMENT_FLAG_MESSAGE;
        }else if((itemId & Constant.BTN_FLAG_CONTACTS) != 0){
            tag = Constant.FRAGMENT_FLAG_CONTACTS;
        }else if((itemId & Constant.BTN_FLAG_NEWS) != 0){
            tag = Constant.FRAGMENT_FLAG_NEWS;
        }else if((itemId & Constant.BTN_FLAG_SETTING) != 0){
            tag = Constant.FRAGMENT_FLAG_SETTING;
        }
        setTabSelection(tag); //切换Fragment
        headPanel.setMiddleTitle(tag);//切换标题

    }
}

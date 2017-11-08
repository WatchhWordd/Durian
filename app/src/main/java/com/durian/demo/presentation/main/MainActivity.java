package com.durian.demo.presentation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.durian.demo.R;
import com.durian.demo.base.utils.ACache;
import com.durian.demo.base.utils.ConfigUtil;
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.presentation.main.widget.MainDrawerListener;
import com.durian.demo.presentation.overview.OverViewFragment;

public class MainActivity extends AppCompatActivity implements MainContract.View, DrawerLayout.DrawerListener {

    private MainContract.Presenter presenter;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfo = (UserInfo) ACache.get(this).getAsObject(ConfigUtil.S_USER_INFO);
        if (userInfo == null) {
            userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
        }
        initViews();
        new MainPresenter(this);
        presenter.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initViews() {
        initTabLayout();
        initDrawerLayout();
        initContentLayout();
    }

    @Override
    public void setupFragments() {
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.id_main_fragment_container,
                OverViewFragment.newInstance(userInfo.getLogin(), "subject"),
                OverViewFragment.class.getSimpleName());
        transaction.commit();
    }

    @Override
    public void appendFragments() {

    }

    private void initTabLayout() {
        toolbar = (Toolbar) findViewById(R.id.id_main_tab);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.id_main_drawer);
        MainDrawerListener mainDrawerListener = new MainDrawerListener(this, drawerLayout,
                toolbar, R.string.open_string, R.string.close_string);
        mainDrawerListener.setDrawerListener(this);
        mainDrawerListener.syncState();
        drawerLayout.setDrawerListener(mainDrawerListener);
    }

    private void initContentLayout() {
        initPersonView();
        initContentView();
    }

    private void initPersonView() {
        ImageView avatarView = (ImageView) findViewById(R.id.id_main_drawer_avatar);
        TextView nameView = (TextView) findViewById(R.id.id_main_drawer_user_account);
        TextView orgView = (TextView) findViewById(R.id.id_main_drawer_user_org);
        TextView positionView = (TextView) findViewById(R.id.id_main_drawer_user_position);
        TextView joinDataView = (TextView) findViewById(R.id.id_main_drawer_user_join_date);
        if (userInfo != null) {
            Glide.with(this).load(userInfo.getAvatarUrl())
                    .error(R.drawable.ic_perm_identity_white_24dp)
                    .into(avatarView);
            setTitle(String.format(getString(R.string.main_title_suffix), userInfo.getName()));
            toolbar.setSubtitle(String.format(getString(R.string.main_sub_title_suffix), userInfo.getName(),
                    String.valueOf(userInfo.getId())));
            nameView.setText(userInfo.getName());
            orgView.setText(userInfo.getCompany());
            positionView.setText(userInfo.getLocation());
            joinDataView.setText(userInfo.getCreatedAt());
        }
    }

    private void initContentView() {
        View overViewLayout = findViewById(R.id.id_main_drawer_item_menu_overview);
        ImageView overView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_overview);

        View repositoriesLayout = findViewById(R.id.id_main_drawer_item_menu_repositories);
        ImageView repositoriesView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_repositories);

        View starsLayout = findViewById(R.id.id_main_drawer_item_menu_stars);
        ImageView starsView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_stars);

        View followersLayout = findViewById(R.id.id_main_drawer_item_menu_followers);
        ImageView followersView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_followers);

        View followingLayout = findViewById(R.id.id_main_drawer_item_menu_following);
        ImageView followingView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_following);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        //TO-Do
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}

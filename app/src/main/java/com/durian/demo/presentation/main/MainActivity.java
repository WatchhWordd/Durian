package com.durian.demo.presentation.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.durian.demo.data.net.bean.UserInfo;
import com.durian.demo.presentation.followers.FollowersFragment;
import com.durian.demo.presentation.following.FollowingFragment;
import com.durian.demo.presentation.main.widget.MainDrawerListener;
import com.durian.demo.presentation.overview.OverViewContract;
import com.durian.demo.presentation.overview.OverViewFragment;
import com.durian.demo.presentation.repositories.RepositoriesFragment;
import com.durian.demo.presentation.stars.StarsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        DrawerLayout.DrawerListener {

    private MainContract.Presenter presenter;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private View overViewLayout;
    private ImageView overView;
    private View repositoriesLayout;
    private ImageView repositoriesView;
    private View starsLayout;
    private ImageView starsView;
    private View followersLayout;
    private ImageView followersView;
    private View followingLayout;
    private ImageView followingView;

    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
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
        setupFragments();
        overViewLayout.setSelected(true);
        overView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupFragments() {
        appendFragments();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void appendFragments() {
        if (getSupportFragmentManager().getFragments()
                .size() >= 4) {//如果已加载的fragment数目大于等于4，则说明无需再append
            return;
        }
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.id_main_fragment_container,
                OverViewFragment.newInstance(userInfo.getLogin(), "subject"),
                OverViewFragment.class.getSimpleName());
        transaction.add(R.id.id_main_fragment_container,
                RepositoriesFragment.newInstance(userInfo.getLogin(), "subject"), RepositoriesFragment.class
                        .getSimpleName());
        transaction.add(R.id.id_main_fragment_container,
                StarsFragment.newInstance(userInfo.getLogin(), "subject"), StarsFragment.class
                        .getSimpleName());
        transaction.add(R.id.id_main_fragment_container,
                FollowersFragment.newInstance(userInfo.getLogin(), "subject"), FollowersFragment.class
                        .getSimpleName());
        transaction.add(R.id.id_main_fragment_container,
                FollowingFragment.newInstance(userInfo.getLogin(), "subject"), FollowingFragment.class
                        .getSimpleName());
        transaction.commit();
    }

    @Override
    public void updateOverView(Integer tabIndex) {

        OverViewFragment overViewFragment = (OverViewFragment) getSupportFragmentManager()
                .findFragmentByTag(OverViewFragment.class.getSimpleName());
        OverViewContract.View view = overViewFragment;
        OverViewContract.Presenter presenter = (OverViewContract.Presenter) overViewFragment;
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
            nameView.setText(userInfo.getName());
            orgView.setText(userInfo.getCompany());
            positionView.setText(userInfo.getLocation());
            joinDataView.setText(userInfo.getCreatedAt());
        }
    }

    private void initContentView() {
        overViewLayout = findViewById(R.id.id_main_drawer_item_menu_overview);
        overView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_overview);

        repositoriesLayout = findViewById(R.id.id_main_drawer_item_menu_repositories);
        repositoriesView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_repositories);

        starsLayout = findViewById(R.id.id_main_drawer_item_menu_stars);
        starsView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_stars);

        followersLayout = findViewById(R.id.id_main_drawer_item_menu_followers);
        followersView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_followers);

        followingLayout = findViewById(R.id.id_main_drawer_item_menu_following);
        followingView = (ImageView) findViewById(R.id.id_main_drawer_status_item_menu_following);

        overViewLayout.setOnClickListener(view ->
                navigateItemSelected(R.id.id_main_drawer_item_menu_overview));
        repositoriesLayout.setOnClickListener(view ->
                navigateItemSelected(R.id.id_main_drawer_item_menu_repositories));
        starsLayout.setOnClickListener(view ->
                navigateItemSelected(R.id.id_main_drawer_item_menu_stars));
        followersLayout.setOnClickListener(view ->
                navigateItemSelected(R.id.id_main_drawer_item_menu_followers));
        followingLayout.setOnClickListener(view ->
                navigateItemSelected(R.id.id_main_drawer_item_menu_following));
    }

    private void navigateItemSelected(int i) {
        overView.setVisibility(View.INVISIBLE);
        overViewLayout.setSelected(false);
        repositoriesView.setVisibility(View.INVISIBLE);
        repositoriesLayout.setSelected(false);
        starsView.setVisibility(View.INVISIBLE);
        starsLayout.setSelected(false);
        followersView.setVisibility(View.INVISIBLE);
        followersLayout.setSelected(false);
        followingView.setVisibility(View.INVISIBLE);
        followingLayout.setSelected(false);
        if (i == R.id.id_main_drawer_item_menu_overview) {
            overView.setVisibility(View.VISIBLE);
            overViewLayout.setSelected(true);
            navigateToFragment(OverViewFragment.class.getSimpleName());
            setTitle(String.format(getString(R.string.main_title_suffix), userInfo.getName()));
        } else if (i == R.id.id_main_drawer_item_menu_repositories) {
            repositoriesView.setVisibility(View.VISIBLE);
            repositoriesLayout.setSelected(true);
            navigateToFragment(RepositoriesFragment.class.getSimpleName());
            setTitle(R.string.main_nav_repositories);
        } else if (i == R.id.id_main_drawer_item_menu_stars) {
            starsView.setVisibility(View.VISIBLE);
            starsLayout.setSelected(true);
            navigateToFragment(StarsFragment.class.getSimpleName());
            setTitle(R.string.main_nav_stars);
        } else if (i == R.id.id_main_drawer_item_menu_followers) {
            followersView.setVisibility(View.VISIBLE);
            followersLayout.setSelected(true);
            navigateToFragment(FollowersFragment.class.getSimpleName());
            setTitle(R.string.main_nav_followers);
        } else if (i == R.id.id_main_drawer_item_menu_following) {
            followingView.setVisibility(View.VISIBLE);
            followingLayout.setSelected(true);
            navigateToFragment(FollowingFragment.class.getSimpleName());
            setTitle(R.string.main_nav_following);
        }
    }

    @SuppressLint("RestrictedApi")
    private void navigateToFragment(String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment toShow = getSupportFragmentManager().findFragmentByTag(tag);
        List<Fragment> toHideList = new ArrayList<>(getSupportFragmentManager().getFragments());
        if (toShow == null) {
            return;
        } else if (toHideList.contains(toShow)) {
            toHideList.remove(toShow);
            transaction.show(toShow);
        }
        if (!toHideList.isEmpty()) {
            for (Fragment fragment : toHideList) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

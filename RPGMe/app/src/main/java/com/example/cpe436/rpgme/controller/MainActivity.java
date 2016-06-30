package com.example.cpe436.rpgme.controller;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.cpe436.rpgme.R;
import com.example.cpe436.rpgme.common.StorageTool;
import com.example.cpe436.rpgme.common.Values;
import com.example.cpe436.rpgme.model.Character;

import java.util.Date;

/**
 * This class sets up the navigation drawer and tool bar.
 * Other applications extend this to make use of the drawer and toolbar.
 * Displays the main home screen.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavBar();
        if (savedInstanceState == null) {
            loadFragmentById(R.layout.fragment_main, getResources().getString(R.string.app_name), new HomeFragment());
        }
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        recover();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recover();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Return to home screen; finished
        if (id == R.id.nav_home) {
            startNewActivity(MainActivity.class);
        }
        // Launch quest activity; high priority
        else if (id == R.id.nav_quests) {
            startNewActivity(QuestActivity.class);
        }
        // Launch battle activity; may not be implemented
        else if (id == R.id.nav_battle) {
            startNewActivity(BattleActivity.class);
        }
        // Launch shop activity; may not be implemented
        else if (id == R.id.nav_shop) {

        }
        // Launch character info+customization; medium priority (shouldn't be too hard)
        else if (id == R.id.nav_character) {
            startNewActivity(CharacterActivity.class);
        }
        // Launch intelligence training activity; low priority
        else if (id == R.id.nav_training_int) {

        }
        // Launch strength training activity; medium priority (basic functionality done)
        else if (id == R.id.nav_training_str) {
            startNewActivity(StrengthActivity.class);

        }
        // Launch stamina training activity; medium priority (basic functionality done)
        else if (id == R.id.nav_training_sta) {
            startNewActivity(StaminaActivity.class);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Loads a new fragment into activity and changes title
     */
    public void loadFragmentById(int loadId, String newTitle, Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();

        // Fragment not specified, use default
        if (fragment == null) {
            fragment = new MyFragment();
        }

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("id", loadId);
        args.putString("name", newTitle);
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
        setTitle(newTitle);
    }

    /**
     * Launches a new activity
     */
    public void startNewActivity(Class newClass) {
        Intent intent = new Intent(this, newClass);
        startActivity(intent);
    }

    /**
     * Sets up tool bar, navigation drawer, and action bar
     */
    private void setupNavBar() {
        // Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup navigation drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Recovers HP and MP
     */
    private void recover() {
        Date lastLog = StorageTool.getLastLoginTime(this);
        Date curLog = new Date();
        Character character = Character.getCharacterInstance(this);

        if (lastLog == null) {
            StorageTool.saveLastLoginTime(this);
            return;
        }

        long diffMillis = curLog.getTime() - lastLog.getTime();
        int hp = character.getCurHP() + (int) (diffMillis/Values.HP_RECOVERY_TIME);
        int mp = character.getCurMP() + (int) (diffMillis/Values.MP_RECOVERY_TIME);
        character.setCurHP(hp, this);
        character.setCurMP(mp, this);

        StorageTool.saveLastLoginTime(this);
    }
}
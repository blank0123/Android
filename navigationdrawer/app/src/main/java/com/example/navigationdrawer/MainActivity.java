package com.example.navigationdrawer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.example.navigationdrawer.zxing.activity.CaptureActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//////////////
        final LinearLayout tv_lin= findViewById(R.id.text_lin);//要显示的字体
        final LinearLayout tv_hide_lin= findViewById(R.id.text_hide_lin);//所谓的布
        final RelativeLayout rl =findViewById(R.id.start);
        ImageView logo = findViewById(R.id.sakula);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash);
        logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.text_splash_position);
                tv_lin.startAnimation(animation);
                animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.text_canvas);
                tv_hide_lin.startAnimation(animation);
                animation.setDuration(1000);
                animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.canvas_leave);
                rl.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
////////////////


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        ImageView mImageView = findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this,"HELLO FIRST",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this,User_login.class);
                startActivity(intent);

            }
        });

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "设置！！！！", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_scan:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                startQrCode();



                Toast.makeText(this, "扫一扫", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.home:
//                Toast.makeText(this, "home!!!", Toast.LENGTH_SHORT).show();
//                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void startQrCode(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
          if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

          }
          ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},11003);
          return;
        }
        Intent intent = new Intent(MainActivity.this,CaptureActivity.class);
        startActivityForResult(intent,11002);
    }





}

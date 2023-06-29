package com.example.boutiqueshop;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.boutiqueshop.R;
import com.example.boutiqueshop.databinding.ActivityMainBinding;
import com.example.boutiqueshop.ui.dashboard.DashboardFragment;
import com.example.boutiqueshop.ui.gallery.GalleryFragment;
import com.example.boutiqueshop.ui.home.HomeFragment;
import com.example.boutiqueshop.ui.notifications.NotificationsFragment;
import com.example.boutiqueshop.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private SparseArray<LottieAnimationView> animationViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawerLayout)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return MainActivity.this.onNavigationItemSelected(item);
            }
        });

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        switch (itemId) {
            case R.id.nav_home:
                replaceFragment(new HomeFragment());
                break;
            case R.id.nav_gallery:
                replaceFragment(new GalleryFragment());

                break;
            case R.id.nav_slideshow:
                replaceFragment(new SlideshowFragment());

                break;
            case R.id.navigation_home:
                replaceFragment(new HomeFragment());
                setLottieAnimation(itemId, R.raw.home);
                break;
            case R.id.navigation_dashboard:
                replaceFragment(new DashboardFragment());
                setLottieAnimation(itemId, R.raw.dashboard);
                break;
            case R.id.navigation_notifications:
                replaceFragment(new NotificationsFragment());
                setLottieAnimation(itemId, R.raw.notifications);
                break;
            default:
                break;
        }
        // Cerrar el Navigation Drawer si está abierto
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
        fragmentTransaction.commit();
    }

    private void setLottieAnimation(int menuItemId, int animationResId) {
        if (animationViews == null) {
            animationViews = new SparseArray<>();
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.CENTER;
            params.weight = 1;

            for (int i = 0; i < menuView.getChildCount(); i++) {
                View menuItemView = menuView.getChildAt(i);
                BottomNavigationItemView item = (BottomNavigationItemView) menuItemView;

                int id = item.getId();
                LottieAnimationView animationView = new LottieAnimationView(this);
                animationView.setLayoutParams(params);
                animationViews.put(id, animationView);
                item.addView(animationView);
            }
        }

        for (int i = 0; i < animationViews.size(); i++) {
            int id = animationViews.keyAt(i);
            LottieAnimationView animationView = animationViews.valueAt(i);
            if (id == menuItemId) {
                animationView.setAnimation(animationResId);
                animationView.playAnimation();
                animationView.setVisibility(View.VISIBLE);
            } else {
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);
            }
        }
    }

}

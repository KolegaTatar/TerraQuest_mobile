package edu.zsk.terraquest;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.zsk.terraquest.ui.ExploreFragment;
import edu.zsk.terraquest.ui.HomeFragment;
import edu.zsk.terraquest.ui.User_menuFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();

            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoggedIn = getSharedPreferences("user_prefs", 0)
                .getBoolean("is_logged_in", false);

        MenuItem loginItem = bottomNav.getMenu().findItem(R.id.nav_login);
        if (isLoggedIn) {
            loginItem.setTitle("Profil");
        } else {
            loginItem.setTitle("Zaloguj");
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                Fragment selectedFragment = null;

                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    if (!(currentFragment instanceof HomeFragment)) {
                        selectedFragment = new HomeFragment();
                    }
                } else if (id == R.id.nav_search) {
                    if (!(currentFragment instanceof ExploreFragment)) {
                        selectedFragment = new ExploreFragment();
                    }
                } else if (id == R.id.nav_login) {
                    if (!(currentFragment instanceof User_menuFragment)) {
                        selectedFragment = new User_menuFragment();
                    }
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            };
}

package team.everywhere.javaproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import team.everywhere.javaproject.R;
import team.everywhere.javaproject.adapters.ViewPagerFragmentAdapter;
import team.everywhere.javaproject.databinding.ActivityMainBinding;
import team.everywhere.javaproject.models.User;
import team.everywhere.javaproject.utils.SaveSharedPreference;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginListener {
    private static final String TAG = "MainActivity";
    private static MainActivity mActivity;
    ActivityMainBinding binding;

    LoginFragment loginFragment;

    String animalName;

    public static ProgressBar pbMain;

    private SeekBar sb;
    private InputStream is;
    private int len;
    private String myurl;

    private User user;

    private boolean isTabVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

        setListener();

        initLogin();

        setNavigation();


        binding.setMain(this);
    }

    private void setNavigation() {
        View view = binding.navigation.getHeaderView(0);
        TextView textView = view.findViewById(R.id.textView);

        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuFirst:

                        break;
                    case R.id.menuSecond:

                        break;
                    case R.id.menuThird:

                        break;
                    case R.id.menuFourth:

                        break;
                    case R.id.menuFifth:

                        break;

                }

                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initLogin() {
        Bundle bundle = new Bundle();
        bundle.putString("name", "사람이름");
        bundle.putInt("age", 30);
        bundle.putString("birthday", "20200101");
        Log.d(TAG, "eventClick: bundle : " + bundle);
        loginFragment = new LoginFragment();
        loginFragment.setArguments(bundle);

//        Intent intent = new Intent(this, MainActivity.class);
//
//        intent.putExtras(bundle);
//        intent.putExtra("name", "이름");
//
//        startActivity(intent);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frameLayout, loginFragment).commit();

//        ft.add(R.id.frameLayout, loginFragment).addToBackStack("login").commit();
    }


    private void setListener() {

    }


    private void init() {
        mActivity = this;
        pbMain = binding.pbMain;


    }


    @Override
    protected void onResume() {
        super.onResume();
        init();
    }


    public void eventTest(View view) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain, fragment).commit();
    }

    public void eventUserInfo(View view) {
        UserInfoFragment fragment = new UserInfoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }

    public void eventDrawer(View view) {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    public void eventLogout(View view) {
        SaveSharedPreference.clearAll(this);
        initLogin();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static void showMainProgressBar(boolean b) {
        if (b) {
            pbMain.setVisibility(View.VISIBLE);
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            pbMain.setVisibility(View.GONE);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    private void testUrl() {
        Log.d(TAG, "testUrl: called");
        is = null;
        len = 1000;

        myurl = "https://tutorial.team-everywhere.com/api/user";
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    URL url = new URL(myurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.d(TAG, "response : " + response);
                    is = conn.getInputStream();
                    String contentAsString = readIt(is, len);
                    Log.d(TAG, "testUrl: content : " + contentAsString);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        }.execute();

    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    @Override
    public void onLogin(User user) {
        if (loginFragment != null) {
            loginFragment.getFragmentManager().beginTransaction().detach(loginFragment).commit();
        }
//        onBackPressed();
        Log.d(TAG, "onLogin: user : " + user.getId());
    }
}
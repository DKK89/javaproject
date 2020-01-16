package team.everywhere.javaproject.views;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import team.everywhere.javaproject.R;
import team.everywhere.javaproject.adapters.ViewPagerAdatper;

public class ViewPagerFragment extends Fragment {
    private static final String TAG = "ViewPagerFragment";
    ViewPager viewPager;
    public static TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.VISIBLE);

        Uri uri1 = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.cat);
        Uri uri2 = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.dog);
        Uri uri3 = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.racoon);
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        uriArrayList.add(uri1);
        uriArrayList.add(uri2);
        uriArrayList.add(uri3);

        ViewPagerAdatper vpAdapter = new ViewPagerAdatper(getContext(), uriArrayList);
        viewPager.setAdapter(vpAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabLayout.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }
}

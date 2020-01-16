package team.everywhere.javaproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import team.everywhere.javaproject.views.LoginFragment;
import team.everywhere.javaproject.views.SignUpFragment;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private int mPageCount;

    public ViewPagerFragmentAdapter(@NonNull FragmentManager fm, int behavior, int pageCount) {
        super(fm, behavior);
        this.mPageCount = pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new SignUpFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}

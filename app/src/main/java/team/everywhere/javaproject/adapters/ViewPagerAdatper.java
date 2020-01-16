package team.everywhere.javaproject.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import team.everywhere.javaproject.R;

public class ViewPagerAdatper extends PagerAdapter {
    Context mContext;
    ArrayList<Uri> imageList;

    @Override
    public int getCount() {
        if (imageList != null) {
            return imageList.size();
        }
        return 0;
    }

    public ViewPagerAdatper(Context mContext, ArrayList<Uri> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

//    @Nullable
//    @Override
//    public Parcelable saveState() {
//        return null;
//    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_viewpager, container, false);

        ImageView ivImage = view.findViewById(R.id.ivImage);
        if (imageList != null) {
            Uri uri = imageList.get(position);
            Picasso.get().load(uri).fit().centerCrop().into(ivImage);
        }
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}

package com.zoom.happiestplacesrestaurant.ui.order.display;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.ui.order.OrderFragment;
import com.zoom.happiestplacesrestaurant.util.AppConstants;

import java.util.UUID;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int TAB_ITEM_SIZE = 2;
    public static final String[] TAB_NAMES={"Current","Completed"};
    UUID mRestId;

    public String getTabName(int position)
    {
        return TAB_NAMES[position];
    }


    public ViewPagerAdapter(@NonNull Fragment fragment, UUID restId) {
        super(fragment);
        mRestId=restId;
    }

    @NonNull @Override public Fragment createFragment(int position) {
        if(position==0)
            return OrderFragment.newInstance(mRestId,"ALL");
        else
            return OrderFragment.newInstance(mRestId, AppConstants.Status.Paid.toString());
    }
    @Override public int getItemCount() {
        return TAB_ITEM_SIZE;
    }

    public int getTabIcon(int position) {
        if(position==0)
        return R.drawable.ic_action_current;
        else
            return R.drawable.ic_action_time;

    }
}
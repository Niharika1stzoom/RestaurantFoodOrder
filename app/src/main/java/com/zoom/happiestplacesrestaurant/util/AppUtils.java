package com.zoom.happiestplacesrestaurant.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.model.Restaurant;

import java.util.UUID;


public class AppUtils {
    public static void setImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .into(imageView);
    }

    /*    public static void shareFoodMenu(Context context, MenuItem foodMenu) {
        String foodDesc=foodMenu.getDescription();
        if(foodDesc==null)
            foodDesc="";
        else
            foodDesc=foodDesc+"\n";
        String foodDetail=foodMenu.getName()+"\n"+foodDesc;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT, foodDetail);
        context.startActivity(Intent.createChooser(shareIntent, "Choose one"));
    }
    */
    public static void showSnackbar(View view, String msg){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
    public static boolean isNetworkAvailableAndConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    public static Bundle getBundleForOrderFragment(UUID mRestId, String status) {
        Bundle bundle=new Bundle();
        bundle.putString(AppConstants.KEY_RESTAURANT_ID,String.valueOf(mRestId));
        bundle.putString(AppConstants.KEY_STATUS,status);
        return bundle;

    }

    public static boolean checkUserAccessCheckout(Context context) {
        Restaurant rest=SharedPrefUtils.getUser(context);
        if(rest.getOwner())
            return true;
        else
            if(rest.getAccess().getCheck_out())
                return true;
            return false;
    }
    public static String getHeaderToken(Context context) {
        return "Token "+SharedPrefUtils.getUser(context).getToken();
    }
}

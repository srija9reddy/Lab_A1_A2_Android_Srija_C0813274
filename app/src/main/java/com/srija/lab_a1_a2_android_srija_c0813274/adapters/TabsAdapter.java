package com.srija.lab_a1_a2_android_srija_c0813274.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.srija.lab_a1_a2_android_srija_c0813274.ProductsFragment;
import com.srija.lab_a1_a2_android_srija_c0813274.ProviderFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    public TabsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductsFragment productsFragment = new ProductsFragment();
                return productsFragment;
            case 1:
                ProviderFragment providerFragment = new ProviderFragment();
                return providerFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Products";
            case 1:
                return "Providers";
            default:
                return null;
        }
    }
}

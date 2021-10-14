package com.srija.lab_a1_a2_android_srija_c0813274;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.TabsAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Product;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SQLiteHandler db;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsAdapter tabsAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        db = new SQLiteHandler(this);
        if (db.getProviders().size() == 0) {
            fillDatabase();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.main_tabs);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void fillDatabase() {

        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider("Store 1", "store1@gmail.com", "+17638291333", 54.68, -77.49));
        providers.add(new Provider("Store 2", "store2@gmail.com", "+16473435455", 44.74, -76.70));
        db.addProviders(providers);

        providers = db.getProviders();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < providers.size(); i++) {
            for (int j = 0; j < 10; j++)
                products.add(new Product("Product " + j, "Product Description of " + providers.get(i).getProviderName() + " id: " + j, 13 * 123 * j+2, providers.get(i).getProviderId()));
        }
        db.addProducts(products);
    }
}
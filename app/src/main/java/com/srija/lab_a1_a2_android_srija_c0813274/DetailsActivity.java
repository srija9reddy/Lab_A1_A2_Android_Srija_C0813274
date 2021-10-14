package com.srija.lab_a1_a2_android_srija_c0813274;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProductsRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Product;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class DetailsActivity extends AppCompatActivity {
    private int id = -1;
    private int type = 0;
    private boolean screenType = false;
    private AppCompatEditText name, des_email, price_phone;
    private TextInputLayout n, d, p;
    private FrameLayout provider_details;
    private TextView prov_n, prov_e, prov_p;
    private RecyclerView recyclerView;
    private SQLiteHandler db;


    private List<Product> products;
    private Product product;
    private Provider provider;
    private ProductsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        db = new SQLiteHandler(this);
        recyclerView = findViewById(R.id.recyclerView_product);
        products = new ArrayList<>();
        adapter = new ProductsRecyclerViewAdapter(this, products);
        recyclerView.setAdapter(adapter);
        name = findViewById(R.id.appCompatEditText_name);
        des_email = findViewById(R.id.appCompatEditText_desc_email);
        price_phone = findViewById(R.id.appCompatEditText_price_phone);
        n = findViewById(R.id.textInputLayout_name_name);
        d = findViewById(R.id.textInputLayout_desc_email);
        p = findViewById(R.id.textInputLayout_price_phone);
        provider_details = findViewById(R.id.frameLayout_provider_details);
        prov_n = findViewById(R.id.textView_prov_name);
        prov_e = findViewById(R.id.textView_prov_email);
        prov_p = findViewById(R.id.textView_prov_phone);

        Bundle extras = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            id = extras.getInt("id", -1);
            type = extras.getInt("screenType", 0);
            screenType = extras.getBoolean("screenType", false);
            setupViews(type);
            setScreenType(screenType);
        }
    }

    private void setupViews(int type) {
        boolean b = !(type == 0);
        name.setEnabled(b);
        des_email.setEnabled(b);
        price_phone.setEnabled(b);
    }

    private void setScreenType(boolean screenType) {
        if (screenType) {// Product
            product = db.getProduct(id);
            name.setText(product.getProductName());
            des_email.setText(product.getProductDescription());
            price_phone.setText(product.getProductPrice() + "");

            provider = db.getProvider(product.getProviderId());
            prov_n.setText(provider.getProviderName());
            prov_e.setText(provider.getProviderEmail());
            prov_p.setText(provider.getProviderPhone());

            setTitle("Product");
            recyclerView.setVisibility(GONE);
            d.setHint("Description");
            p.setHint("Price $");
        } else {// Provider
            provider = db.getProvider(id);
            products = db.getProductsByProvider(provider.getProviderId());
            adapter.updateList(products);
            name.setText(provider.getProviderName());
            des_email.setText(provider.getProviderEmail());
            price_phone.setText(provider.getProviderPhone());

            setTitle("Provider");
            provider_details.setVisibility(GONE);
            d.setHint("Email");
            p.setHint("Phone");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //
}
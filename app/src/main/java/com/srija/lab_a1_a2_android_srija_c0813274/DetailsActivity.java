package com.srija.lab_a1_a2_android_srija_c0813274;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProductsRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.SimpleItemTouchHelperCallback;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.SpinnerAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Product;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ItemTouchHelper.SimpleCallback callback;
    ItemTouchHelper touchHelper;
    private LatLng latLng;
    private int id = -1;
    private int type = 0;
    private boolean screenType = false;
    private AppCompatEditText name, des_email, price_phone;
    private TextInputLayout n, d, p;
    private FrameLayout provider_details;
    private TextView prov_n, prov_e, prov_p;
    private RecyclerView recyclerView;
    private SQLiteHandler db;
    private ImageView img_call, img_email;
    private List<Provider> providers;
    private List<Product> products;
    private Product product;
    private Provider provider;
    private ProductsRecyclerViewAdapter adapter;
    private AppCompatButton save_update, map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        db = new SQLiteHandler(this);
        recyclerView = findViewById(R.id.recyclerView_product);
        products = new ArrayList<>();
        providers = new ArrayList<>();
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
        img_call = findViewById(R.id.imageView_call);
        img_email = findViewById(R.id.imageView_email);
        spinner = findViewById(R.id.appCompatSpinner_provider);
        save_update = findViewById(R.id.appCompatButton_create_update);
        map = findViewById(R.id.appCompatButton_view_map);
        map.setOnClickListener(this);
//        callback = new SimpleItemTouchHelperCallback(adapter);
//        touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);

        img_call.setOnClickListener(this);
        img_email.setOnClickListener(this);
        save_update.setOnClickListener(this);
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), providers);
        spinner.setAdapter(spinnerAdapter);

        Bundle extras = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            id = extras.getInt("id", -1);
            type = extras.getInt("type", 0);
            screenType = extras.getBoolean("screenType", false);
            setScreenType(screenType);//1
            setupViews(type);//2
        }
    }

    private void setupViews(int type) {
        boolean b = !(type == 0);
        name.setEnabled(b);
        des_email.setEnabled(b);
        price_phone.setEnabled(b);
        if (type != 0 && !screenType) {
            recyclerView.setVisibility(GONE);
            des_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            price_phone.setInputType(InputType.TYPE_CLASS_PHONE);
            price_phone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
            img_email.setVisibility(GONE);
            img_call.setVisibility(GONE);

            String text;
            if (type == 1) text = "Create Provider";
            else text = "Update Provider";
            save_update.setText(text);
        }

        if (id != -1 && type == 0) {
            save_update.setVisibility(GONE);
        }

        if (type != 0 && screenType) {// edit/create product
            providers = db.getProviders();
            spinnerAdapter.updateData(providers);
            String text;
            if (type == 2) {
                for (int i = 0; i < providers.size(); i++) {
                    if (providers.get(i).getProviderId() == provider.getProviderId())
                        spinner.setSelection(i);
                }
                text = "Update Product";
            } else {
                text = "Create Product";
            }
            save_update.setText(text);
            provider_details.setVisibility(GONE);
            price_phone.setInputType(InputType.TYPE_CLASS_NUMBER);

        } else {
            spinner.setVisibility(GONE);
        }

    }

    private void setScreenType(boolean screenType) {
        if (screenType) {// Product
            if (id != -1) {
                product = db.getProduct(id);
                name.setText(product.getProductName());
                des_email.setText(product.getProductDescription());
                price_phone.setText(product.getProductPrice() + "");

                provider = db.getProvider(product.getProviderId());
                prov_n.setText(provider.getProviderName());
                prov_e.setText(provider.getProviderEmail());
                prov_p.setText(provider.getProviderPhone());
            }
            setTitle("Product");
            recyclerView.setVisibility(GONE);
            map.setVisibility(GONE);
            img_call.setVisibility(GONE);
            img_email.setVisibility(GONE);
            d.setHint("Description");
            p.setHint("Price $");
        } else {// Provider
            if (id != -1) {
                provider = db.getProvider(id);
                products = db.getProductsByProvider(provider.getProviderId());
                adapter.updateList(products);
                name.setText(provider.getProviderName());
                des_email.setText(provider.getProviderEmail());
                price_phone.setText(provider.getProviderPhone());
            }

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_email:
                Uri uri = Uri.parse("mailto:" + provider.getProviderEmail().toLowerCase().toString())
                        .buildUpon()
                        .appendQueryParameter("subject", provider.getProviderName())
                        .appendQueryParameter("body", provider.getProviderName() + " " + provider.getProviderPhone())
                        .build();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(emailIntent, "Email to " + provider.getProviderEmail()));
                break;
            case R.id.imageView_call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + price_phone.getText()));
                startActivity(intent);
                break;
            case R.id.appCompatButton_create_update:
                saveUpdate();
                break;
            case R.id.appCompatButton_view_map:
                MapsFragment mapFragment = new MapsFragment();
                if (id != -1 && !screenType) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    mapFragment.setArguments(bundle);
                }
                mapFragment.show(getSupportFragmentManager(), "map");

                break;
            default:
                break;
        }
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    private void saveUpdate() {
        String name_n = name.getText().toString().trim();
        String des_e = des_email.getText().toString().trim();
        String price_p = price_phone.getText().toString().trim();
        int index = spinner.getSelectedItemPosition();
        Provider selectedItem = null;
        if (screenType) selectedItem = providers.get(index);

        String error = "Your Input is invalid. Please Enter valid values.";
        if (name_n.equals("")) {
            name.setError(error);
            return;
        } else if (des_e.equals("")) {
            des_email.setError(error);
            return;
        } else if (price_p.equals("")) {
            price_phone.setError(error);
            return;
        }
        if (screenType && selectedItem == null) {
            Toast.makeText(this, "Please select a provider.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!screenType && latLng == null) {
            Toast.makeText(this, "Select a location on the Map", Toast.LENGTH_LONG).show();
//            Map
            return;
        }
        if (screenType) {// Product
            int provider_id = selectedItem.getProviderId();
            if (type == 1) {//Create
                List<Product> list = new ArrayList<>();
                list.add(new Product(name_n, des_e, Integer.parseInt(price_p), provider_id));
                db.addProducts(list);
                Toast.makeText(this, name_n + " created Successfully!", Toast.LENGTH_LONG).show();
            } else if (type == 2) {//Update
                product.setProductName(name_n);
                product.setProductDescription(des_e);
                product.setProductPrice(Integer.parseInt(price_p));
                product.setProviderId(provider_id);
                db.updateProduct(product);
                Toast.makeText(this, name_n + " created Successfully!", Toast.LENGTH_LONG).show();
            }
        } else {// Provider
            if (type == 1) {//Create
                List<Provider> list = new ArrayList<>();
                list.add(new Provider(name_n, des_e, price_p, latLng.latitude, latLng.longitude));
                db.addProviders(list);
                Toast.makeText(this, name_n + " created Successfully!", Toast.LENGTH_LONG).show();
            } else if (type == 2) {//Update
                provider.setProviderName(name_n);
                provider.setProviderEmail(des_e);
                provider.setProviderPhone(price_p);
                provider.setProviderLat(latLng.latitude);
                provider.setProviderLng(latLng.longitude);
                db.updateProvider(provider);
                Toast.makeText(this, name_n + " created Successfully!", Toast.LENGTH_LONG).show();
            }
        }
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//
}
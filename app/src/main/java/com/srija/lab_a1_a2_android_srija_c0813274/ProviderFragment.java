package com.srija.lab_a1_a2_android_srija_c0813274;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProductsRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProvidersRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.SimpleItemTouchHelperCallback;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SQLiteHandler db;
    ItemTouchHelper.SimpleCallback callback;
    ItemTouchHelper touchHelper;
    GoogleMap map;
    FrameLayout mapFrame;
    private RecyclerView recyclerView;
    private List<Provider> providers;
    private ProvidersRecyclerViewAdapter adapter;
    private AppCompatEditText searchText;
    private FloatingActionButton fab;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProviderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProviderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProviderFragment newInstance(String param1, String param2) {
        ProviderFragment fragment = new ProviderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        db = new SQLiteHandler(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider, container, false);
        SupportMapFragment m = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view_provider));
        m.getMapAsync(this);
        recyclerView = view.findViewById(R.id.recyclerView_product);
        providers = new ArrayList<>();
        adapter = new ProvidersRecyclerViewAdapter(getActivity(), providers);
        searchText = view.findViewById(R.id.appCompatEditText_prod_search);
        fab = view.findViewById(R.id.floatingActionButton_provider);
        fab.setOnClickListener(this);
        callback = new SimpleItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        mapFrame = view.findViewById(R.id.fragment_map_view_provider);
        SwitchCompat toggle = view.findViewById(R.id.switchCompat_toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setVisibility(View.GONE);
                    mapFrame.setVisibility(View.VISIBLE);

                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    mapFrame.setVisibility(View.GONE);
                }
            }
        });

        providers = db.getProviders();
        adapter.updateList(providers);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        providers = db.getProviders();
        adapter.updateList(providers);
        if (map != null) {
            checkDataChangeOnMaps();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton_provider:
                Intent intent = new Intent(this.getActivity(), DetailsActivity.class);
                intent.putExtra("screenType", false);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        if (marker.getTag() != null) {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("id", (Integer) marker.getTag());
            intent.putExtra("screenType", false);
            intent.putExtra("type", 0);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowClickListener(this);
        checkDataChangeOnMaps();
    }

    private void checkDataChangeOnMaps() {
        if (providers == null || providers.size() < 1) {
            providers = db.getProviders();
        }
        map.clear();
        for (Provider provider : providers) {
            LatLng latLng = new LatLng(provider.getProviderLat(), provider.getProviderLng());
            addMarker(provider.getProviderId(), latLng, provider.getProviderName(), provider.getProviderEmail());
        }
    }

    private void addMarker(int id, LatLng latLng, String title, String email) {
        Marker m1 = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(email)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .anchor(0f, 0.5f)
                .draggable(false));
        m1.setTag(id);
        m1.showInfoWindow();
    }
}
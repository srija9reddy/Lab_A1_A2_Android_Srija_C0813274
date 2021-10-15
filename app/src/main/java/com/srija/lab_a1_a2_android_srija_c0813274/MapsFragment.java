package com.srija.lab_a1_a2_android_srija_c0813274;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;


public class MapsFragment extends AppCompatDialogFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnInfoWindowClickListener {


    GoogleMap map;
    SQLiteHandler db;
    int id;
    AppCompatButton select;
    private SupportMapFragment fragment;
    private LatLng mlatlng;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        // Inflate the layout for this fragment
        SupportMapFragment m = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map_view));
        m.getMapAsync(this);
        select = view.findViewById(R.id.map_select_button);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mlatlng != null) {
                    if (getActivity() instanceof DetailsActivity) {
                        ((DetailsActivity) getActivity()).setLatLng(mlatlng);
                        dismiss();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setOnMapClickListener(this);
        db = new SQLiteHandler(getContext());
        if (getArguments() != null) {
            id = getArguments().getInt("id", -1);
            if (id != -1) {
                Provider provider = db.getProvider(id);
                addMarker(provider.getProviderId(), new LatLng(provider.getProviderLat(), provider.getProviderLng()), provider.getProviderName(), provider.getProviderEmail());
            }
        }
    }

    private void addMarker(int id, LatLng latLng, String title, String email) {
        map.clear();
        Marker m1 = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(email)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .anchor(0f, 0.5f)
                .draggable(false));
        m1.setTag(id);
        m1.showInfoWindow();
        mlatlng = m1.getPosition();
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        map.clear();
        addMarker(-1, latLng, "Tap here to Select", null);
    }
}
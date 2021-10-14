package com.srija.lab_a1_a2_android_srija_c0813274;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProductsRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProvidersRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProviderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProviderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SQLiteHandler db;
    private RecyclerView recyclerView;
    private List<Provider> providers;
    private ProvidersRecyclerViewAdapter adapter;
    private AppCompatEditText searchText;
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
        recyclerView = view.findViewById(R.id.recyclerView_product);
        providers = new ArrayList<>();
        adapter = new ProvidersRecyclerViewAdapter(getActivity(), providers);
        searchText = view.findViewById(R.id.appCompatEditText_prod_search);

        providers = db.getProviders();
        adapter.updateList(providers);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateList(db.getProviders());
    }
}
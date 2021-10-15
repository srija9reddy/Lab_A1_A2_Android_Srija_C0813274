package com.srija.lab_a1_a2_android_srija_c0813274;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.ProductsRecyclerViewAdapter;
import com.srija.lab_a1_a2_android_srija_c0813274.adapters.SimpleItemTouchHelperCallback;
import com.srija.lab_a1_a2_android_srija_c0813274.database.SQLiteHandler;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Product;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SQLiteHandler db;
    private List<Product> products;
    private ProductsRecyclerViewAdapter adapter;
    private AppCompatEditText searchText;
    private FloatingActionButton fab;
    ItemTouchHelper.SimpleCallback callback;
    ItemTouchHelper touchHelper;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_product);
        fab =  view.findViewById(R.id.floatingActionButton_product);
        fab.setOnClickListener(this);
        products = new ArrayList<>();
        adapter = new ProductsRecyclerViewAdapter(getActivity(), products);
        searchText = view.findViewById(R.id.appCompatEditText_prod_search);
        callback = new SimpleItemTouchHelperCallback(adapter);
        touchHelper = new ItemTouchHelper(callback);

        products = db.getProducts(searchText.getText().toString());
        adapter.updateList(products);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);



        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                products = db.getProducts(s.toString());
                adapter.updateList(products);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        products = db.getProducts(null);
        adapter.updateList(products);
        searchText.setText("");
        searchText.clearFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionButton_product:
                Intent intent = new Intent(this.getActivity(), DetailsActivity.class);
                intent.putExtra("screenType", true);
                intent.putExtra("type", 1);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
package com.example.darshanbeta.Fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.darshanbeta.ProductAsyncTask;
import com.example.darshanbeta.R;
import com.google.gson.annotations.JsonAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */

public class ProductFragment extends Fragment {

    private Set<String> element;
    private static final String BARCODE_API_KEY = "mpd4hagf6pkuzp9c6lz0ehorywe6ta";

    public ProductFragment(Set<String> element) {
        this.element = element;
    }

    private ExpandableListView view;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private AppCompatTextView textView;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product, container, false);
        view = v.findViewById(R.id.expandableListView);
        textView = v.findViewById(R.id.textView);
        for (String element : element) {
            ProductAsyncTask asyncTask = new ProductAsyncTask();
            asyncTask.execute("https://api.barcodelookup.com/v2/products?barcode=" +Long.parseLong(element)+"&formatted=y&key=" + BARCODE_API_KEY);
            try {
                String response = asyncTask.get();
                textView.setText(response);
                getAllProductDetail(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        prepareListData();
        view.setAdapter(new ExpandableListAdapter(requireContext(),listDataHeader,listDataChild));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

    private void getAllProductDetail(String response) {
        ArrayList<String> product = new ArrayList<String>();
        product.add("product_name");
        product.add("category");
        product.add("brand");
        product.add("description");
        product.add("barcode_formats");
        product.add("model");
        product.add("title");
        product.add("manufacturer");
        product.add("label");
        product.add("author");
        product.add("publisher");
        product.add("color");
        product.add("size");
        product.add("length");
        product.add("width");
        product.add("height");
        product.add("release_date");

        ArrayList<String> store = new ArrayList<String>();
        store.add("store_name");
        store.add("store_price");
        store.add("currency_code");
        store.add("currency_symbol");
        try {
            JSONObject object = new JSONObject(response);
            JSONArray array = object.getJSONArray("products");
            Log.i("response", array.toString());
            JSONObject object1 = array.getJSONObject(0);
            for (int i = 0; i < product.size(); i++) {
                String demo = object1.getString(product.get(i));
            }
            JSONArray features = object1.getJSONArray("features");
            JSONArray imagesArray = object1.getJSONArray("images");
            JSONArray storesArray = object1.getJSONArray("stores");
            JSONArray reviewsArray = object1.getJSONArray("reviews");
            for (int i = 0; i < 2; i++) {
                JSONObject storesObject = storesArray.getJSONObject(i);
                for (int j = 0; j < store.size(); j++) {
                    String demo = storesObject.getString(store.get(j));
                    Log.i("demo" + j, demo);
                }
            }
            textView.setText(storesArray.toString());
            Log.i("array2", features.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

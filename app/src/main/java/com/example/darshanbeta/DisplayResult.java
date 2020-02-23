package com.example.darshanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.darshanbeta.Fragment.BillFragment;
import com.example.darshanbeta.Fragment.CustomerFragment;
import com.example.darshanbeta.Fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class DisplayResult extends AppCompatActivity {

    private int currentItemIndex;
    private TextView textView;
    private BottomNavigationView bottomNavigationView;
    private static final String BARCODE_API_KEY = "mpd4hagf6pkuzp9c6lz0ehorywe6ta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        bottomNavigationView = findViewById(R.id.nav_view);
        textView = findViewById(R.id.toolBarTextView);
        textView.setText("Customers");

        Intent i = getIntent();
        ScannerResult result = (ScannerResult) i.getSerializableExtra("array");
        final Set<String> a = result.getResultCount();
        for (String element : a) {
            ProductAsyncTask asyncTask = new ProductAsyncTask();
            asyncTask.execute("https://api.barcodelookup.com/v2/products?barcode=" + Long.parseLong(element) + "&formatted=y&key=" + BARCODE_API_KEY);
            try {
                String response = asyncTask.get();
                getAllProductDetail(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        CustomerFragment customerFragment = new CustomerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, customerFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        bottomNavigationView.setSelectedItemId(R.id.customerInfo);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.customerInfo:
                        textView.setText("Customers");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new CustomerFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                        break;

                    case R.id.productInfo:
                        textView.setText("Products");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProductFragment(a)).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                        break;

                    case R.id.billReceipt:
                        textView.setText("Billing");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new BillFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                        break;

                    case R.id.scanner:
                        Intent intent = new Intent(DisplayResult.this, Scanner.class);
                        startActivity(intent);
                }
                return true;
            }

        });

        /*scannerResult = findViewById(R.id.textViewScannerResult);
        Intent i =getIntent();
        String barCodeEncodedResult = i.getStringExtra("scannerResult");
        scannerResult.setText(i.getStringExtra("scannerResult"));
        ProductAsyncTask asyncTask =new ProductAsyncTask();
        asyncTask.execute("https://api.barcodelookup.com/v2/products?barcode="+ barCodeEncodedResult +"&formatted=y&key=fcoaxpqcg10cz01lngtoxn3bxsljb8");*/
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

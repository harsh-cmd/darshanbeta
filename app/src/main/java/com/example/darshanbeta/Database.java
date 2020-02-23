package com.example.darshanbeta;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Set;


public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "liberystore";
    private static final String TABLE_NAME = "product";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_CATEGORY = "category";
    private static final String PRODUCT_BRAND = "brand";
    private static final String PRODUCT_DESCRIPTION = "description";
    private static final String PRODUCT_BARCODE_FORMATE = "barcode_formate";
    private static final String PRODUCT_MODEL = "model";
    private static final String PRODUCT_TITLE = "title";
    private static final String PRODUCT_MANUFACTURE = "manufacture";
    private static final String PRODUCT_LABEL = "label";
    private static final String PRODUCT_AUTHOR = "author";
    private static final String PRODUCT_PUBLISHER = "publisher";
    private static final String PRODUCT_COLOR = "color";
    private static final String PRODUCT_SIZE = "size";
    private static final String PRODUCT_LENGTH = "length";
    private static final String PRODUCT_HEIGHT = "height";
    private static final String PRODUCT_WIDTH = "width";
    private static final String PRODUCT_RELEASE_DATE = "release_date";

    public Database(@Nullable Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductQuery = "create table " + TABLE_NAME + "(" +
                PRODUCT_ID + " " + " integer primary key autoincrement," +
                PRODUCT_NAME + " varchar(255)," +
                PRODUCT_CATEGORY + " varchar(255)," +
                PRODUCT_BRAND + " varchar(255)," +
                PRODUCT_BARCODE_FORMATE + " varchar(255)," +
                PRODUCT_DESCRIPTION + " varchar(255)," +
                PRODUCT_MODEL + " varchar(255)," +
                PRODUCT_TITLE + " varchar(255)," +
                PRODUCT_MANUFACTURE + " varchar(255)," +
                PRODUCT_LABEL + " varchar(255)," +
                PRODUCT_AUTHOR + " varchar(255)," +
                PRODUCT_PUBLISHER + " varchar(255)," +
                PRODUCT_COLOR + " varchar(255)," +
                PRODUCT_SIZE + " varchar(255)," +
                PRODUCT_LENGTH + " varchar(255)," +
                PRODUCT_WIDTH + " varchar(255)," +
                PRODUCT_HEIGHT + " varchar(255)," +
                PRODUCT_RELEASE_DATE + " varchar(255));";
        db.execSQL(createProductQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Long insertProductDetail(ArrayList<String> barcodeData) {

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
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0;i<product.size();i++)
        {
            values.put(product.get(i),barcodeData.get(i));
        }

        Long result = db.insert(TABLE_NAME,null,values);
    return result;
    }


}

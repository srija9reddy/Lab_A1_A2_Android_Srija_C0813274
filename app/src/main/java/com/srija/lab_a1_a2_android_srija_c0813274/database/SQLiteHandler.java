package com.srija.lab_a1_a2_android_srija_c0813274.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.srija.lab_a1_a2_android_srija_c0813274.models.Product;
import com.srija.lab_a1_a2_android_srija_c0813274.models.Provider;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "srija_database";
    private static final String TABLE_PRODUCT = "products";
    private static final String Product_Name = "product_name";
    private static final String Product_Desc = "product_description";
    private static final String Product_Price = "product_price";
    private static final String Product_Provider_Id = "provider_id";

    private static final String TABLE_PROVIDER = "providers";
    private static final String Provider_Name = "provider_name";
    private static final String Provider_Email = "provider_email";
    private static final String Provider_Phone = "provider_phone";
    private static final String Provider_Lat = "provider_lat";
    private static final String Provider_Lng = "provider_lng";

    private static final String KEY_ID = "id";


    public SQLiteHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, DATABASE_VERSION);
    }

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Tables
        String Create_Products_Table = "CREATE TABLE  IF NOT EXISTS " + TABLE_PRODUCT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Product_Name + " TEXT," + Product_Desc + " TEXT, " + Product_Price + " INTEGER, " + Product_Provider_Id + " INTEGER, " + " FOREIGN KEY(" + Product_Provider_Id + ") REFERENCES " + TABLE_PROVIDER + "(" + KEY_ID + ") " + " ON DELETE CASCADE)";
        String Create_Providers_Table = "CREATE TABLE  IF NOT EXISTS " + TABLE_PROVIDER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Provider_Name + " TEXT," + Provider_Email + " TEXT, " + Provider_Phone + " TEXT, " + Provider_Lat + " DOUBLE, " + Provider_Lng + " DOUBLE" + ")";
        db.execSQL(Create_Products_Table);
        db.execSQL(Create_Providers_Table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addProducts(List<Product> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Product product : list) {
                insertProduct(product, values, db);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addProviders(List<Provider> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Provider provider : list) {
                insertProvider(provider, values, db);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertProduct(Product product, ContentValues values, SQLiteDatabase db) {
        values.put(Product_Name, product.getProductName());
        values.put(Product_Desc, product.getProductDescription());
        values.put(Product_Price, product.getProductPrice());
        values.put(Product_Provider_Id, product.getProviderId());
        db.insert(TABLE_PRODUCT, null, values);
    }

    private void insertProvider(Provider provider, ContentValues values, SQLiteDatabase db) {
        values.put(Provider_Name, provider.getProviderName());
        values.put(Provider_Email, provider.getProviderEmail());
        values.put(Provider_Phone, provider.getProviderPhone());
        values.put(Provider_Lat, provider.getProviderLat());
        values.put(Provider_Lng, provider.getProviderLng());
        db.insert(TABLE_PROVIDER, null, values);
    }

    public Product getProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCT, new String[]{KEY_ID, Product_Name, Product_Desc, Product_Price, Product_Provider_Id}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Product product = new Product();//Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getLong(2));
        product.setProductId(cursor.getInt(0));
        product.setProductName(cursor.getString(1));
        product.setProductDescription(cursor.getString(2));
        product.setProductPrice(cursor.getInt(3));
        product.setProviderId(cursor.getInt(4));

        return product;
    }

    public Provider getProvider(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROVIDER, new String[]{KEY_ID, Provider_Name, Provider_Email, Provider_Phone, Provider_Lat, Provider_Lng}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Provider provider = new Provider();
        if (cursor.moveToFirst()) {
            do {
                provider.setProviderId(cursor.getInt(0));
                provider.setProviderName(cursor.getString(1));
                provider.setProviderEmail(cursor.getString(2));
                provider.setProviderPhone(cursor.getString(3));
                provider.setProviderLat(cursor.getDouble(4));
                provider.setProviderLng(cursor.getDouble(5));
            } while (cursor.moveToNext());
        }

        return provider;
    }

    public List<Product> getProducts(@Nullable String searchText) {
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT;
        if (searchText != null && !searchText.equals("")) {
            selectQuery += " WHERE " + Product_Name + " LIKE '%" + searchText + "%' OR " + Product_Desc + " LIKE '%" + searchText + "%'";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return processProduct(cursor);
    }

    public List<Product> getProductsByProvider(int id) {
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + Product_Provider_Id + "='" + id + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return processProduct(cursor);
    }

    private List<Product> processProduct(Cursor cursor) {
        List<Product> productList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setProductId(cursor.getInt(0));
                product.setProductName(cursor.getString(1));
                product.setProductDescription(cursor.getString(2));
                product.setProductPrice(cursor.getInt(3));
                product.setProviderId(cursor.getInt(4));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        return productList;
    }

    public List<Provider> getProviders() {
        List<Provider> providers = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_PROVIDER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Provider provider = new Provider();
                provider.setProviderId(cursor.getInt(0));
                provider.setProviderName(cursor.getString(1));
                provider.setProviderEmail(cursor.getString(2));
                provider.setProviderPhone(cursor.getString(3));
                provider.setProviderLat(cursor.getDouble(4));
                provider.setProviderLng(cursor.getDouble(5));
                provider.setCount(getCount(db, cursor.getInt(0)));
                providers.add(provider);
            } while (cursor.moveToNext());
        }
        return providers;
    }

    private int getCount(SQLiteDatabase db, int id) {
        String q = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + Product_Provider_Id + "='" + id + "'";
        Cursor cursor1 = db.rawQuery(q, null);
        return cursor1.getCount();
    }

    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Product_Name, product.getProductName());
        values.put(Product_Desc, product.getProductDescription());
        values.put(Product_Price, product.getProductPrice());
        values.put(Product_Provider_Id, product.getProviderId());

        return db.update(TABLE_PRODUCT, values, KEY_ID + " = ?", new String[]{String.valueOf(product.getProductId())});
    }

    public int updateProvider(Provider provider) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Provider_Name, provider.getProviderName());
        values.put(Provider_Email, provider.getProviderEmail());
        values.put(Provider_Phone, provider.getProviderPhone());
        values.put(Provider_Lat, provider.getProviderLat());
        values.put(Provider_Lng, provider.getProviderLng());

        return db.update(TABLE_PROVIDER, values, KEY_ID + " = ?", new String[]{String.valueOf(provider.getProviderId())});
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, KEY_ID + " = ?", new String[]{String.valueOf(product.getProductId())});
        db.close();
    }

    public void deleteProvider(Provider provider) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROVIDER, KEY_ID + " = ?", new String[]{String.valueOf(provider.getProviderId())});
        db.close();
    }

    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCT, null, null);
        db.close();
    }

    public void deleteAllProviders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROVIDER, null, null);
        db.close();
    }


    //
}

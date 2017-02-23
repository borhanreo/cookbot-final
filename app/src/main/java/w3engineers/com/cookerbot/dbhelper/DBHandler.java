package w3engineers.com.cookerbot.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import w3engineers.com.cookerbot.model.RecipeModel;

/**
 * Created by borhan on 2/22/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cookbot";
    private static final String TABLE_RECIPE = "recipe";
    private static final String KEY_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_API = "recipe_api";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + RECIPE_NAME + " TEXT,"
                + RECIPE_API + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Adding new shop
    public void addRecipe(RecipeModel recipeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECIPE_NAME, recipeModel.getRecipe_name());
        values.put(RECIPE_API, recipeModel.getRecipe_api());
        db.insert(TABLE_RECIPE, null, values);
        db.close();
    }
    // Getting one recipe
    public RecipeModel getOneRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE, new String[] { KEY_ID,
                        RECIPE_NAME, RECIPE_API }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        RecipeModel contact = new RecipeModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
// return shop
        return contact;
    }

    // Getting All Shops
    public List<RecipeModel> getAllRecipe() {
        List<RecipeModel> shopList = new ArrayList<RecipeModel>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RecipeModel shop = new RecipeModel();
                shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setRecipeName(cursor.getString(1));
                shop.setRecipeApi(cursor.getString(2));
                shopList.add(shop);
            } while (cursor.moveToNext());
        }
        return shopList;
    }

    public boolean DeleteData(int id) {
        String selectQuery = "DELETE * FROM " + TABLE_RECIPE +" WHERE id="+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return true;
    }
    public void deleteSingleContact(String title){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPE,   "id=?", new String[]{title});
//KEY_NAME is a column name
    }


}

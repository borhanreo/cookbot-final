package w3engineers.com.cookerbot.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.adapter.RecipeAdapter;
import w3engineers.com.cookerbot.controller.OnItemSelectCallBackListener;
import w3engineers.com.cookerbot.dbhelper.DBHandler;
import w3engineers.com.cookerbot.model.RecipeModel;


public class RecipeList extends AppCompatActivity implements OnItemSelectCallBackListener{
    private String TAG="borhan";
    private List<RecipeModel> recipeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecipeAdapter mAdapter;
    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new RecipeAdapter(recipeList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareRecipeModelData();
    }
    private void prepareRecipeModelData() {
        List<RecipeModel> recipeModels = db.getAllRecipe();
        for (RecipeModel recipeModel : recipeModels) {
            recipeModel = new RecipeModel(recipeModel.getId(), recipeModel.getRecipe_name(), recipeModel.getRecipe_api());
            recipeList.add(recipeModel);
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void back(int id, String name, String api) {
        Log.d(TAG," borhan "+id);
        db.deleteSingleContact(id+"");
        prepareRecipeModelData();
    }
}

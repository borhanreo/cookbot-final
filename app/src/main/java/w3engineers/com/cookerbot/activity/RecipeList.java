package w3engineers.com.cookerbot.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.adapter.RecipeAdapter;
import w3engineers.com.cookerbot.controller.OnItemSelectCallBackListener;
import w3engineers.com.cookerbot.dbhelper.DBHandler;
import w3engineers.com.cookerbot.model.RecipeModel;


public class RecipeList extends AppCompatActivity implements OnItemSelectCallBackListener{
    private String TAG="borhan";
    private Context context;
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
        context=this;
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
            recipeModel = new RecipeModel(recipeModel.getId(), recipeModel.getRecipe_name(), recipeModel.getRecipe_api(),recipeModel.getRecipe_gradients_list());
            recipeList.add(recipeModel);
            //Log.d(TAG," borhan "+ recipeModel.getRecipe_gradients_list());
        }
        mAdapter.notifyDataSetChanged();
    }

    public void clearRecyclerView()
    {
        for(int i=0; i<mAdapter.getSize(); i++) {
            mAdapter.delete(i);
        }
    }
    @Override
    public void back(int id, String name, String api, String gradients_list) {
        //Log.d(TAG," borhan "+name+api+ gradients_list);
        final int rId = id;
        Intent intent = new Intent(this,ShowRecipeActivity.class);
        intent.putExtra("api",api);
        intent.putExtra("name",name);
        intent.putExtra("id",id);
        intent.putExtra("gradients_list",gradients_list);
        startActivity(intent);

        /*DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(rId<=10)
                        {
                            Toast.makeText(context,"You could not delete system recipe",Toast.LENGTH_LONG).show();
                        }else
                        {
                            db.deleteSingleContact(rId+"");
                            clearRecyclerView();
                            recipeList.clear();
                            prepareRecipeModelData();
                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this recipe?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();*/


    }
}

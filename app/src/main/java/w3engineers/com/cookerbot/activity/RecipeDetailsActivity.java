package w3engineers.com.cookerbot.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.adapter.RecipeAdapter;
import w3engineers.com.cookerbot.adapter.RecipeShowingAdapter;
import w3engineers.com.cookerbot.controller.OnItemSelectCallBackListener;
import w3engineers.com.cookerbot.model.RecipeDetailsModel;



public class RecipeDetailsActivity extends AppCompatActivity implements OnItemSelectCallBackListener{
    private String  gradients_list=null,api=null,name=null,id=null;
    private TextView rid;
    private EditText tv_gradients;

    private List<RecipeDetailsModel> recipeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecipeShowingAdapter mAdapter;
    private EditText recipe_gradients_list;
    private TextView r_id;
    private Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recipe_gradients_list = (EditText) findViewById(R.id.recipe_gradients_list);
        rid = (TextView)findViewById(R.id.r_id);
        update = (Button)findViewById(R.id.update);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            gradients_list = extras.getString("gradients_list");
            api = extras.getString("api");
            name = extras.getString("name");
            id = extras.getString("id");
            this.setTitle(name);
            recipe_gradients_list.setText(gradients_list);
            rid.setText(id);
        }





        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RecipeShowingAdapter(recipeList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareRecipeModelData();
    }

    private void prepareRecipeModelData() {
        /*List<RecipeModel> recipeModels = db.getAllRecipe();
        for (RecipeModel recipeModel : recipeModels) {
            recipeModel = new RecipeModel(recipeModel.getId(), recipeModel.getRecipe_name(), recipeModel.getRecipe_api(),recipeModel.getRecipe_gradients_list());
            recipeList.add(recipeModel);
        }
        mAdapter.notifyDataSetChanged();*/
        RecipeDetailsModel recipeDetailsModel = new RecipeDetailsModel();
        for (int i=0;i<=10;i++)
        {
            recipeDetailsModel = new RecipeDetailsModel("Oil", 12, "sec",1);
            recipeList.add(recipeDetailsModel);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void back(int id, String name, String api, String gradients_list) {

    }
}

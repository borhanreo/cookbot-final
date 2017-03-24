package w3engineers.com.cookerbot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import w3engineers.com.cookerbot.R;


public class ShowRecipeActivity extends AppCompatActivity {
    private TextView gradients;
    private String gradients_list = null, api = null, name = null, id = null;
    private Button details;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        gradients = (TextView) findViewById(R.id.gradients);
        details = (Button) findViewById(R.id.details);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            /*intent.putExtra("api",api);
            intent.putExtra("name",name);
            intent.putExtra("id",id);
            intent.putExtra("gradients_list",gradients_list);*/
            gradients_list = extras.getString("gradients_list");
            api = extras.getString("api");
            name = extras.getString("name");
            id = extras.getString("id");
            gradients.setText(gradients_list);
            this.setTitle(name);

        }
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("api", api);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("gradients_list", gradients_list);
                startActivity(intent);
            }
        });
    }

}

package w3engineers.com.cookerbot.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.controller.OnItemSelectCallBackListener;
import w3engineers.com.cookerbot.model.RecipeModel;

/**
 * Created by borhan on 2/22/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {
    private List<RecipeModel> moviesList;
    private OnItemSelectCallBackListener onItemSelectCallBackListener =null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, recipeName, recipeApi;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.title);
            recipeApi = (TextView) view.findViewById(R.id.recipeName);
            recipeName = (TextView) view.findViewById(R.id.recipeApi);
        }
    }


    public RecipeAdapter(List<RecipeModel> moviesList, OnItemSelectCallBackListener onItemSelectCallBackListener) {
        this.moviesList = moviesList;
        this.onItemSelectCallBackListener = onItemSelectCallBackListener;
    }

    /*@Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list, parent, false);

        return new MyViewHolder(itemView);
    }*/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView recipeName = (TextView) view.findViewById(R.id.recipeName);
                TextView recipeApi = (TextView) view.findViewById(R.id.recipeApi);
                onItemSelectCallBackListener.back(1,recipeName.getText().toString(),recipeApi.getText().toString());

            }
        });

        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecipeModel recipeModel = moviesList.get(position);
        //holder.id.setText(recipeModel.getId());
        holder.recipeApi.setText(recipeModel.getRecipe_name());
        holder.recipeName.setText(recipeModel.getRecipe_api());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

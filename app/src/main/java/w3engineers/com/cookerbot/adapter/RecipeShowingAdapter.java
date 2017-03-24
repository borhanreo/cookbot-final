package w3engineers.com.cookerbot.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.controller.OnItemSelectCallBackListener;
import w3engineers.com.cookerbot.model.RecipeDetailsModel;
import w3engineers.com.cookerbot.model.RecipeModel;

import static android.content.ContentValues.TAG;

/**
 * Created by Borhan Uddin on 3/24/2017.
 */

public class RecipeShowingAdapter extends RecyclerView.Adapter<RecipeShowingAdapter.MyViewHolder>  {
    private List<RecipeDetailsModel> recipeList;
    private OnItemSelectCallBackListener onItemSelectCallBackListener =null;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView action_name,action_unit, action_type;
        public EditText action_value;
        public MyViewHolder(View view) {
            super(view);
            action_name = (TextView) view.findViewById(R.id.action_name);
            action_type = (TextView) view.findViewById(R.id.action_type);
            action_unit = (TextView) view.findViewById(R.id.action_unit);
            action_value = (EditText) view.findViewById(R.id.action_value);
        }
    }
    public RecipeShowingAdapter(List<RecipeDetailsModel> moviesList, OnItemSelectCallBackListener onItemSelectCallBackListener) {
        this.recipeList = moviesList;
        this.onItemSelectCallBackListener = onItemSelectCallBackListener;
    }
    public void delete(int position){
        recipeList.remove(position);
        notifyItemRemoved(position);
    }

    public int getSize(){
        return recipeList.size();
    }
    /*@Override
    public RecipeShowingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }*/
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_recycler_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*TextView recipeId = (TextView) view.findViewById(R.id.key_id);
                TextView recipeName = (TextView) view.findViewById(R.id.recipeName);
                TextView recipeApi = (TextView) view.findViewById(R.id.recipeApi);
                TextView recipeGradientsList = (TextView) view.findViewById(R.id.recipeGradientsList);
                onItemSelectCallBackListener.back(Integer.parseInt(recipeId.getText().toString()),recipeName.getText().toString(),recipeApi.getText().toString(),recipeGradientsList.getText().toString());*/

            }
        });

        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(RecipeShowingAdapter.MyViewHolder holder, int position) {
        RecipeDetailsModel recipeDetailsModel = recipeList.get(position);
        //Log.d(TAG,"borhan"+recipeDetailsModel.getId());
        //Log.d(TAG,"borhan "+holder.keyId);
        holder.action_name.setText(recipeDetailsModel.getAction_name());
        holder.action_value.setText(recipeDetailsModel.getAction_value()+"");
        holder.action_unit.setText(recipeDetailsModel.getAction_unit());
        holder.action_type.setText(recipeDetailsModel.getAction_type()+"");
    }

    @Override
    public int getItemCount() {
      return   recipeList.size();
    }
}

package w3engineers.com.cookerbot.activity;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import w3engineers.com.cookerbot.R;
import w3engineers.com.cookerbot.dbhelper.DBHandler;
import w3engineers.com.cookerbot.model.RecipeModel;

public class NewRecipe extends AppCompatActivity {

    final Context context = this;
    private Button Oilbutton,reset,WaterButton,spud_grinding,create,onion,chili,salt,mixed_spice,ch_po_ot;
    private String hardwareApiString="", oilStr="",waterStr="",TAG="borhan",spudGrindingStr="",globStr="";
    EditText amount_oil,recipe_name;
    DBHandler db = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<RecipeModel> recipeModels = db.getAllShops();

                for (RecipeModel recipeModel : recipeModels) {
                    String log = "Id: " + recipeModel.getId() + " ,Name: " + recipeModel.getRecipe_name() + " ,Address: " + recipeModel.getRecipe_api();
                    Log.d(TAG, log);
                }
                resetRecipe();
            }
        });

        Oilbutton = (Button) findViewById(R.id.oil);
        spud_grinding = (Button) findViewById(R.id.spud_grinding);
        create = (Button)findViewById(R.id.create);
        recipe_name=(EditText)findViewById(R.id.recipe_name);

        onion=(Button)findViewById(R.id.onion);
        chili=(Button)findViewById(R.id.chili);
        salt=(Button)findViewById(R.id.salt);
        mixed_spice=(Button)findViewById(R.id.mixed_spice);
        ch_po_ot=(Button)findViewById(R.id.ch_pot_other);
        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onion.setEnabled(false);
                globStr=globStr+":s#1";

            }
        });

        chili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chili.setEnabled(false);
                globStr=globStr+":s#2";
            }
        });

        salt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salt.setEnabled(false);
                globStr=globStr+":s#3";
            }
        });
        mixed_spice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mixed_spice.setEnabled(false);
                globStr=globStr+":s#9";
            }
        });
        ch_po_ot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch_po_ot.setEnabled(false);
                globStr=globStr+":s#7";
            }
        });
        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onion.setEnabled(false);
                globStr=globStr+":s#1";
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty(recipe_name))
                {

                    String recipeNameStr = recipe_name.getText().toString();
                    globStr = recipeNameStr+":"+globStr;
                    db.addRecipe(new RecipeModel(1,recipeNameStr,globStr));
                    resetRecipe();
                }else
                {
                    Toast.makeText(context,"Please enter your recipe name",Toast.LENGTH_LONG).show();
                }

            }
        });
        Oilbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.oiltimer);
                dialog.setTitle("Oil in gm");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                 amount_oil = (EditText) dialog.findViewById(R.id.amount_oil) ;
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String strValue= amount_oil.getText().toString();
                        if (strValue==" " && strValue==null)
                        {
                            Toast.makeText(context,"Can not be empty or null or string",Toast.LENGTH_LONG).show();
                        }else
                        {
                            int getAmount = Integer.parseInt(strValue);
                            oilStr=":o+"+ getTimeInSecondOil(getAmount);
                            globStr = globStr+oilStr;
                            Oilbutton.setEnabled(false);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();
            }
        });
        WaterButton = (Button) findViewById(R.id.water);
        WaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.oiltimer);
                dialog.setTitle("Water in gm");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                amount_oil = (EditText) dialog.findViewById(R.id.amount_oil) ;
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String strValue= amount_oil.getText().toString();
                        if (strValue==" " && strValue==null)
                        {
                            Toast.makeText(context,"Can not be empty or null or string",Toast.LENGTH_LONG).show();
                        }else
                        {
                            int getAmount = Integer.parseInt(strValue);
                            //waterStr="w^"+ getTimeInSecondWater(getAmount);
                            waterStr=":o+"+ getTimeInSecondWater(getAmount);
                            globStr = globStr+waterStr;
                            WaterButton.setEnabled(false);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();
            }
        });

        spud_grinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.oiltimer);
                dialog.setTitle("Spud Grinding in second");
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                amount_oil = (EditText) dialog.findViewById(R.id.amount_oil) ;
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String strValue= amount_oil.getText().toString();
                        if (strValue==" " && strValue==null)
                        {
                            Toast.makeText(context,"Can not be empty or null or string",Toast.LENGTH_LONG).show();
                        }else
                        {
                            int getAmount = Integer.parseInt(strValue);
                            spudGrindingStr=":t*"+ getAmount;
                            globStr = globStr+spudGrindingStr;
                            //spud_grinding.setEnabled(false);
                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();
            }
        });
    }
    public int getTimeInSecondWater(int gm)
    {
        int rtn=0;
        int second = gm/5;
        return second;
    } public int getTimeInSecondOil(int gm)
    {
        int rtn=0;
        int second = gm/10;
        return second;
    }
    public void resetRecipe()
    {
        Oilbutton.setEnabled(true);
        oilStr="";
        WaterButton.setEnabled(true);
        waterStr="";
        spud_grinding.setEnabled(true);
        spudGrindingStr="";

        onion.setEnabled(true);
        chili.setEnabled(true);
        salt.setEnabled(true);
        mixed_spice.setEnabled(true);
        ch_po_ot.setEnabled(true);
        globStr = "";

    }
    public void showToast( String str)
    {
        Toast.makeText(context,"Already add this "+ str,Toast.LENGTH_LONG).show();
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}

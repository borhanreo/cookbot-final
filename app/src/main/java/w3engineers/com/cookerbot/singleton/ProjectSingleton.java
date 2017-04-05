package w3engineers.com.cookerbot.singleton;

import android.content.Context;

/**
 * Created by Borhan Uddin on 4/5/2017.
 */

public class ProjectSingleton {
    private static ProjectSingleton mySingleton= null;
    private Context context;
    private String address;


    private ProjectSingleton(Context context)
    {
        this.context = context;
        address =null;
    }
    public static ProjectSingleton getInstance(Context context)
    {
        if(mySingleton == null)
        {
            mySingleton = new ProjectSingleton(context);
        }
        return mySingleton;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String value){
        address = value;
    }

}

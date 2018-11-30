package phantomshadow.example.com.kedaikeun.ui;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Phantom Shadow on 22/11/2017.
 */

public class HelpManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public HelpManager(Context context){

        this.context = context;
        preferences = context.getSharedPreferences("first",0);
        editor = preferences.edit();
    }

    public void setFirst(Boolean setFirst){
        editor.putBoolean("check",setFirst);
        editor.commit();
    }

    public boolean check(){
        return preferences.getBoolean("check",true);
    }

}

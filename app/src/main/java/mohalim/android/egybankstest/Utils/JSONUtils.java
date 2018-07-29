package mohalim.android.egybankstest.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mohalim.android.egybankstest.Models.Choice;

public class JSONUtils {
    public static ArrayList<Choice> getChoicesFromJSON(String json){
        json = "[" + json.substring(1,json.length()-1) + "]";

        Log.v("choices :", json);

        ArrayList<Choice> choices = new ArrayList<>();

        try {
            JSONArray choicesArray = new JSONArray(json);



        } catch (JSONException e) {

        }
        return choices;
    }
}

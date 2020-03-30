package com.frednm.baking_app.features.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.frednm.baking_app.R;

public class UpdateIngredientService extends IntentService {

    static SharedPreferences sharedPref;
    static String recipeName = "Baking App";
    public static final String FOR_WIDGET = "FOR_WIDGET";
    private static final String BAKING_APP = "Baking App";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UpdateIngredientService() {
        super("UpdateIngredientService");
    }

    public static void startBakingService(Context context) {
        Intent intent = new Intent(context, UpdateIngredientService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            handleActionUpdateBakingWidgets();
        }
    }

    private void handleActionUpdateBakingWidgets() { //(ArrayList<String> ingredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        sendBroadcast(intent);
        // 1-
        sharedPref = getSharedPreferences(FOR_WIDGET, Context.MODE_PRIVATE);
        Integer recipeId = sharedPref.getInt(getString(R.string.pref_widget_label_recipeId),0);
        recipeName = sharedPref.getString(getString(R.string.pref_widget_label_recipeName),BAKING_APP);
        // 2-
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        //Now update all widgets
        RecipeWidgetProvider.updateBakingWidgets(this, appWidgetManager, appWidgetIds);
    }
}

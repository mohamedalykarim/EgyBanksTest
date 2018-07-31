package mohalim.android.egybankstest.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import mohalim.android.egybankstest.R;

public class ResumeWidgetService extends IntentService {

    public static final String ACTION_UPDATE_RESUME_WIDGETS
            = "mohalim.android.egybankstest.Widget.UPDATE_RESUME_WIDGET";


    public ResumeWidgetService() {
        super("ResumeWidgetService");
    }


    public static void startActionUpdateResumeWidgets(Context context) {
        Intent intent = new Intent(context, ResumeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RESUME_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_RESUME_WIDGETS.equals(action)){
                handleActionUpdateResumeWidgets();
            }
        }
    }

    private void handleActionUpdateResumeWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, AppResumeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        
        AppResumeWidgetProvider.updateResumeWidgets(this, appWidgetManager, appWidgetIds);

    }
}

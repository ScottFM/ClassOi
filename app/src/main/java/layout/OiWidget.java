package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.scott.classoi.R;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link OiWidgetConfigureActivity OiWidgetConfigureActivity}
 */
public class OiWidget extends AppWidgetProvider {

    private static final String RED_CLICKED = "RED CLICKED";
    private static final String GREEN_CLICKED = "GREEN CLICKED";



    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.oi_widget);

        Toast.makeText(context,"updateAppWidget",Toast.LENGTH_LONG).show();
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


     /*
        public void onUpdate (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
            Called in response to the ACTION_APPWIDGET_UPDATE and ACTION_APPWIDGET_RESTORED
            broadcasts when this AppWidget provider is being asked to provide RemoteViews for a
            set of AppWidgets. Override this method to implement your own AppWidget functionality.
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        /*
            RemoteViews
                A class that describes a view hierarchy that can be displayed in another process.
                The hierarchy is inflated from a layout resource file, and this class provides
                some basic operations for modifying the content of the inflated hierarchy.
         */
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.oi_widget);

        /*
            ComponentName
                Identifier for a specific application component (Activity, Service,
                BroadcastReceiver, or ContentProvider) that is available. Two pieces of
                information, encapsulated here, are required to identify a component: the package
                (a String) it exists in, and the class (a String) name inside of that package.
         */
        ComponentName watchWidget = new ComponentName(context, OiWidget.class);

        /*
            public static PendingIntent getBroadcast
                (Context context, int requestCode, Intent intent, int flags)

                requestCode Private request code for the sender
        */

        /*
            setOnClickPendingIntent(int viewId, PendingIntent pendingIntent)
                Equivalent to calling setOnClickListener(android.view.View.OnClickListener)
                to launch the provided PendingIntent.
            */

        // Set a Click Listener for Red TextView
        remoteViews.setOnClickPendingIntent(
                R.id.tv_red,
                getPendingSelfIntent(context, RED_CLICKED)
        );

        // Set a Click Listener for Green TextView
        remoteViews.setOnClickPendingIntent(
                R.id.tv_green,
                getPendingSelfIntent(context, GREEN_CLICKED)
        );

        /*
            AppWidgetManager
                Updates AppWidget state; gets information about installed
                AppWidget providers and other AppWidget related state.

            updateAppWidget(ComponentName provider, RemoteViews views)
                Set the RemoteViews to use for all AppWidget
                instances for the supplied AppWidget provider.
         */
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    /*
        public void onEnabled (Context context)
            Called in response to the ACTION_APPWIDGET_ENABLED broadcast when the a AppWidget
            for this provider is instantiated. Override this method to implement your
            own AppWidget functionality.
     */

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            OiWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    protected  PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);

        Toast.makeText(context, "Clicked: " + action, Toast.LENGTH_SHORT).show();
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void onReceive(Context context, Intent intent,int appWidgetId) {
        //Auto-generated method stub
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.oi_widget);

        // Display which View is clicked
        Toast.makeText(context,"onReceive: "+intent.getAction(),Toast.LENGTH_LONG).show();

        // Generate a random number
        Random rand = new Random();
        Integer randomNumber = rand.nextInt(25);

            /*
                String getAction()
                Retrieve the general action to be performed, such as ACTION_VIEW.
            */

        /*if (.equals(intent.getAction())) {
            // If the Red TextView clicked, then do that
            remoteViews.setTextViewText(R.id.tv_red, "" + randomNumber);
        }

        if (GREEN_CLICKED.equals(intent.getAction())) {
            // If the Green TextView clicked, then do that
            remoteViews.setTextViewText(R.id.tv_green, "" + randomNumber);
        }*/

        remoteViews.setTextViewText(R.id.appwidget_message,"changed" + randomNumber);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }




    public void sendMessage() {

    }
}


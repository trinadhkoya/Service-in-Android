package trinadhkoya.gihub.io.serviceintroductioninandroid;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public static final String TAG=MyIntentService.class.getCanonicalName();
    private static final String URL = "http://androidtutorialpoint.com/lucky_number.php";



    public MyIntentService() {
        super("MyIntentService");
    }

    // TODO: Customize helper method
    public static void startActionFoo(Context context, boolean param1, String param2) {
        Log.d(TAG,"is it 1");

        Intent intent = new Intent(context, MyIntentService.class);
        PendingIntent pendingIntent=PendingIntent.getService(context,0,intent,0);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);


        if(param1){
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),60,pendingIntent);
        }else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }



    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG,"is it 2");

        if(!isNetWorkAvailable()){
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    final int luckyNuumber=jsonObject.getInt("lucky_number");
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                    Notification notification=new NotificationCompat.Builder(MyIntentService.this)
                                                .setTicker("Your Alarm is")
                                                .setSmallIcon(android.R.drawable.ic_menu_report_image)

                            .setContentTitle("Number is "+luckyNuumber)
                            .setAutoCancel(true).setSound(alarmSound).build();

                    NotificationManagerCompat notificationManager =
                            NotificationManagerCompat.from(MyIntentService.this);
                    notificationManager.notify(0, notification);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
            }
        });
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest,null);



    }

    private boolean isNetWorkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean connectionAvailable=connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnected();
        return connectionAvailable;
    }


}

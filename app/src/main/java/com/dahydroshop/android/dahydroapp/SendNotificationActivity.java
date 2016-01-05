package com.dahydroshop.android.dahydroapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike on 11/23/2015.
 */
public class SendNotificationActivity extends AppCompatActivity {

    private EditText mNotificationEdit;
    private Button mNotificationButton;
    private RequestQueue mRequestQueue;
    private static final String URL = "http://euclid.nmu.edu/~mkinnune/dahydroapp/updatenotifcation.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        mRequestQueue = Volley.newRequestQueue(this);
        mNotificationEdit = (EditText)findViewById(R.id.notification_edit);
        mNotificationButton = (Button)findViewById(R.id.notification_button);
        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String notification = mNotificationEdit.getText().toString();
                StringRequest request = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Toast.makeText(getApplicationContext(), "You sent the following notification: " + s, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getApplicationContext(), "Error: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("notification", notification);

                        return params;
                    }
                };
                mRequestQueue.add(request);
                finish();
            }
        });

    }
    @Override
    protected void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

}

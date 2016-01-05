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
 * Created by Mike on 10/28/2015.
 */
public class MessageActivity extends AppCompatActivity{

    private EditText mEmailEditText;
    private EditText mMessageEditText;
    private Button mSubmitButton;
    private RequestQueue mRequestQueue;
    private static final String URL = "http://euclid.nmu.edu/~mkinnune/dahydroapp/updatemessages.php";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mEmailEditText = (EditText)findViewById(R.id.message_user_email);
        mMessageEditText = (EditText)findViewById(R.id.message_edit);
        mSubmitButton = (Button)findViewById(R.id.message_submit_button);
        mRequestQueue = Volley.newRequestQueue(this);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailEditText.getText().toString();
                final String message = mMessageEditText.getText().toString();
                if(checkValidEmail(email)) {
                    if(!message.isEmpty()) {
                        StringRequest request = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        Toast.makeText(getApplicationContext(), "You sent the following message to the owners: " + message, Toast.LENGTH_LONG).show();
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

                                params.put("email", email);
                                params.put("message", message);

                                return params;
                            }
                        };
                        mRequestQueue.add(request);
                        finish();
                    }
                    else
                        Toast.makeText(MessageActivity.this, "Empty Message.", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MessageActivity.this, "That is not a valid email", Toast.LENGTH_SHORT).show();
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

    private boolean checkValidEmail(CharSequence email){
        // http://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not/9225678#9225678
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

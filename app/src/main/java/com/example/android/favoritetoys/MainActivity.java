/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.favoritetoys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.R.attr.action;


public class MainActivity extends AppCompatActivity {

    private EditText e_name, e_rollno, e_marks;
    private Button insert, update, delete;
    RequestQueue queue;
    String url ="https://iamanonymous729.000webhostapp.com/operations1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e_name = (EditText) findViewById(R.id.name);
        e_rollno = (EditText) findViewById(R.id.rollno);
        e_marks = (EditText) findViewById(R.id.marks);
        insert = (Button) findViewById(R.id.insert);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        queue = Volley.newRequestQueue(this);

        // listeners
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_rollno.getText().toString().equals("") || e_marks.getText().toString().equals("") || e_name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "EMPTY FIELD FOUND. \n PLEASE ENTER ALL THE DETAILS!!", Toast.LENGTH_SHORT).show();
                } else {

                    send("INSERT");
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_rollno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "please enter your roll number!", Toast.LENGTH_SHORT).show();
                } else {
                    send("UPDATE");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e_rollno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "please enter your roll number", Toast.LENGTH_SHORT).show();
                } else {
                    e_name.setText("0");
                    e_marks.setText("0");
                    send("DELETE");
                }
            }
        });

    }

    public void send(final String actions){
        Toast.makeText(getApplicationContext(),e_name.getText().toString()+e_rollno.getText().toString()+e_marks.getText().toString()+actions, Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(obj.getString("status").equals("success"))
                            Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getApplicationContext(),"failed", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected HashMap<String, String> getParams() {
                // Posting parameters to login url
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("name", e_name.getText().toString());
                params.put("roll", e_rollno.getText().toString());
                params.put("marks", e_marks.getText().toString());
                params.put("operation", actions);

                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
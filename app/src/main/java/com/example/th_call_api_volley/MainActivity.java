package com.example.th_call_api_volley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String url_api = "https://60af714e5b8c300017decbb5.mockapi.io/moto";
    List<MoTo> moToList = new ArrayList<>();
    MoToAdapter adapter;
    ListView lv;
    ImageView imgAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        imgAdd = findViewById(R.id.imgAdd);

        GET_Volley();
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PostMoToActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GET_Volley();
    }

    void GET_Volley() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Dữ liệu trả về sẽ là chuỗi
                        // Việc xử lý chuyển đổi dữ liệu thành đối tượng json hoặc array
                        // thì thực hiện giống ví dụ của http get
                        moToList.clear();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                MoTo moTo = new MoTo();
                                moTo.setId(jsonObject.getString("id"));
                                moTo.setCreatedAt(jsonObject.getString("createdAt"));
                                moTo.setImage(jsonObject.getString("image"));
                                moTo.setName(jsonObject.getString("name"));
//                                moTo.setPrice(jsonObject.getInt("price"));
                                moTo.setColor(jsonObject.getString("color"));

                                moToList.add(moTo);
                                Log.d("zzzz", "onResponse: " + array.length());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new MoToAdapter(moToList, MainActivity.this);
                        lv.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
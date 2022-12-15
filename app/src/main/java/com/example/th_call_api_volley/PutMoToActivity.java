package com.example.th_call_api_volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PutMoToActivity extends AppCompatActivity {
    TextView tvName, tvPrice, tvTime, tvcolor;
    ImageView imageView;
    MoTo moto;
    Button btnUpdate, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_mo_to);

        moto = (MoTo) getIntent().getSerializableExtra("data");

        tvName = findViewById(R.id.edit_text_name);
        tvName.setText(moto.getName());
        tvPrice = findViewById(R.id.edit_text_price);
        tvPrice.setText(moto.getPrice() + "");

        tvcolor = findViewById(R.id.edit_text_color);
        tvcolor.setText(moto.getColor());
        imageView = findViewById(R.id.image_view);

        Glide.with(PutMoToActivity.this).load(moto.getImage()).placeholder(R.drawable.ic_launcher_background).into(imageView);
        btnUpdate = findViewById(R.id.btnUdate);
        btnCancel = findViewById(R.id.btnCancell);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moto.setName(tvName.getText().toString());
                moto.setPrice(Integer.parseInt(tvPrice.getText().toString()));
                moto.setColor(tvcolor.getText().toString());
                moto.setCreatedAt(tvcolor.getText().toString());
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(PutMoToActivity.this);
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("name", moto.getName());
                    jsonBody.put("image", moto.getImage());
                    jsonBody.put("color", moto.getColor());
                    jsonBody.put("price", moto.getPrice());
                    String url_api = "https://60af714e5b8c300017decbb5.mockapi.io/moto/" + moto.getId();
                    final String requestBody = jsonBody.toString();

                    //Cú pháp: StringRequest stringRequest = new StringRequest(METHOD, URL, respone) {request_option};

                    StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_api, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(PutMoToActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {// phần gửi đi
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };


                    // thực hiện kết nối server và gửi dữ liệu (volley thực thi)
                    requestQueue.add(stringRequest);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PutMoToActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
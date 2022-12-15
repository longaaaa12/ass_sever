package com.example.th_call_api_volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class PostMoToActivity extends AppCompatActivity {
    String url_api = "https://60af714e5b8c300017decbb5.mockapi.io/moto";
    EditText edtname, edtPrice, edtcolor, edtImage;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_mo_to);
        initView();
    }

    private void initView() {
        edtname = findViewById(R.id.ed_name);
        edtcolor = findViewById(R.id.ed_color);
        edtPrice = findViewById(R.id.ed_price);
        edtImage = findViewById(R.id.ed_img);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADDProduct();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PostMoToActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void ADDProduct() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", edtname.getText().toString());
            jsonBody.put("image", edtImage.getText().toString());
            jsonBody.put("price", Integer.parseInt(edtPrice.getText().toString()));
            jsonBody.put("color", edtcolor.getText().toString());

            final String requestBody = jsonBody.toString();

            //Cú pháp: StringRequest stringRequest = new StringRequest(METHOD, URL, respone) {request_option};

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_api, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(PostMoToActivity.this, "Add success", Toast.LENGTH_SHORT).show();
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
}
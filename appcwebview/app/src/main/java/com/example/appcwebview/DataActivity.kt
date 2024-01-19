package com.example.appcwebview

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class DataActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var promoAdapter: PromoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        listView = findViewById(R.id.listView)
        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        val url = "https://rnfrenp.000webhostapp.com/api.php"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    // Get the JSON array from the "data" field
                    val jsonArray = response.getJSONArray("data")
                    val promoList = parseJson(jsonArray)
                    promoAdapter = PromoAdapter(this, R.layout.list_item_promo, promoList)
                    listView.adapter = promoAdapter
                } catch (e: JSONException) {
                    Log.e("Json Parsing Error", e.toString())
                }
            },
            Response.ErrorListener { error ->
                // Handle error
                Log.e("Volley Error", error.toString())
            }
        )

        // Add the request to the RequestQueue.
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseJson(jsonArray: JSONArray): List<PromoModel> {
        val promoList = mutableListOf<PromoModel>()

        for (i in 0 until jsonArray.length()) {
            try {
                val jsonPromo: JSONObject = jsonArray.getJSONObject(i)
                val promoModel = PromoModel(
                    jsonPromo.getString("promoId"),
                    jsonPromo.getString("promo"),
                    jsonPromo.getString("promoUntil")
                )
                promoList.add(promoModel)
            } catch (e: JSONException) {
                Log.e("Json Parsing Error", e.toString())
            }
        }

        return promoList
    }
}



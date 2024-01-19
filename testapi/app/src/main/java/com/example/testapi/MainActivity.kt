package com.example.testapi
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private val blogPosts = ArrayList<BlogPost>()
    private lateinit var blogPostAdapter: BlogPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.blogPostRecyclerView)
        blogPostAdapter = BlogPostAdapter(this, blogPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = blogPostAdapter

        fetchData()
    }

    private fun fetchData() {
        val url = "http://192.168.0.106/siprodi2/public/api/blog-posts"

        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val blogPost = BlogPost(
                        jsonObject.getInt("id"),
                        jsonObject.getInt("blog_author_id"),
                        jsonObject.getInt("blog_category_id"),
                        jsonObject.getString("title"),
                        jsonObject.getString("slug"),
                        jsonObject.getString("excerpt"),
                        jsonObject.getString("banner"),
                        jsonObject.getString("content"),
                        jsonObject.getString("published_at"),
                        jsonObject.getString("created_at"),
                        jsonObject.getString("updated_at")
                    )
                    blogPosts.add(blogPost)
                }
                blogPostAdapter.notifyDataSetChanged()
            },
            { error ->
                error.printStackTrace()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }
}


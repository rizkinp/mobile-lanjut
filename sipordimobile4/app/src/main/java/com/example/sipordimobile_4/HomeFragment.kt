package com.example.sipordimobile_4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray

class HomeFragment : Fragment() {
    private val blogPosts = ArrayList<BlogPost>()
    private lateinit var blogPostAdapter: BlogPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.blogPostRecyclerView)
        blogPostAdapter = BlogPostAdapter(requireContext(), blogPosts)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = blogPostAdapter
        // Tambahkan kode ini untuk mengakses FloatingActionButton di dalam Fragment Home
        val fabHome = view.findViewById<FloatingActionButton>(R.id.fab)
        val fab2 = view.findViewById<FloatingActionButton>(R.id.fab2)
        fabHome.setOnClickListener {
            // Tambahkan tindakan yang sesuai ketika FAB diklik di dalam Fragment
            val url = "https://test-tamatika.vercel.app/"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        fab2.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            // Menampilkan pesan Toast "Log out berhasil"
            Toast.makeText(requireContext(), "Log out berhasil", Toast.LENGTH_SHORT).show()
        }

        fetchData()

        return view

    }

    private fun fetchData() {
        val url = "http://192.168.182.64/siprodi2/public/api/blog-posts"

        val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())
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

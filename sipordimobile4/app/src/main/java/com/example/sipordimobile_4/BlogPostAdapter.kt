package com.example.sipordimobile_4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.squareup.picasso.Picasso

class BlogPostAdapter(private val context: Context, private val blogPosts: List<BlogPost>) :
    RecyclerView.Adapter<BlogPostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.blog_post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blogPost = blogPosts[position]

        // Tampilkan gambar menggunakan Glide
        Glide.with(context)
            .load("http://192.168.182.64/siprodi2/storage/app/public/${blogPost.banner}")
            .error(R.drawable.ic_launcher_background) // Tambahkan placeholder image jika gambar tidak ditemukan
            .into(holder.bannerImageView)

        // Tampilkan judul
        holder.titleTextView.text = blogPost.title

        // Tampilkan tanggal unggah
        holder.publishedDateTextView.text = blogPost.published_at

        holder.itemView.setOnClickListener {
            // Handle item click
            val fragment = BlogPostDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable("blogPost", blogPost)
            fragment.arguments = bundle

            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    override fun getItemCount(): Int {
        return blogPosts.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bannerImageView: ImageView = itemView.findViewById(R.id.bannerImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val publishedDateTextView: TextView = itemView.findViewById(R.id.publishedDateTextView)
    }
}

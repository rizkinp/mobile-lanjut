package com.example.testapi
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
            .load("http://192.168.0.106/siprodi2/storage/app/public/" + blogPost.banner)
            .error(R.drawable.ic_launcher_background) // Tambahkan placeholder image jika gambar tidak ditemukan
            .into(holder.bannerImageView)




        // Tampilkan judul
        holder.titleTextView.text = blogPost.title

        // Tampilkan tanggal unggah
        holder.publishedDateTextView.text = blogPost.published_at
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

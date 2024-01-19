// BlogPostDetailFragment.kt
package com.example.sipordimobile_4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import com.bumptech.glide.Glide

class BlogPostDetailFragment : Fragment() {
    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var bannerImageView: ImageView
    private lateinit var publishedDateTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blog_post_detail, container, false)

        titleTextView = view.findViewById(R.id.titleTextView)
        contentTextView = view.findViewById(R.id.contentTextView)
        bannerImageView = view.findViewById(R.id.bannerImageView)
        publishedDateTextView = view.findViewById(R.id.publishedDateTextView)

        // Retrieve the detailed information for the selected blog post and display it.
        val blogPost = arguments?.getSerializable("blogPost") as BlogPost?
        if (blogPost != null) {
            titleTextView.text = blogPost.title
            contentTextView.text = blogPost.content

            // Load and display the banner image using Glide
            // ...

// Load and display the banner image using Glide
            if (!blogPost.banner.isNullOrBlank()) {
                Glide.with(requireContext())
                    .load("http://192.168.182.64/siprodi2/storage/app/public/${blogPost.banner}")
                    .error(R.drawable.ic_launcher_background) // Gambar default
                    .into(bannerImageView)
            } else {
                // Jika gambar tidak ada, tampilkan gambar default
                bannerImageView.setImageResource(R.drawable.ic_launcher_background)
            }

// ...

            publishedDateTextView.text = blogPost.published_at
        }

        // Mengatur listener untuk tombol "Tambahkan ke Favorit"
        val addToFavoritesButton = view.findViewById<Button>(R.id.addToFavoritesButton)
        addToFavoritesButton.setOnClickListener {
            // Implementasi aksi yang sesuai ketika tombol "Tambahkan ke Favorit" diklik di sini
            // Misalnya, tambahkan blog post ke daftar favorit Anda atau lakukan tindakan lain.

            // Subscribe ke topik dengan ID blog post atau topik yang sesuai
            val blogPostId = blogPost?.id.toString()
            FirebaseMessaging.getInstance().subscribeToTopic("favorit")

            // Menampilkan pesan "Berhasil tersimpan di favorit" menggunakan Toast
            val toast = Toast.makeText(context, "Berhasil tersimpan di favorit", Toast.LENGTH_SHORT)
            toast.show()
        }



        return view
    }

}

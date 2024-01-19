package com.example.firebasestorage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class CustomAdapter(private val context: Context, private val dataList: ArrayList<HashMap<String, Any>>) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val fileName = dataList[position]["file_name"] as String
        val fileType = dataList[position]["file_type"] as String
        val fileUrl = dataList[position]["file_url"] as String

        viewHolder.fileNameTextView.text = "Name: $fileName"
        viewHolder.fileTypeTextView.text = "Type: $fileType"
        viewHolder.fileUrlTextView.text = "URL: $fileUrl"

        // Menambahkan listener klik ke elemen ListView
        // Menambahkan listener klik ke elemen ListView
        view.setOnClickListener {
            val fileUrl = dataList[position]["file_url"] as String
            initiateDownload(context, fileUrl) // Pass the context here
        }

        if (fileType == "Image") {
            viewHolder.imageView.visibility = View.VISIBLE
            loadImageIntoImageView(fileUrl, viewHolder.imageView)
        } else {
            viewHolder.imageView.visibility = View.VISIBLE
            // Set the default drawable for non-image items (e.g., ic_file)
            viewHolder.imageView.setImageResource(R.drawable.ic_file)
        }

        return view
    }

    private class ViewHolder(view: View) {
        val fileNameTextView: TextView = view.findViewById(R.id.fileNameTextView)
        val fileTypeTextView: TextView = view.findViewById(R.id.fileTypeTextView)
        val fileUrlTextView: TextView = view.findViewById(R.id.fileUrlTextView)
        val imageView: ImageView = view.findViewById(R.id.fileIconImageView) // Add this line
    }
    }

private fun initiateDownload(context: Context, fileUrl: String) {
    // Di sini Anda dapat menginisiasi unduhan dari URL Firebase.
    // Anda dapat menggunakan perpustakaan atau metode yang sesuai untuk melakukannya.
    // Contoh sederhana menggunakan Intent untuk membuka tautan URL.
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(fileUrl)
    context.startActivity(intent)
}

    private fun loadImageIntoImageView(imageUrl: String, imageView: ImageView) {
        Picasso.get().load(imageUrl).into(imageView)
    }


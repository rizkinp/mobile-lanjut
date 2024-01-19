package com.example.testapi

data class BlogPost(
    val id: Int,
    val blog_author_id: Int,
    val blog_category_id: Int,
    val title: String,
    val slug: String,
    val excerpt: String,
    val banner: String?,
    val content: String,
    val published_at: String?,
    val created_at: String,
    val updated_at: String
)

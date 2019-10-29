package com.visualeap.aliforreddit.data.repository.comment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.PrimitiveAdapters

data class CommentResponse(val comments: List<Comment>) {
    @JsonSerializable
    data class Comment(
        @Json(name = "name") val id: String,
        @Json(name = "author") val authorName: String,
        @Json(name = "body") val text: String,
        val score: Int,
        @Json(name = "created_utc") val creationDate: Long,
        val depth: Int,
        @Json(name = "link_id") val postId: String,
        @Json(name = "parent_id") val parentId: String,
        val replies: List<Comment>?
    )
}



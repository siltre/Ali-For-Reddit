package com.visualeap.aliforreddit.data.repository.post

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.visualeap.aliforreddit.data.repository.redditor.RedditorEntity
import com.visualeap.aliforreddit.data.repository.subreddit.SubredditEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = SubredditEntity::class,
        parentColumns = ["name"],
        childColumns = ["subredditName"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class PostEntity(
    @PrimaryKey val id: String,
    val authorName: String,
    val title: String,
    val text: String,
    val score: Int,
    val commentCount: Int,
    val subredditName: String,
    val created: Long
)
package com.visualeap.aliforreddit.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.visualeap.aliforreddit.data.repository.account.AccountDao
import com.visualeap.aliforreddit.data.repository.account.AccountEntity
import com.visualeap.aliforreddit.data.repository.post.PostDao
import com.visualeap.aliforreddit.data.repository.post.PostEntity
import com.visualeap.aliforreddit.data.repository.redditor.RedditorDao
import com.visualeap.aliforreddit.data.repository.redditor.RedditorEntity
import com.visualeap.aliforreddit.data.repository.token.TokenDao
import com.visualeap.aliforreddit.data.repository.token.CurrentTokenEntity
import com.visualeap.aliforreddit.data.repository.token.TokenEntity
import com.visualeap.aliforreddit.data.repository.token.UserTokenEntity
import com.visualeap.aliforreddit.data.repository.token.UserlessTokenEntity
import com.visualeap.aliforreddit.data.repository.comment.CommentDao
import com.visualeap.aliforreddit.data.repository.comment.CommentEntity
import com.visualeap.aliforreddit.data.repository.feed.FeedDao
import com.visualeap.aliforreddit.data.repository.feed.FeedEntity
import com.visualeap.aliforreddit.data.repository.post.postfeed.PostFeedDao
import com.visualeap.aliforreddit.data.repository.post.postfeed.PostFeedEntity
import com.visualeap.aliforreddit.data.repository.subreddit.SubredditEntity

@Database(
    entities = [TokenEntity::class, UserTokenEntity::class, UserlessTokenEntity::class,
        CurrentTokenEntity::class, AccountEntity::class, RedditorEntity::class, PostEntity::class,
        SubredditEntity::class, CommentEntity::class, FeedEntity::class, PostFeedEntity::class],
    version = 1
)
@TypeConverters(
    CurrentTokenEntity.TokenTypeConverter::class,
    FeedEntity.FeedTypeConverter::class,
    FeedEntity.SortByTypeConverter::class
)
abstract class RedditDatabase : RoomDatabase() {
    companion object {
        const val NOT_SET_ROW_ID = 0
        const val SINGLE_RECORD_ID = 1
    }

    abstract fun tokenDao(): TokenDao
    abstract fun accountDao(): AccountDao
    abstract fun redditorDao(): RedditorDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun feedDao(): FeedDao
    abstract fun postFeedDao(): PostFeedDao
}
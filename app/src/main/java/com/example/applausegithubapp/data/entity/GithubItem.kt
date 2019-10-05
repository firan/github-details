package com.example.applausegithubapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "githubItem", indices = [Index(value = ["identifier"], unique = true)])
data class GithubItem(
    @ColumnInfo(name = "identifier") var identifier: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "private") var private: Boolean = false,
    @ColumnInfo(name = "description") var description: String? = "",
    @ColumnInfo(name = "url") var url: String? = "",
    @ColumnInfo(name = "created_at") var createdAt: Date? = Date(),
    @ColumnInfo(name = "updated_at") var updatedAt: Date? = Date(),
    @ColumnInfo(name = "language") var language: String? = "",
    @ColumnInfo(name = "has_issues") var hasIssues: Boolean? = false
) {
    @PrimaryKey(autoGenerate = true)
    var rid: Int? = null
}

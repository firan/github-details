package com.example.applausegithubapp.data.to;

import androidx.room.ColumnInfo;

data class NameTuple(
    @ColumnInfo(name = "rid") var rid: Int,
    @ColumnInfo(name = "name") var name: String
)
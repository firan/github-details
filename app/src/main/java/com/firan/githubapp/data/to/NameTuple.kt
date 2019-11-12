package com.firan.githubapp.data.to;

import androidx.room.ColumnInfo;

/**
 * author: Artur Godlewski
 * dto for display data in StartFragment in RecyclerView
 */
data class NameTuple(
    @ColumnInfo(name = "rid") var rid: Int,
    @ColumnInfo(name = "name") var name: String
)
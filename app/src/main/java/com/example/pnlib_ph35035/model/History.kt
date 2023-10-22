package com.example.pnlib_ph35035.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("idHistory")
    var idHistory: Int?,

    @ColumnInfo("searchName")
    var searchName: String?
)

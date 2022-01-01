package com.example.xminutesworkout.Dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    @ColumnInfo(name = "exercise-date")
    val date:String="",
    @ColumnInfo(name = "exercise-sets")
    val sets:String="",
    @ColumnInfo(name = "exercise-duration")
    val durationExer:String="",
    @ColumnInfo(name = "exercise-restduration")
    val duartionRest:String=""
)

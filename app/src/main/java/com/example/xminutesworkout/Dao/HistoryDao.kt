package com.example.xminutesworkout.Dao

import androidx.room.*

import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)
    @Delete
    suspend fun delete(entity:HistoryEntity)
    @Query("SELECT * FROM `history-table`")
    fun fetchAllHistory(): Flow<List<HistoryEntity>>
    @Query("SELECT * FROM `history-table` where id=:id")
    @JvmSuppressWildcards
    fun fetcheAllEmployeesById(id:Int):Flow<HistoryEntity>
}
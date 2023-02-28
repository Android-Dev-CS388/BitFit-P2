package com.codepath.articlesearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Insert
    fun insertAll(foods: FoodEntity)

    @Insert
    fun insertOne(food: FoodEntity)

    @Query("DELETE FROM food_table")
    fun deleteAll()
}
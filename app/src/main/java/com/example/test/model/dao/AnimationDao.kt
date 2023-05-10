package com.example.test.model.dao

import androidx.room.Dao


import androidx.room.Insert
import androidx.room.OnConflictStrategy

import androidx.room.Query

import androidx.room.Update
import com.example.test.model.Animation

@Dao public interface AnimationDao {
    @Query("SELECT * FROM animation")
    fun getAll(): MutableList<Animation>

    @Query("SELECT * FROM animation where selected = 1")
    fun getAnimation():  Animation?

    @Query("SELECT * FROM animation WHERE path = :uri")
    fun findByPath(uri: String): Animation?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(   users: Animation)

    @Query("UPDATE animation SET selected =0 WHERE selected =1")
    fun updateCheckedObjects()

    @Update
    fun update(animation: Animation)



}
package com.example.test.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "animation")
  data class Animation(
  @PrimaryKey(autoGenerate = true) val id: Int,
  @ColumnInfo(name = "path") var path: String,
  @ColumnInfo(name = "repeat") var repeat:Boolean,
  @ColumnInfo(name = "dateTime") var dateTime: Boolean,
  @ColumnInfo(name = "lever") var lever: Boolean,
  @ColumnInfo(name = "type") var type: String,
  @ColumnInfo(name = "selected") var selected:Boolean,
 ): Serializable

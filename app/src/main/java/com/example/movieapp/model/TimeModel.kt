package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time")
data class TimeModel(@PrimaryKey val time: Int)
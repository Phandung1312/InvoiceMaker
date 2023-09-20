package com.bravo.domain.model

import java.io.Serializable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nameClient") val nameClient: String?,
    @ColumnInfo(name = "nameProject") val nameProject: String?,
    @ColumnInfo(name = "dateCreate") val dateProject: String?,
    @ColumnInfo(name = "locationList") val locationList: List<String>
) : Serializable
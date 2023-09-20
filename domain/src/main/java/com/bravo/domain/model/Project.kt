package com.bravo.domain.model

import java.io.Serializable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class Project(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "nameClient") val nameClient: String?,
    @ColumnInfo(name = "nameProject") val nameProject: String?,
    @ColumnInfo(name = "dateCreate") val dateProject: String?,
    @ColumnInfo(name = "dateStart") val dateStart: String?,
    @ColumnInfo(name = "dateEnd") val dateEnd: String?,
    @ColumnInfo(name = "locationList") val locationList: List<String>,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "status") val status: String,

) : Serializable
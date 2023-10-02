package com.bravo.domain.model

import java.io.Serializable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "nameClient") val nameClient: String?,
    @ColumnInfo(name = "nameProject") val nameProject: String?,
    @ColumnInfo(name = "dateCreate") val dateProject: String?,
    @ColumnInfo(name = "dateStart") val dateStart: String?,
    @ColumnInfo(name = "dateEnd") val dateEnd: String?,
    @ColumnInfo(name = "locationList") val locationList: List<String>,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "notePrivate") val notePrivate: String?,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "fileList") val fileList: List<String>,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean = false
) : Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = ["id"],
        childColumns = ["projectId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class ContactInfoProject(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "projectId") val projectId: Long?,
    @ColumnInfo(name = "givenName") val givenName: String?,
    @ColumnInfo(name = "surName") val surName: String?,
    @ColumnInfo(name = "mobileContact") val mobileContact: String?,
    @ColumnInfo(name = "emailContact") val emailContact: String?,
    @ColumnInfo(name = "roleContact") val role: String?
) : Serializable


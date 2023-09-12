package com.bravo.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "billingName") val billingName: String?,
    @ColumnInfo(name = "mobile") val mobileNumber: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "nameContact") val nameContact: String?,
    @ColumnInfo(name = "phoneContact") val phoneContact: String?,
    @ColumnInfo(name = "website") val website: String?,
    @ColumnInfo(name = "taxNumber") val taxNum: String?,
    @ColumnInfo(name = "paymentTerms") val payment: String?,
    @ColumnInfo(name = "billingAddress") val address: String?,
    @ColumnInfo(name = "noteClient") val note: String?
)
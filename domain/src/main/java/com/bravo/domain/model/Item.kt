package com.bravo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    var name : String = "",
    var description : String = "",
    var  rate : Long = 0,
    var  cost : Long = 0,
    var unitType : Int = 0,
    var applyTaxes : Boolean = true,
)

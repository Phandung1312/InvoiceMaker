package com.bravo.invoice.common


import com.bravo.invoice.models.BusinessInfo
import com.bravo.invoice.models.InvoiceDesign
import com.bravo.invoice.models.converters.InvoiceDesignConverter
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(
    private val rxSharedPreferences: RxSharedPreferences
) {
    val isFirstSetUp = rxSharedPreferences.getBoolean("isFirstSetUp", true)
    val isFirstDesign = rxSharedPreferences.getBoolean("isFirstDesign", true)
    val businessInfo = rxSharedPreferences.getObject("businessInfo", BusinessInfo(), object : Preference.Converter<BusinessInfo>{
        override fun deserialize(serialized: String): BusinessInfo {
            val adapter = Moshi.Builder().build().adapter(BusinessInfo::class.java)
            return adapter.fromJson(serialized) ?: BusinessInfo()
        }

        override fun serialize(value: BusinessInfo): String {
            val adapter = Moshi.Builder().build().adapter(BusinessInfo::class.java)
            return adapter.toJson(value)
        }
    })
    val invoiceDesignedTemp = rxSharedPreferences.getObject("invoiceDesignedTemp", InvoiceDesign(), InvoiceDesignConverter())
    val invoiceDesigned = rxSharedPreferences.getObject("invoiceDesigned", InvoiceDesign(), InvoiceDesignConverter())
    val enableNotify = rxSharedPreferences.getBoolean("enableNotify", false)
    val enableMail = rxSharedPreferences.getBoolean("enableMail", false)

    val notifyBeforeDueDay = rxSharedPreferences.getBoolean("notifyBeforeDueDay", true)
    val notifyOnDueDay = rxSharedPreferences.getBoolean("notifyOnDueDay", true)
    val notifyAfterDueDate3 = rxSharedPreferences.getBoolean("notifyAfterDueDate3", true)
    val notifyAfterDueDate7 = rxSharedPreferences.getBoolean("notifyAfterDueDate7", true)

    val taxType = rxSharedPreferences.getInteger("taxType", Constants.EXCLUSIVE)
    val taxRate = rxSharedPreferences.getFloat("taxRate", 10f)

    val isAutoSaveItem = rxSharedPreferences.getBoolean("isAutoSaveItem", true)
}
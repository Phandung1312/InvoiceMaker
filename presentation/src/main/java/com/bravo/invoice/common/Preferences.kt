package com.bravo.invoice.common

import com.bravo.invoice.models.BusinessInfo
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

    val enableNotify = rxSharedPreferences.getBoolean("enableNotify", false)
    val enableMail = rxSharedPreferences.getBoolean("enableMail", false)
}
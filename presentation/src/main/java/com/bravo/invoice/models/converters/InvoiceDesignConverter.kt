package com.bravo.invoice.models.converters

import com.bravo.domain.model.converters.BitmapConverter
import com.bravo.invoice.models.InvoiceDesign
import com.f2prateek.rx.preferences2.Preference
import com.squareup.moshi.Moshi

class InvoiceDesignConverter : Preference.Converter<InvoiceDesign>{
    override fun deserialize(serialized: String): InvoiceDesign {
        val adapter = Moshi.Builder()
            .add(BitmapConverter()).build()
            .adapter(InvoiceDesign::class.java)
        return adapter.fromJson(serialized) ?: InvoiceDesign()
    }

    override fun serialize(value: InvoiceDesign): String {
        val adapter = Moshi.Builder()
            .add(BitmapConverter()).build()
            .adapter(InvoiceDesign::class.java)
        return adapter.toJson(value)
    }

}
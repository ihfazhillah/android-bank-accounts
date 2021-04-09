package com.ihfazh.bankaccounts.core.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bank(
        val id: String,
        val name: String,
        val code: String?,
        val image: String?,
        var favorite: Boolean = false
) : Parcelable {
//    override fun toString(): String {
//        return this.name
//    }
}

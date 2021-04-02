package com.ihfazh.bankaccounts.domain.data

data class Bank(
    val id: String,
    val name: String,
    val code: String?,
    val image: String?
){
    override fun toString(): String {
        return this.name
    }
}

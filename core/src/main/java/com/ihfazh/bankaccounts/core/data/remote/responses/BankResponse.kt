package com.ihfazh.bankaccounts.data.remote.responses

data class BankResponse(
	val banks: List<BanksItem>,
)

data class BanksItem(
	val image: String,
	val code: String,
	val name: String
)


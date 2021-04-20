package com.ihfazh.bankaccounts.core.data.remote.responses

import com.google.gson.annotations.SerializedName

data class BankResponse(

	@field:SerializedName("banks")
	val banks: List<BanksItem>
)

data class BanksItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("name")
	val name: String
)

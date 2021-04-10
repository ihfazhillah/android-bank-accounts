package com.ihfazh.bankaccounts.core.domain.utils

class BankFormatter {
    companion object {
        fun formatAccountNumber(number: String): String {
            val chars = number.toCharArray()
            if (chars.size <= 4) {
                return number
            }
            val stringBuilder = StringBuilder()
            for ((index, char) in chars.withIndex()) {
                when {
                    index % 4 == 0 -> {
                        stringBuilder.append(char)
                        stringBuilder.append(" ")
                    }
                    else -> {
                        stringBuilder.append(char)
                    }
                }

            }
            return stringBuilder.toString()
        }
    }
}
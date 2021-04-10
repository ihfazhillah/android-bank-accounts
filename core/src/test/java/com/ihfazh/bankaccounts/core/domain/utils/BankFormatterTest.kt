package com.ihfazh.bankaccounts.core.domain.utils

import org.junit.Assert.assertEquals
import org.junit.Test


class BankFormatterTest {
    @Test
    fun testFormatter() {
        val expected = "0 1234 5678 90"
        val result = BankFormatter.formatAccountNumber("01234567890")
        assertEquals(expected, result)
    }

    @Test
    fun testFormatterLessThanFourOrFour() {
        val expected = "123"
        val result = BankFormatter.formatAccountNumber("123")
        assertEquals(expected, result)

        val nextexpected = "1234"
        val nextresult = BankFormatter.formatAccountNumber("1234")
        assertEquals(nextexpected, nextresult)
    }
}
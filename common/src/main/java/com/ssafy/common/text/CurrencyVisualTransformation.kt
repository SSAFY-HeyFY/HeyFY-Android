package com.ssafy.common.text

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CurrencyVisualTransformation(private val currency: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text

        if (originalText.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val formattedText = when (currency) {
            "KRW" -> formatKRW(originalText)
            else -> formatUSD(originalText)
        }

        return TransformedText(
            AnnotatedString(formattedText),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return formattedText.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return originalText.length
                }
            }
        )
    }

    private fun formatKRW(text: String): String {
        val parts = text.split(".")
        val integerPart = parts[0]
        val decimalPart = if (parts.size > 1) parts[1] else ""

        val formattedInteger = integerPart.reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return if (decimalPart.isNotEmpty()) {
            "₩ $formattedInteger.$decimalPart"
        } else {
            "₩ $formattedInteger"
        }
    }

    private fun formatUSD(text: String): String {
        if (text == ".") {
            return "$ 0."
        }

        if (text.startsWith(".")) {
            return "$ 0$text"
        }
        
        val parts = text.split(".")
        val integerPart = parts[0]
        val decimalPart = if (parts.size > 1) parts[1] else ""

        val formattedInteger = if (integerPart.isEmpty()) "0" else {
            integerPart.reversed()
                .chunked(3)
                .joinToString(",")
                .reversed()
        }

        return if (decimalPart.isNotEmpty()) {
            "$ $formattedInteger.$decimalPart"
        } else if (text.endsWith(".")) {
            "$ $formattedInteger."
        } else {
            "$ $formattedInteger"
        }
    }
}

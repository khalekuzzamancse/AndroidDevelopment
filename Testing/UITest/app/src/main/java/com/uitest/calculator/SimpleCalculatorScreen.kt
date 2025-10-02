package com.uitest.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleCalculatorScreen(
    modifier: Modifier = Modifier,
    onCalculate: (String) -> Unit = {}
) {
    var input by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf<String?>(null) }

    val keys: List<List<String>> = listOf(
        listOf("7","8","9","÷"),
        listOf("4","5","6","×"),
        listOf("1","2","3","-"),
        listOf("C","0","=","+")
    )

    fun onKeyPressed(value: String) {
        when (value) {
            "C" -> {
                input = ""
                result = null
            }
            "=" -> {
                try {
                    result = calculateExpression(input)
                    onCalculate(result ?: "")
                } catch (e: Exception) {
                    result = "Error"
                }
            }
            else -> {
                // Append number or operator if valid
                // Avoid two operators consecutively
                if (value in listOf("+", "-", "×", "÷")) {
                    if (input.isNotEmpty() && input.last() !in listOf('+', '-', '×', '÷')) {
                        input += value
                    }
                } else {
                    input += value
                }
                result = null
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = if (result != null) result!! else input.ifEmpty { "0" },
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = if (result == "Error") Color.Red else Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            textAlign = TextAlign.End
        )
        Spacer(modifier = Modifier.height(30.dp))

        keys.forEach { row ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { key ->
                    Key(
                        label = key,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        onClick = { onKeyPressed(key) }
                    )
                }
            }
        }
    }
}

@Composable
fun Key(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when (label) {
        "C" -> Color.Red.copy(alpha = 0.8f)
        "=" -> Color(0xFF4CAF50)
        "+", "-", "×", "÷" -> Color(0xFF2196F3)
        else -> Color.White
    }
    val contentColor = if (backgroundColor == Color.White) Color.Black else Color.White

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        shadowElevation = 4.dp,
        modifier = modifier
            .clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = label,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = contentColor
            )
        }
    }
}

// Simple expression evaluation supporting +, -, ×, ÷ with left-to-right evaluation (no operator precedence)
fun calculateExpression(expression: String): String {
    if (expression.isEmpty()) return "0"
    val tokens = mutableListOf<String>()
    var currentNumber = ""
    expression.forEach { c ->
        if (c in listOf('+', '-', '×', '÷')) {
            if (currentNumber.isNotEmpty()) {
                tokens.add(currentNumber)
                currentNumber = ""
            }
            tokens.add(c.toString())
        } else {
            currentNumber += c
        }
    }
    if (currentNumber.isNotEmpty()) tokens.add(currentNumber)

    // Evaluate left to right
    var acc = tokens[0].toDoubleOrNull() ?: throw IllegalArgumentException("Invalid expression")
    var i = 1
    while (i < tokens.size) {
        val op = tokens[i]
        val next = tokens.getOrNull(i+1)?.toDoubleOrNull() ?: throw IllegalArgumentException("Invalid expression")
        acc = when (op) {
            "+" -> acc + next
            "-" -> acc - next
            "×" -> acc * next
            "÷" -> {
                if (next == 0.0) throw ArithmeticException("Division by zero")
                acc / next
            }
            else -> throw IllegalArgumentException("Unknown operator $op")
        }
        i += 2
    }
    return String.format("%.2f", acc)
}

@Preview
@Composable
private fun CalculatorPreview() {
    SimpleCalculatorScreen()
}
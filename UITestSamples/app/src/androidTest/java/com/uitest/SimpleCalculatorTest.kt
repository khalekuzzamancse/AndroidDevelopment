import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.uitest.calculator.SimpleCalculatorScreen
import org.junit.Rule
import org.junit.Test

class SimpleCalculatorTest {

    @get:Rule
    val rule = createComposeRule()

    private fun pressKey(key: String){
        rule.onNode(hasText(key) and hasClickAction()).performClick()
        Thread.sleep(500)//to show the keypress
    }

    @Test
    fun input_and_clear() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("1")
        pressKey("2")
        pressKey("3")
        pressKey("+")
        rule.onNodeWithText("123+").assertExists()

        pressKey("C") // Clear
        rule.onNodeWithText("0").assertExists()
    }

    @Test
    fun simple_addition() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("1")
        pressKey("0")
        pressKey("+")
        pressKey("2")
        pressKey("=")
        rule.onNodeWithText("12.00").assertExists()
    }

    @Test
    fun simple_subtraction() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("5")
        pressKey("0")
        pressKey("-")
        pressKey("1")
        pressKey("5")
        pressKey("=")
        rule.onNodeWithText("35.00").assertExists()
    }

    @Test
    fun simple_multiplication() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("6")
        pressKey("×")
        pressKey("7")
        pressKey("=")
        rule.onNodeWithText("42.00").assertExists()
    }

    @Test
    fun simple_division() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("8")
        pressKey("4")
        pressKey("÷")
        pressKey("2")
        pressKey("=")
        rule.onNodeWithText("42.00").assertExists()
    }

    @Test
    fun division_by_zero() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("7")
        pressKey("÷")
        pressKey("0")
        pressKey("=")
        rule.onNodeWithText("Error").assertExists()
    }

    @Test
    fun invalid_operator_sequence_prevention() {
        rule.setContent { SimpleCalculatorScreen() }
        pressKey("+")
        rule.onNodeWithText("0").assertExists()  // Operator at start ignored

        pressKey("1")
        pressKey("+")
        pressKey("+")
        rule.onNodeWithText("1+").assertExists() // Double operators not allowed
    }
}

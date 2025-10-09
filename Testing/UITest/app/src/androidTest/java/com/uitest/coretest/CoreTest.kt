@file:Suppress("Unused")
package com.uitest.coretest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule


fun AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.backPress(){
    this.activity.onBackPressedDispatcher.onBackPressed()
}
fun ComposeContentTestRule.waitFoScreen(screen: String){
    this.waitUntil(timeoutMillis = 5_000) {
        try {
            this.onNode(hasContentDescription(screen)).assertExists()
            true // Node exists → stop waiting
        } catch (e: AssertionError) {
            false // Node not found yet → keep waiting
        }
    }
}
fun ComposeContentTestRule.waitForLabelOnTextView(label: String,substring:Boolean=false){
    this.waitUntil(timeoutMillis = 5_000) {
        try {
            this.onNode(hasText(label, substring = substring)).assertExists()
            true // Node exists → stop waiting
        } catch (e: AssertionError) {
            false // Node not found yet → keep waiting
        }
    }
}
/**
 * @param navItem is content description of the nav item or drawer item
 * @param screen semantic for the screen, expect the semantic belongs to the screen modifier or screen
 */
fun ComposeContentTestRule.navigateTo(navItem: String, screen: String, sleep:Long=1000 ) {
    this.onNode(hasContentDescription(navItem) and hasClickAction()).performClick()
    this.waitFoScreen(screen)
    Sleeper.sleep(sleep)
}

/**
 * - If the has the label or text and it is clickable then it click and wait for the idle
 * - After that sleep for 500ms so that action become visible to the user
 */
 fun ComposeContentTestRule.clickIfHasLabel(label: String){
    onNode(hasText(label) and hasClickAction()).performClick()
     waitForIdle()
    Thread.sleep(500)//to show the keypress
}

object  Sleeper{
    fun sleep(ms:Long){
        Thread.sleep(ms)
    }
    fun  sleepInfinity(){
        while (true){
            Thread.sleep(1000)
        }
    }

    fun sleep1sec(){
        Thread.sleep(1_000)
    }
    fun sleep2sec(){
        Thread.sleep(2_000)
    }
    fun sleep3sec() {
        Thread.sleep(3_000)
    }
    fun sleep4sec() {
        Thread.sleep(4_000)
    }
    fun sleep5sec() {
        Thread.sleep(5_000)
    }
}
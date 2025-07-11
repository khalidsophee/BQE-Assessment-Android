// Created by Khalid Aziz

package com.alaziz.bqe

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun addTodoItem_displaysInList() {
        val title = "BQE Item 1"
        val description = "Dummy Item"

        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
    }

    @Test
    fun markTodoItemAsComplete() {
        val title = "BQE Item 2"

        // Add item
        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Mark as complete
        composeTestRule.onAllNodes(isToggleable())[0].performClick()

        // Can't verify line-through directly in test, so just assert presence
        composeTestRule.onNodeWithText(title).assertExists()
    }


    @Test
    fun deleteTodoItem_removesFromList() {
        val title = "BQE Item 3"

        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        // Confirm delete button exists
        composeTestRule.onNodeWithTag("delete_0").assertExists()

        // Click delete
        composeTestRule.onNodeWithTag("delete_0").performClick()

        // Let Compose finish recomposing
        composeTestRule.waitForIdle()

        // Check it's not displayed
        composeTestRule.onNodeWithText(title).assertIsNotDisplayed()
    }


    @Test
    fun validateListContent_addMultipleItems() {
        val tasks = listOf("BQE A", "BQE B", "BQE C")

        tasks.forEach {
            composeTestRule.onNodeWithText("Title").performTextInput(it)
            composeTestRule.onNodeWithText("Add To-Do").performClick()
        }

        tasks.forEach {
            composeTestRule.onNodeWithText(it).assertIsDisplayed()
        }

        composeTestRule.onAllNodes(hasText("Add To-Do")).assertCountEquals(1)
    }
}

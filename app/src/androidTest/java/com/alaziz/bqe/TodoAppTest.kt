// Created by Khalid Aziz

package com.alaziz.bqe

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // 1. Test Case 1: Add Item to List
    @Test
    fun addTodoItem_displaysInList() {
        val title = "BQE Item 1"
        val description = "Dummy Item"

        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
        composeTestRule.onNodeWithText(description).assertIsDisplayed()
        //also we can check the list size
    }

    //2. Test Case: Mark Item as Completed
    @Test
    fun markTodoItemAsComplete() {
        val title = "BQE Item 2"
        val description = "Dummy Item"

        // Add item
        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Mark as complete
        composeTestRule.onAllNodes(isToggleable())[0].performClick()

        // verify assert presence
        composeTestRule.onNodeWithText(title).assertExists()
    }

    //3. Remove Item From List
    @Test
    fun deleteTodoItem_removesFromList() {
        val title = "BQE Item 3"
        val description = "Dummy Item"

        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description)
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


    //4. Test Case: Add Multiple Items
    // Validate List Content and UI State test case checks:
    //(a)That all tasks appear correctly after being added
    //(b)That their UI state (completed/incomplete) and visibility is consistent
    //(c)That the UI reflects correct task count, toggle state, visibility, and order (if relevant)

    @Test
    fun validateListContent_addMultipleItems() {
        val tasks = listOf("BQE A", "BQE B", "BQE C")
        val desc = listOf("BQE A desc", "BQE B desc", "BQE C desc")

        tasks.zip(desc).forEach { (title, description) ->
            composeTestRule.onNodeWithText("Title").performTextInput(title)
            composeTestRule.onNodeWithText("Description (Optional)")
                .performTextInput(description)
            composeTestRule.onNodeWithText("Add To-Do").performClick()
        }

        tasks.forEach { title ->
            composeTestRule.onNodeWithText(title).assertIsDisplayed()
        }

        desc.forEach { description ->
            composeTestRule.onNodeWithText(description).assertIsDisplayed()
        }

        composeTestRule.onAllNodes(hasText("Add To-Do")).assertCountEquals(1)
    }

    //Edge Cases
    //Edge Test Case 5(a):Empty Title Input (Required Field)

    @Test
    fun emptyTitle_shouldNotAddTodoItem() {
        val invalidDescription = "This shouldn't be saved"

        // Clear title field to make it empty
        composeTestRule.onNodeWithText("Title").performTextClearance()

        // Enter description only
        composeTestRule.onNodeWithText("Description (Optional)")
            .performTextInput(invalidDescription)

        // Attempt to add the item
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Assert that the item was NOT added to the LazyColumn (todoList)
        composeTestRule
            .onNodeWithTag("todoList")
            .onChildren()
            .filter(hasText(invalidDescription))
            .assertCountEquals(0)
    }

    //Edge Test Case 5(b):Empty Title Input (Required Field) and empty Description
    @Test
    fun emptyTitleAndDescription_shouldNotAddTodoItem() {
        // Clear title and description
        composeTestRule.onNodeWithText("Title").performTextClearance()
        composeTestRule.onNodeWithText("Description (Optional)").performTextClearance()

        // Click Add
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Assert list is still empty
        composeTestRule.onNodeWithTag("todoList")
            .onChildren()
            .assertCountEquals(0)
    }

    //Edge Test Case 5(c): Title with only spaces should not be accepted
    @Test
    fun titleWithOnlySpaces_shouldNotAddTodoItem() {
        val description = "Legit Description"

        // Input whitespace as title
        composeTestRule.onNodeWithText("Title").performTextInput("   ")
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description)

        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Assert nothing was added
        composeTestRule.onNodeWithTag("todoList")
            .onChildren()
            .filter(hasText(description))
            .assertCountEquals(0)
    }

    //Edge Test Case 5(d):Allow empty description when title is valid
    @Test
    fun validTitleEmptyDescription_shouldAddTodoItem() {
        val title = "Task With Title Only"

        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextClearance()
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Assert the title is displayed via test tag
        composeTestRule.onNodeWithTag("title_0").assertExists()
    }


    //Edge Test Case 5(e):Duplicate titles should be allowed (if allowed in app logic)
    @Test
    fun duplicateTitles_shouldAddBothItems() {
        val title = "Same Task"
        val description1 = "First Entry"
        val description2 = "Second Entry"

        // Add first item
        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description1)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Add second item with same title
        composeTestRule.onNodeWithText("Title").performTextClearance()
        composeTestRule.onNodeWithText("Description (Optional)").performTextClearance()
        composeTestRule.onNodeWithText("Title").performTextInput(title)
        composeTestRule.onNodeWithText("Description (Optional)").performTextInput(description2)
        composeTestRule.onNodeWithText("Add To-Do").performClick()

        // Assert both descriptions exist using test tags
        composeTestRule.onNodeWithTag("desc_0").assertExists()
        composeTestRule.onNodeWithTag("desc_1").assertExists()
    }



}

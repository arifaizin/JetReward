package com.dicoding.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dicoding.jetreward.R
import com.dicoding.jetreward.model.OrderReward
import com.dicoding.jetreward.model.Reward
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    val fakeOrderReward = OrderReward(Reward(4, "", "Jacket Hoodie Dicoding", 7500), 0)
    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetRewardTheme {
                DetailContent(
                    R.drawable.reward_4,
                    "Jacket Hoodie Dicoding",
                    7500,
                    0,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText("Jacket Hoodie Dicoding").assertIsDisplayed()
        composeTestRule.onNodeWithText("7500 Pts").assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithText("＋").performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("1"))
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun decreaseProduct_stillZero() {
        composeTestRule.onNodeWithText("—").performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}
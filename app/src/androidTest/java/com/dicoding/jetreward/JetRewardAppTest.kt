package com.dicoding.jetreward

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetreward.ui.navigation.Screen
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetRewardTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navController = navController)
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun navHost_verifyStartDestination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(currentRoute, Screen.Home.route)
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithText("Keranjang").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.Cart.route)
        composeTestRule.onNodeWithText("Profile").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.Profile.route)
        composeTestRule.onNodeWithText("Home").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.DetailReward.route)
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").assertIsDisplayed()
    }

    @Test
    fun navHost_cartDecreased_removed() {
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.DetailReward.route)
        composeTestRule.onNodeWithText("＋").performClick()
        composeTestRule.onNodeWithText("Tambah ke Keranjang : 1000 Pts").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.Cart.route)
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").assertIsDisplayed()
        composeTestRule.onNodeWithText("—").performClick()
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").assertDoesNotExist()
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText("Jaket Hoodie Dicoding").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.DetailReward.route)
        composeTestRule.onNodeWithText("＋").performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("1"))
        composeTestRule.onNodeWithText("Tambah ke Keranjang : 1000 Pts").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.Cart.route)
        composeTestRule.onNodeWithText("Home").performClick()
        assertEquals(navController.currentBackStackEntry?.destination?.route, Screen.Home.route)
    }
}
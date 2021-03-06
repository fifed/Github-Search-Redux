package com.fifed.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fifed.ui.repository.RepositoryScreen
import com.fifed.ui.repository.RepositoryScreenStarter
import com.fifed.ui.search.SearchScreen
import com.fifed.ui.search.SearchScreenStarter

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SearchScreenStarter.graphRoute) {
        composable(SearchScreenStarter.graphRoute) { SearchScreen(navController) }
        composable(RepositoryScreenStarter.graphRoute, arguments = RepositoryScreenStarter.arguments) {
            RepositoryScreen(navController = navController, arguments = requireNotNull(it.arguments))
        }
    }
}

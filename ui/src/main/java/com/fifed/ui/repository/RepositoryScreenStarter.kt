package com.fifed.ui.repository

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

data class RepositoryScreenStarter(
    private val id: Int,
    private val ownerName: String,
    private val repositoryName: String) {

    fun getRoute(): String {
        return "${this.javaClass.name}?$OWNER=$ownerName&$REPOSITORY=$repositoryName&$ID=$id"
    }

    companion object {
        private const val OWNER = "owner"
        private const val REPOSITORY = "repository"
        private const val ID = "id"
        val graphRoute = "${RepositoryScreenStarter::class.java.name}?$OWNER={$OWNER}&$REPOSITORY={$REPOSITORY}&$ID={$ID}"
        fun getOwnerName(arguments: Bundle) = requireNotNull(arguments.getString(OWNER))
        fun getRepositoryName(arguments: Bundle) = requireNotNull(arguments.getString(REPOSITORY))
        fun getRepositoryId(arguments: Bundle) = requireNotNull(arguments.getInt(ID))
        val arguments: List<NamedNavArgument> = listOf(
            navArgument(OWNER) {
                type = NavType.StringType
            }, navArgument(REPOSITORY) {
                type = NavType.StringType
            }, navArgument(ID) {
                type = NavType.IntType
            }
        )
    }
}

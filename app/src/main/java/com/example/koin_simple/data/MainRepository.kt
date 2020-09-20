package com.example.koin_simple.data

import com.example.koin_simple.data.models.Commit
import com.example.koin_simple.network.GithubService

class MainRepository(private val githubService: GithubService) {

    suspend fun listCommits(username: String, repoName: String): List<Commit> {
        return githubService.listCommits(username, repoName)
    }

}
package com.example.koin_simple.ui.main

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import com.example.koin_simple.data.MainRepository
import com.example.koin_simple.data.models.Commit
import com.example.koin_simple.network.LogService
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository,
    private val logService: LogService
) : BaseViewModel() {

    @Bindable
    var username: String = "sierraobryan"
        set(value) {
            field = value
            notifyPropertyChanged(BR.username)
        }

    @Bindable
    var repoName: String = "hackerNews"
        set(value) {
            field = value
            notifyPropertyChanged(BR.repoName)
        }

    @Bindable
    var loading: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @Bindable
    var commits: List<Commit> = listOf()
        set(value) {
            field = value
            notifyPropertyChanged(BR.commits)
        }

    @Bindable
    var isError: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isError)
        }

    @Bindable("username", "repoName")
    fun isFetchCommitsEnabled() = validateRepo() && validateUserName()

    fun listCommits() {
        logService.logNetworkAttempt()
        viewModelScope.launch {
            loading = true
            if (mainRepository.allowNetworkCall.value == true) {
                val result = mainRepository.listCommits(
                        username.trim(),
                        repoName.trim()
                )
                logService.logSuccess()
                loading = false
                commits = result
            } else {
                logService.logError()
                loading = false
                isError = true
            }
        }
    }

    private fun validateUserName() = username.isNotBlank()

    private fun validateRepo() = repoName.isNotBlank()

}
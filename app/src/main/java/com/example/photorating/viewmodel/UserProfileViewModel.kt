package com.example.photorating.viewmodel

import androidx.lifecycle.*
import com.example.photorating.data.UserEntity
import com.example.photorating.data.UserDao
import kotlinx.coroutines.launch

class UserProfileViewModel(private val userDao: UserDao) : ViewModel() {
    private val _userProfile = MutableLiveData<UserEntity>()
    val userProfile: LiveData<UserEntity> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadUserProfile(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = userDao.getUser(username)
                _userProfile.value = user
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateFollowStatus(username: String, isFollowing: Boolean) {
        viewModelScope.launch {
            userDao.updateFollowStatus(username, isFollowing)
            loadUserProfile(username)
        }
    }
}
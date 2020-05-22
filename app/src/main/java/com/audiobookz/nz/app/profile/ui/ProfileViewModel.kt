package com.audiobookz.nz.app.profile.ui

import androidx.lifecycle.ViewModel
import com.audiobookz.nz.app.profile.data.ProfileRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(repository: ProfileRepository) : ViewModel() {
    var token: String? = null
    val queryProfile by lazy { token?.let { repository.queryProfile(it) } }

}
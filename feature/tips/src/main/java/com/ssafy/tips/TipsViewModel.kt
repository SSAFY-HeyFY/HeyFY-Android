package com.ssafy.tips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipsViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator
) : ViewModel() {

    fun back() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }
}

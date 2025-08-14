package com.ssafy.heyfy

import androidx.lifecycle.ViewModel
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeyFYViewModel @Inject constructor(
    heyFYAppNavigator: HeyFYAppNavigator,
): ViewModel() {
    val navigationChannel = heyFYAppNavigator.navigationChannel
}

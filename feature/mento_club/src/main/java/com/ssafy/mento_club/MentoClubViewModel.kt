package com.ssafy.mento_club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MentoClubViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator
): ViewModel() {

    fun navigateBack() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateBack()
        }
    }

    fun goToSuccess() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Success(),
            )
        }
    }
}

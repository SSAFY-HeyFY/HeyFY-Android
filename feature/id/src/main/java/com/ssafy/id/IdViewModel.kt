package com.ssafy.id

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.navigation.Destination
import com.ssafy.navigation.HeyFYAppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdViewModel @Inject constructor(
    private val heyFYAppNavigator: HeyFYAppNavigator,
) : ViewModel() {

    fun goToSuccess() {
        viewModelScope.launch {
            heyFYAppNavigator.navigateTo(
                route = Destination.Success(),
                isBackStackCleared = true,
            )
        }
    }
}

package com.ssafy.mento_club

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.common.ui.DetailTopBar
import com.ssafy.common.ui.HeyFYPopUp
import com.ssafy.navigation.DestinationParamConstants.MENTO

data class Mentor(
    val id: String,
    val name: String,
    val title: String,
    val description: String,
    val imageUrl: String,
)

@Composable
fun MentoClubScreen(
    viewModel: MentoClubViewModel = hiltViewModel<MentoClubViewModel>(),
) {

    var isShowPopUp by remember { mutableStateOf(false) }

    val mentors = listOf(
        Mentor(
            id = "1",
            name = "Sarah Johnson",
            title = "Senior Product Manager at Google",
            description = "with 8+ years of experience in tech. Specialized in product strategy, user experience, and team leadership.",
            imageUrl = "http://localhost:3845/assets/b4ab72641176b0cc2e1ef990a1352b26e77f7de1.png"
        ),
        Mentor(
            id = "2",
            name = "Michael Chen",
            title = "Software Engineering Manager at Microsoft",
            description = "Expert in full-stack development, cloud architecture, and engineering leadership.",
            imageUrl = "http://localhost:3845/assets/1cf761465086d7cb3a73b008fbed9cfb1012fe69.png"
        ),
        Mentor(
            id = "3",
            name = "Emily Rodriguez",
            title = "Marketing Director at Spotify",
            description = "Specializes in digital marketing, brand strategy, and growth hacking for tech startups.",
            imageUrl = "http://localhost:3845/assets/eed0fd1a80730f9ecae8e4bd22c4209fc9fc3100.png"
        ),
        Mentor(
            id = "4",
            name = "David Kim",
            title = "UX Design Lead at Airbnb",
            description = "10+ years of experience in user research, design systems, and creating intuitive digital experiences.",
            imageUrl = "http://localhost:3845/assets/c77b5c00685bb3b77655ab2ec29bcdd937de69a8.png"
        )
    )

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            val title = if (viewModel.type == MENTO) "1:1 Mentoring" else "Club Recommendation"
            DetailTopBar(
                title = title,
                onBack = viewModel::back
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mentors) { mentor ->
                MentorCard(
                    mentor = mentor,
                    onClick = {
                        isShowPopUp = true
                    }
                )
            }
        }
    }

    if (isShowPopUp) {
        HeyFYPopUp(
            onCancel = {
                isShowPopUp = false
            },
            onConfirm = {
                isShowPopUp = false
                // TODO: 신청 완료 처리
                viewModel.goToSuccess()
            },
            onDismiss = {
                isShowPopUp = false
            }
        )
    }
}

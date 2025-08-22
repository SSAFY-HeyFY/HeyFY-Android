package com.ssafy.mento_club

import androidx.annotation.DrawableRes
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
import com.ssafy.common.R as commonR

data class Mentor(
    val name: String,
    val description: String,
    @DrawableRes val imageId: Int,
)

@Composable
fun MentoClubScreen(
    viewModel: MentoClubViewModel = hiltViewModel<MentoClubViewModel>(),
) {

    var isShowPopUp by remember { mutableStateOf(false) }

    val mentors = listOf(
        Mentor(
            name = "Jae Hong Park",
            description = "Hi! I’m passionate about exploring local restaurants and discovering hidden food spots. I love trying new dishes and sharing my experiences with friends. Excited to exchange food stories with you!",
            imageId = commonR.drawable.image_jaehong
        ),
        Mentor(
            name = "Dae Eol Park",
            description = "Join our traditional Korean music club, where you can learn Daegeum (Korean bamboo flute) and Gayageum. I’ve been studying Daegeum for three years and can now perform a variety of folk songs, including Arirang.",
            imageId = commonR.drawable.image_daeul
        ),
        Mentor(
            name = "U Sang Um",
            description = "Specializes in digital marketing, brand strategy, and growth hacking for tech startups.",
            imageId = commonR.drawable.image_usang_2
        ),
        Mentor(
            name = "Hae Rin Kim",
            description = "Hi! I love traveling and discovering new places. I’d be happy to share Korea’s unique culture with you. If you’d like to join me on fun and meaningful trips, just reach out!",
            imageId = commonR.drawable.image_haerin
        ),

        Mentor(
            name = "Seung Sang Kim",
            description = "I have a great passion for coding and spend a lot of my time at the Coding Lounge. If you're also interested in programming, feel free to come join me there. Let's work on projects together!",
            imageId = commonR.drawable.image_seungsang
        ),

        Mentor(
            name = "David Kim",
            description = "10+ years of experience in user research, design systems, and creating intuitive digital experiences.",
            imageId = commonR.drawable.image_usang_1
        )
    )

    val clubs = listOf(
        Mentor(
            name = "Equal Opportunities Club",
            description = "Equal Opportunities Club is a welcoming space where diversity is celebrated, and everyone is valued. We stand for no discrimination, equal chances, and higher happiness for all members together.",
            imageId = commonR.drawable.image_club_1
        ),
        Mentor(
            name = "Broadcasting Club",
            description = "Broadcasting Club is where voices and creativity come alive. We create videos, host events, and share stories that inspire. Join us to learn media skills and bring exciting ideas to life together.",
            imageId = commonR.drawable.image_club_2
        ),
        Mentor(
            name = "HEYFY PEOPLE",
            description = "HEYFY PEOPLE is a spirited club where students unite through dance, music, and cheering. We celebrate passion, energy, and creativity, inspiring each other to shine on stage and beyond together.",
            imageId = commonR.drawable.image_club_3
        ),
        Mentor(
            name = "YOUTH PARTY",
            description = "YOUTH PARTY is a dynamic concert club where passion meets music. We gather to enjoy live performances, share youthful energy, and create unforgettable moments filled with rhythm, joy, and unity.",
            imageId = commonR.drawable.image_club_4
        ),

        Mentor(
            name = "HEYFY LIFE STYLE",
            description = "HEYFY LIFE STYLE is a vibrant community where people connect, share passions, and explore trends. We inspire each other to live with balance, joy, and individuality every single day.",
            imageId = commonR.drawable.image_club_5
        ),
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
            items(if (viewModel.type == MENTO) mentors else clubs) { mentor ->
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

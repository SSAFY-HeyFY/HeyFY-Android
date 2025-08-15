package com.ssafy.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.common.theme.HeyFYTheme

@Composable
internal fun UniversityCard(
    modifier: Modifier = Modifier,
    universityName: String = "Seoul National University",
    studentNumber: String = "2024123456",
    name: String = "John Smith",
    major: String = "Computer Science",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF7D80EE), Color(0xFFC78DEB)),
                    start = Offset.Zero,
                    end = Offset.Infinite,
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 20.dp)
    ) {
        Text(
            text = "University Card",
            style = HeyFYTheme.typography.headlineS,
            color = Color.White
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = universityName,
            style = HeyFYTheme.typography.bodyM,
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        Row {

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(100.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {

            }

            Spacer(Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Student ID",
                        style = HeyFYTheme.typography.bodyM,
                        color = Color.White
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = studentNumber,
                        style = HeyFYTheme.typography.headlineS,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = name,
                    style = HeyFYTheme.typography.headlineS,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = major,
                    style = HeyFYTheme.typography.headlineS,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(
    name = "UniversityCard - Default",
    showBackground = true,
    backgroundColor = 0xFFF8F9FA
)
@Composable
private fun UniversityCardPreview() {
    HeyFYTheme {
        UniversityCard(
            modifier = Modifier.padding(16.dp)
        )
    }
}

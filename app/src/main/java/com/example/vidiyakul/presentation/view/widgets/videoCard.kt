package com.example.vidiyakul.presentation.view.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidiyakul.presentation.view.ImageItems
import com.example.vidiyakul.presentation.view.videoplayer.VideoPlayer
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPagerWidget(list: List<String>, isVideo : Boolean ) {

    val gradientColors = listOf(
        Color(0xFF7B62E9), // hsla(251, 76%, 65%, 1)
        Color(0xFF543AE3), // hsla(249, 75%, 56%, 1)
        Color(0xFF321EB4)  // hsla(248, 71%, 41%, 1)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Column {
            Text(
                "जानिए कैसे हमनै बनाए Board Toppers",
                color = Color.White,
                fontWeight = FontWeight.W700,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val centerIndex = list.size / 2
            val pagerState = rememberPagerState(
                initialPage = centerIndex,
                pageCount = { list.size }
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 70.dp),
            ) { page ->

                val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                val scale = lerp(1f, 0.8f, pageOffset.coerceIn(0f, 1f))

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .width(400.dp)
                        .height(360.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                ) {
                    if (isVideo){
                        VideoPlayer(
                            videoUrl = list[centerIndex],
                            playWhenReady = pagerState.settledPage == page,
                            modifier = Modifier.matchParentSize()
                        )
                    }else{
                        ImagePlayer(list[centerIndex])
                    }
                }
            }
        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}
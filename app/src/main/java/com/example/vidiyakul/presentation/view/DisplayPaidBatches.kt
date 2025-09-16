package com.example.vidiyakul.presentation.view


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidiyakul.R
import com.example.vidiyakul.presentation.view.widgets.AskDoubtButtonWithHand
import com.example.vidiyakul.presentation.view.widgets.CustomPagerWidget
import kotlin.math.abs


@Composable
fun DisplayPaidBatches (){

    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        expanded = true
    }

    val list = listOf(
"https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
        "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_30MB.mp4",
    )

    val shortsIds = listOf(
        "fvYfgPk1it4",
        "k2wGpDwAB0A",
        "v1r6_zVzFRg",
        "dtPSCUmK1yc",
        "JvF-1eMl2fU",
        "hiTrQzrtkIo",
        "v1r6_zVzFRg"
        )

    Column (modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center)
    {

        Spacer(Modifier.height(180.dp))

            CustomPagerWidget(list,isVideo = true)

            Spacer(Modifier.height(30.dp))

            AskDoubtButtonWithHand({})

            Spacer(Modifier.height(30.dp))

    }

}














@Composable
fun SingleDemoItem(){
    Card(

        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent ),
        border = BorderStroke(width = 1.dp, color = Color.Gray)
    ) {
        Column {

            Surface(modifier = Modifier
                .height(150.dp)
                .width(300.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.image1),contentDescription = "sample image")
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    Text("सामान्य हिन्दी ", fontSize = 12.sp, color = Color.Blue)
                    Text(" By Rishi Sir", fontSize = 10.sp, color = Color.Gray)
                }
                Text("अलंकार\n")
                Text("04 Jan 11:07 AM",style = TextStyle(color = Color.Gray), fontSize = 10.sp)
                Text("Duration: 17 Minutes", fontSize = 10.sp, color = Color.Black, fontWeight = FontWeight.Light)
            }

        }
    }
}

@Composable
fun ImageItems(id : Int){
    Card() {

        Surface(
            modifier = Modifier
                .height(320.dp)
                .width(180.dp)
        ) {
            Image(painter = painterResource(id = id), contentDescription = "sample image")
        }


    }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoCard(list1: List<Int>) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF036ECB)
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                "जानिए कैसे हमनै बनाए Board Toppers",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        val centerIndex = list1.size / 2
        val itemWidth = 180.dp
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val initialScrollOffset = ((itemWidth.value * centerIndex) - (screenWidth.value / 2) + (itemWidth.value / 2)).toInt()

        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = 0,
            initialFirstVisibleItemScrollOffset = maxOf(0, initialScrollOffset) // Scroll to center the middle item
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Spacer(modifier = Modifier.width(100.dp)) // Half of your item width for centering
            }

            itemsIndexed(list1) { index, item ->
                val centerItem = listState.layoutInfo.visibleItemsInfo.firstOrNull {
                    it.index == index + 1
                }
                val viewportCenter = listState.layoutInfo.viewportEndOffset / 2
                val itemCenter = centerItem?.let { it.offset + it.size / 2 } ?: 0
                val distance = abs(viewportCenter - itemCenter)
                val scale = 1f - (distance / 600f).coerceIn(0f, 0.4f)

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .width(itemWidth)
                        .height(360.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    ImageItems(item)
                }
            }

            item {
                Spacer(modifier = Modifier.width(100.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoriCarousel(list1: List<Int>) {

    Card(  modifier = Modifier,colors = CardDefaults.cardColors(
        containerColor = Color(0xFF036ECB) )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                "जानिए कैसे हमनै बनाए Board Toppers",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )

        }


        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { list1.count() },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp),
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = list1[i]
            Image(
                modifier = Modifier
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge),
                painter = painterResource(id = item),
                contentDescription = "images",
                contentScale = ContentScale.Crop
            )
        }
    }
}
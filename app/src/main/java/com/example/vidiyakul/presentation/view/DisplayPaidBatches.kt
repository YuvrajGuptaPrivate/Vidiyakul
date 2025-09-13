package com.example.vidiyakul.presentation.view

import android.R.attr.id
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidiyakul.R


@Composable
fun DisplayPaidBatches (){

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {

        Text("Free Demo Lectures", Modifier.padding(start = 8.dp),fontSize = 18.sp, color = Color.Black)
        Column(modifier = Modifier,
            verticalArrangement = Arrangement.Center
        )
         {
            LazyRow(modifier = Modifier, contentPadding = PaddingValues(8.dp)) {
                items(count = 7) { item ->
                    SingleDemoItem()
                    Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))
                }
            }
        }

        Spacer(Modifier.height(16.dp))


        Column(modifier = Modifier,
            verticalArrangement = Arrangement.Center
        )
        {
            LazyRow(modifier = Modifier, contentPadding = PaddingValues(8.dp))
            {
                item{
                    ImageItems(R.drawable.image2)
                    Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))

                }
                item{
                    ImageItems(R.drawable.image3)
                    Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))

                }
                item{
                    ImageItems(R.drawable.image4)
                    Spacer(modifier = Modifier.size(width = 8.dp, height = 0.dp))

                }
                item{
                    ImageItems(R.drawable.image5)

                }
            }
        }
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

            Surface(modifier = Modifier
                .height(150.dp)
                .width(150.dp)
            ) {
                Image(painter = painterResource(id = id),contentDescription = "sample image")
            }


    }
}
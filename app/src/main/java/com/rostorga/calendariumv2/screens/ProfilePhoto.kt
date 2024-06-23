package com.rostorga.calendariumv2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rostorga.calendariumv2.R

val images = listOf(
    R.drawable.angry,
    R.drawable.bear,
    R.drawable.buffalo,
    R.drawable.bunny,
    R.drawable.cat,
    R.drawable.dog,
    R.drawable.elephant,
    R.drawable.fox,
    R.drawable.horse,
    R.drawable.leon,
    R.drawable.leopard,
    R.drawable.lobocafe,
    R.drawable.rhinoceros,
    R.drawable.sparrow,
    R.drawable.wolf,
)


@Composable
fun MyScreen() {
    val imagePainter = painterResource(id = R.drawable.angry)


    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        CircleWithImage(image = imagePainter)

        Text(
            text = "imagen: ${num.Numero.value +1 }",
            modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
        )

        LazyColumn {
            itemsIndexed(images) { index, image ->
                BoxWithButton(index = index, image = image)
            }
        }
    }


}

@Composable
fun BoxWithButton(index: Int, image: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Imagen",
                modifier = Modifier.size(100.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    num.setNum(index)
                }
            ) {
                Text("foto ${index+1}")
            }
        }
    }
}


@Composable
fun CircleWithImage(
    image: Painter,
    imageSize: Dp = 100.dp,
) {
    Box(
        modifier = Modifier
            .size(imageSize)
            .background(Color.Blue, shape = CircleShape)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyScreen() {
    MyScreen()
}


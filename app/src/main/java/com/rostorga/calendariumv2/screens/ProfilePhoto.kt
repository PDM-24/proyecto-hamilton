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
    val imagePainter = painterResource(id = R.drawable.user_circle)
    val imagePainter1 = painterResource(id = R.drawable.angry)
    val imagePainter2 = painterResource(id = R.drawable.bear)
    val imagePainter3 = painterResource(id = R.drawable.buffalo)
    val imagePainter4 = painterResource(id = R.drawable.bunny)
    val imagePainter5 = painterResource(id = R.drawable.cat)
    val imagePainter6= painterResource(id = R.drawable.dog)
    val imagePainter7 = painterResource(id = R.drawable.elephant)
    val imagePainter8 = painterResource(id = R.drawable.fox)
    val imagePainter9 = painterResource(id = R.drawable.horse)
    val imagePainter10 = painterResource(id = R.drawable.leon)
    val imagePainter11 = painterResource(id = R.drawable.leopard)
    val imagePainter12 = painterResource(id = R.drawable.lobocafe)
    val imagePainter13 = painterResource(id = R.drawable.rhinoceros)
    val imagePainter14 = painterResource(id = R.drawable.sparrow)
    val imagePainter15 = painterResource(id = R.drawable.wolf)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        when(num.Numero.value ){
            1-> {CircleWithImage(image = imagePainter1)}
            2-> {CircleWithImage(image = imagePainter2)}
            3-> {CircleWithImage(image = imagePainter3)}
            4-> {CircleWithImage(image = imagePainter4)}
            5-> {CircleWithImage(image = imagePainter5)}
            6-> {CircleWithImage(image = imagePainter6)}
            7-> {CircleWithImage(image = imagePainter7)}
            8-> {CircleWithImage(image = imagePainter8)}
            9-> {CircleWithImage(image = imagePainter9)}
            10-> {CircleWithImage(image = imagePainter10)}
            11-> {CircleWithImage(image = imagePainter11)}
            12-> {CircleWithImage(image = imagePainter12)}
            13-> {CircleWithImage(image = imagePainter13)}
            14-> {CircleWithImage(image = imagePainter14)}
            15-> {CircleWithImage(image = imagePainter15)}
            else ->{CircleWithImage(image = imagePainter)}
        }

        Text(
            text = "imagen: ${num.Numero.value  }",
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


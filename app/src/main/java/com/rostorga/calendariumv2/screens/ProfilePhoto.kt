package com.rostorga.calendariumv2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rostorga.calendariumv2.R
import com.rostorga.calendariumv2.viewModel.UserViewModel

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
var y = mutableStateOf(num.numero)

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MyScreen( navController: NavController,userViewModel: UserViewModel = viewModel()) {
    val currentGlobalValue = remember {
        num.numero
    }

    val countState = remember { mutableStateOf(currentGlobalValue) }

    val x : Painter
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


    when(y.value){
        1-> {x = imagePainter1}
        2-> {x = imagePainter2}
        3-> {x = imagePainter3}
        4-> {x = imagePainter4}
        5-> {x = imagePainter5}
        6-> {x = imagePainter6 }
        7-> {x = imagePainter7}
        8-> {x = imagePainter8}
        9-> {x = imagePainter9}
        10-> {x = imagePainter10}
        11-> {x = imagePainter11 }
        12-> {x = imagePainter12}
        13-> {x = imagePainter13}
        14-> {x = imagePainter14}
        15-> {x = imagePainter15}
        else ->{x = imagePainter}
    }

    TopAppBar(title = {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate("home") })

        }
    })
    Column(

        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Selecciona Imagen de Perfil",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .offset(y = 10.dp, x = 125.dp)
                .clip(CircleShape)
        ){

            Image(
                painter = x,
                contentDescription = null,
                modifier = Modifier.size(125.dp)
            )
        }

        Button(onClick = {
           num.setNum(0)
            val newValue = countState.value - countState.value
            countState.value = newValue
            num.numero = newValue
            y.value=num.nn.value
        }) {
            Text("quitar  foto ")
        }

        LazyColumn {
            itemsIndexed(images) { index, image ->
                BoxWithButton(index = index, image = image)
            }
        }
    }


}

@Composable
fun BoxWithButton(index: Int, image: Int) {
    val currentGlobalValue = remember {
        num.numero
    }
    val countState = remember { mutableStateOf(currentGlobalValue) }

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
                    num.setNum(index +1)

                    val newValue = index +1
                    countState.value = newValue
                    num.numero = newValue
                    y.value=num.nn.value



                }
            ) {
                Text("foto ${index +1 }")
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
            modifier = Modifier
                .size(imageSize)
        )
    }
}



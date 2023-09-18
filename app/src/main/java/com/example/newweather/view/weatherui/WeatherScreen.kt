@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newweather.view.weatherui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newweather.R
import com.example.newweather.model.remotedata.cities.Location
import com.example.newweather.model.repository.Repo
import com.example.newweather.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(navController: NavController,viewModel: WeatherViewModel){

    val weather by viewModel.weather.collectAsState()
    val places by viewModel.cities.collectAsState()
    var isViewVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        ){
        Image(
            painter = painterResource(id = R.drawable.weatherbackground),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize() )

        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text="Current Weather",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Card( modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.7f)
                .padding(30.dp, 10.dp),
                shape = RoundedCornerShape(10.dp)
            ) {

                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                ) {
                    Column( modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        SearchingBar(viewModel){
                            isViewVisible = true
                        }
                        when (weather) {
                            is Repo.Loading -> {}
                            is Repo.Success -> {
                                weather.data?.let {
                                    WeatherDetails(
                                        name = "${it.name}",
                                        status = "${it.weather[0].main}: ${it.weather[0].description}",
                                        image = "${it.weather[0].icon}",
                                        temp = it.main.temp.toInt().toString() + " ºC",
                                        mintemp = it.main.temp_min.toString() + " ºC",
                                        maxtemp = it.main.temp_max.toString() + " ºC"
                                    )
                                }
                            }
                            is Repo.Error -> {
                                weather.message
                            }
                        }
                    }
                    Card(modifier = Modifier
                        .padding(start=15.dp,top=60.dp,end=15.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                    ) {
                        places.let {
                            SearchedItem(cityList = it,viewModel,isViewVisible){
                                isViewVisible = false
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun SearchingBar(
    viewModel: WeatherViewModel,
    toggleView: () -> Unit
) {
    var text by remember{
        mutableStateOf("")
    }
        TextField(modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            value = text,
            onValueChange = {
                toggleView()
                text = it
                if (it.length>=3){
                    viewModel.getCities(it)
                }
            },
            placeholder = {
                Text(
                    text = "Search here...",
                    color = Color.Gray,
                    fontSize = 17.sp
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        viewModel.getWeather(text)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                containerColor = Color.White
            ))
}


@Composable
fun SearchedItem(cityList :ArrayList<Location>, viewModel: WeatherViewModel, isViewVisible: Boolean, toggle: ()->Unit) {
    if(isViewVisible){
        LazyColumn(
        ) {
            items(count = cityList.size) {
                Text(cityList[it].city,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.getWeather(cityList[it].city)
                            toggle()
                        }
                        .padding(horizontal = 30.dp, vertical = 18.dp),
                    fontSize = 18.sp
                )
                Divider( modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp),
                    color = Color.Black
                )
            }
        }
    }

}

@Composable
fun WeatherDetails(
    name :String,
    status : String,
    image : String,
    temp :String,
    mintemp :String,
    maxtemp :String
){
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp,20.dp)
        )
        Text(
            text = status,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp,10.dp)
        )
        Image(painter = rememberAsyncImagePainter("https://openweathermap.org/img/w/${image}.png"),
            contentDescription = "null",
            modifier = Modifier
                .height(150.dp)
                .width(200.dp))
        Text(
            text = temp,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp,0.dp,0.dp,10.dp)
        )
        Row(modifier = Modifier
            .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TempArrangement(value = "min", temp = mintemp)
            TempArrangement(value = "max", temp = maxtemp)
        }
    }
}

@Composable
fun TempArrangement(
    value:String,
    temp :String
){
    Column()
         {
        Text(
            text = value,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(30.dp,5.dp)
        )
        Text(
            text = temp,
            fontSize = 25.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp,5.dp)
        )
    }
}


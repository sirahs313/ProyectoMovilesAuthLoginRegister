package com.example.MovilesSUax.pages



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize()
            .background(Color(0xFFF44336)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment =  Alignment.CenterHorizontally
    ){
        Text(
            text = "Welcome to UPP",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black


        )

        Text(
            text ="This is the Profile Page",
            fontSize = 20.sp,
            color = Color.Black

        )
    }

}
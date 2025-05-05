package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cvut.fel.zan.movielibrary.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            /*.background(colorResource(R.color.dark_ocean)),*/
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.popcorn),
            contentDescription = stringResource(R.string.splashscreen),
            modifier = Modifier.fillMaxWidth()
                .padding(top = 70.dp)
        )
        Text(
            text = "Hi, welcome to the Movie Library",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
    LaunchedEffect(Unit) {
        delay(1000L)
        onNavigate()
    }
}
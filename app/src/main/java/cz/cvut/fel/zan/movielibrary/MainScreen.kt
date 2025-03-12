package cz.cvut.fel.zan.movielibrary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cvut.fel.zan.movielibrary.ui.theme.MovieLibraryTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MovieLibraryTheme {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    Scaffold (
        topBar = { TopBarMainScreen() },
        bottomBar = { BottomBarMainScreen() },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dark_ocean))
        ) {
            MainScreenContent(
                paddingValues = innerPadding
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMainScreen() {
    TopAppBar(
        title = { Text(
            text = "Movie library",
            color = colorResource(R.color.white),
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ) },
        navigationIcon = {
            IconButton(onClick = { /* TODO Open Nav Drawer */ }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.menu),
                    tint = colorResource(R.color.white)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO Open profile */ }) {
                Icon(Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = colorResource(R.color.white)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_blue)
        )
    )
}

@Composable
fun MainScreenContent(paddingValues: PaddingValues) {
//    val scrollableColumnState = rememberScrollState()

    Column (
        modifier = Modifier
            .padding(paddingValues)
//            .verticalScroll(scrollableColumnState)
            .fillMaxSize()
    ) {
        ListMovies()
    }
}

@Composable
fun ListMovies() {
    val movies = GetAllMovies()

    /*val titles = listOf(
        *//* TODO *//*
        "Alien Romulus", "Jurassic World", "Meg 2",
        "Beast", "Conan", "The Penthouse",
        "Doraemon", "Love You 7 Times", "Hidden Love",
        "Titanic", "Love Game", "Dragon Ball"
    )
    val pictures = listOf(
        R.drawable.alien_romulus, R.drawable.jurassic_world, R.drawable.meg_2,
        R.drawable.beast, R.drawable.conan, R.drawable.the_penthouse,
        R.drawable.doraemon, R.drawable.love_you_seven_times, R.drawable.hidden_love,
        R.drawable.titanic, R.drawable.love_game, R.drawable.dragon_ball
    )*/
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.height(1000.dp)
    ) {
        items(movies) {movie ->
            MovieItem(title = movie.movieTitle, picture = movie.movieImage)
        }
    }
}

@Composable
fun MovieItem(title : String, picture : Int) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(110.dp)
    ) {
        Image(
            painter = painterResource(id = picture),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.white)
        )
    }
}

@Composable
fun BottomBarMainScreen() {
    NavigationBar(
        containerColor = colorResource(R.color.purple_blue)
    ) {
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Home,
                    contentDescription = stringResource(R.string.home),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Home",
                    color = colorResource(R.color.white)
                ) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Search,
                    contentDescription = stringResource(R.string.genre),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Genres",
                    color = colorResource(R.color.white)
                ) },
            selected = false,
            onClick = { /* TODO Open List Genres Screen */ }
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Star,
                    contentDescription = stringResource(R.string.favorite_films),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "Favorite films",
                    color = colorResource(R.color.white)
                ) },
            selected = false,
            onClick = { /* TODO Open Favorite Films Screen */ }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.profile),
                    tint = colorResource(R.color.white)
                )
            },
            label = {
                Text(text = "User profile",
                    color = colorResource(R.color.white)
                ) },
            selected = false,
            onClick = { /* TODO Open User Profile Screen */ }
        )
    }
}
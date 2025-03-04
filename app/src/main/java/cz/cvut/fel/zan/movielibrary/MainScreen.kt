package cz.cvut.fel.zan.movielibrary

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        topBar = { TopBar() },
        bottomBar = { BottomBar() },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainScreenContent(
            paddingValues = innerPadding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text("Movie library") },
        navigationIcon = {
            IconButton(onClick = { /* TODO Open Nav Drawer */ }) {
                Icon(Icons.Filled.Menu, contentDescription = stringResource(R.string.menu))
            }
        },
        actions = {
            IconButton(onClick = { /* TODO Open profile */ }) {
                Icon(Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.profile))
            }
        }
    )
}

@Composable
fun MainScreenContent(paddingValues: PaddingValues) {
    /* Display list of movies */
    Column (modifier = Modifier.padding(paddingValues)) {
        ListMovies()
    }
}

@Composable
fun ListMovies() {
    val titles = listOf( /* TODO */
    "Alien Romulus", "Film 2", "Film 3",
    "Film 4", "Film 5", "Film 6",
    "Film 7", "Film 8", "Film 9"
    )
//    val pictures = listOf(
//        R.drawable.alien_romulus, /* TODO */
//    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(titles.size) { index ->
            MovieItem(titles[index])
        }
    }
}

@Composable
fun MovieItem(title : String) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(110.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.alien_romulus),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.home))
            },
            label = { Text("Home") },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.Star, contentDescription = stringResource(R.string.favorite_films))
            },
            label = { Text("Favorite films") },
            selected = false,
            onClick = { /* TODO Open Favorite Films Screen */ }
        )
        NavigationBarItem(
            icon = {
                Icon(Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.profile))
            },
            label = { Text("User profile") },
            selected = false,
            onClick = { /* TODO Open User Profile Screen */ }
        )
    }
}
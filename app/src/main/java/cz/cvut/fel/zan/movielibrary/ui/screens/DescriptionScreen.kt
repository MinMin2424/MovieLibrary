@file:OptIn(ExperimentalMaterial3Api::class)

package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.material3.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.data.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.components.RenderSnackBar
import kotlinx.coroutines.launch

@Composable
fun DescriptionScreen(
    movieInfo: MovieInfo,
    navController: NavController,
    onAddToFavorites: () -> Unit,
    onAddComment: (String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var newComment by remember { mutableStateOf("") }

    Scaffold (
        topBar = { TopBarDescriptionScreen(
            navController = navController,
            onAddToFavorites = {
                onAddToFavorites()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Movie added to favorites",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        ) },
        bottomBar = { BottomBarMainScreen(navController) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                RenderSnackBar(data)
            }
        },
        modifier = Modifier.fillMaxSize()

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dark_ocean))
        ) {
            DescriptionScreenContent(
                paddingValues = innerPadding,
                movieInfo = movieInfo,
                newComment = newComment,
                onCommentChange = { newComment = it },
                onAddComment = {
                    onAddComment(it)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Comment added successfully",
                            duration = SnackbarDuration.Short
                        )
                    }
                    newComment = ""
                }
            )
        }
    }
}

@Composable
fun DescriptionScreenContent(
    paddingValues: PaddingValues,
    movieInfo: MovieInfo,
    newComment: String,
    onCommentChange: (String) -> Unit,
    onAddComment: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
            RenderImage(movieInfo.movieImage, movieInfo.movieTitle)
            Spacer(modifier = Modifier.height(16.dp))
            RenderTitle(movieInfo.movieTitle)
            Spacer(modifier = Modifier.height(8.dp))
            RenderInfo(movieInfo)
            Spacer(modifier = Modifier.height(16.dp))
            RenderDescription(movieInfo.description)
            Spacer(modifier = Modifier.height(16.dp))
            RenderComments(
                comments = movieInfo.comments,
                newComment = newComment,
                onCommentChange = onCommentChange,
                onAddComment = onAddComment
            )
        }
    }
}

@Composable
fun RenderImage(picture: Int, name: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        Image(
            painter = painterResource(picture),
            contentDescription = name,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RenderTitle(movieTitle: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        Text(
            text = movieTitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.white),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RenderInfo(movieInfo: MovieInfo) {
    val genresFormatted = movieInfo.genre
        .joinToString(", ") { it.name.lowercase() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text (
            text = "Rating: ${movieInfo.rating}",
            color = colorResource(R.color.white),)
        Spacer(modifier = Modifier.height(4.dp))
        Text (
            text = "Episodes: ${movieInfo.episodes}",
            color = colorResource(R.color.white),)
        Spacer(modifier = Modifier.height(4.dp))
        Text (
            text = "Genres: $genresFormatted",
            color = colorResource(R.color.white),)
        Spacer(modifier = Modifier.height(4.dp))
        Text (
            text = "Country: ${movieInfo.country}",
            color = colorResource(R.color.white),)
    }
}

@Composable
fun RenderDescription(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = stringResource(R.string.description),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colorResource(R.color.white)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text (
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.white),
        )
    }

}

@Composable
fun RenderComments(
    comments: List<String>,
    newComment: String,
    onCommentChange: (String) -> Unit,
    onAddComment: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text (
            text = stringResource(R.string.comments),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            color = colorResource(R.color.white)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = newComment,
            onValueChange = onCommentChange,
            label = { Text ("Write your opinions about the movie") },
            textStyle = LocalTextStyle.current.copy(Color.White),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onAddComment(newComment) },
            ) {
                Text(stringResource(R.string.send))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        comments.forEach { comment ->
            Text(
                text = comment,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                color = colorResource(R.color.white)
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDescriptionScreen(
    navController: NavController,
    onAddToFavorites: () -> Unit
) {
    TopAppBar(
        title = { Text(
            text = "Movie library",
            color = colorResource(R.color.white),
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ) },
        navigationIcon = {
            IconButton(onClick = {
                /* Go back */
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.navigate_back),
                    tint = colorResource(R.color.white)
                )
            }
        },
        actions = {
            IconButton(onClick = onAddToFavorites) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.add_to_favourites),
                    tint = colorResource(R.color.white)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_blue)
        )
    )
}

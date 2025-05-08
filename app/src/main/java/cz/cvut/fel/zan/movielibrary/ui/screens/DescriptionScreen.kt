@file:OptIn(ExperimentalMaterial3Api::class)

package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.data.local.MovieInfo
import cz.cvut.fel.zan.movielibrary.ui.components.RenderSnackBar
import cz.cvut.fel.zan.movielibrary.ui.utils.isLandscape
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieEditEvent
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel
import cz.cvut.fel.zan.movielibrary.ui.viewModel.ProfileScreenEditEvent
import cz.cvut.fel.zan.movielibrary.ui.viewModel.UserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DescriptionScreen(
    movie: MovieInfo,
    navController: NavController,
    onAddToFavorites: () -> Unit,
    movieViewModel: MovieViewModel,
    userViewModel: UserViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,

    ) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var newComment by rememberSaveable { mutableStateOf("") }

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
        val currentMovie by movieViewModel.currentMovie.collectAsState()
        currentMovie?.let {  nonNullMovie ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    /*.background(colorResource(R.color.dark_ocean))*/
                    .background(MaterialTheme.colorScheme.background)
            ) {
                DescriptionScreenContent(
                    paddingValues = innerPadding,
                    movieInfo = nonNullMovie,
                    newComment = newComment,
                    onCommentChange = { newComment = it },
                    onAddComment = {
                        movieViewModel.onEvent(MovieEditEvent.AddCommentChanged(nonNullMovie.localId, it))
                        userViewModel.onEvent(ProfileScreenEditEvent.AddCommentChanged(1))
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Comment added successfully",
                                duration = SnackbarDuration.Short
                            )
                        }
                        newComment = ""
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DescriptionScreenContent(
    paddingValues: PaddingValues,
    movieInfo: MovieInfo,
    newComment: String,
    onCommentChange: (String) -> Unit,
    onAddComment: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item {
            if (isLandscape()) {
                RenderImageAndInfo(
                    movieInfo = movieInfo,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            } else {
                Spacer(modifier = Modifier.height(32.dp))
                RenderImage(
                    picture = movieInfo.movieImage,
                    name = movieInfo.movieTitle,
                    id = movieInfo.localId,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                Spacer(modifier = Modifier.height(16.dp))
                RenderTitle(movieInfo.movieTitle, TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                RenderInfo(movieInfo)
            }
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RenderImage(
    picture: String,
    name: String,
    id: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        sharedTransitionScope.run {
            AsyncImage(
                model = picture,
                contentDescription = name,
                modifier = Modifier
                    .sharedElement(
                        rememberSharedContentState(key = "image_${id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 300)
                        }
                    )
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun RenderTitle(movieTitle: String, textAlign: TextAlign) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 64.dp)
    ) {
        Text(
            text = movieTitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = textAlign,
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RenderInfo(movieInfo: MovieInfo) {
    val genresFormatted = movieInfo.genre
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = "Rating: ${movieInfo.rating}",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Total seasons: ${movieInfo.totalSeasons}",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Genres: $genresFormatted",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Country: ${movieInfo.country}",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Year: ${movieInfo.year}",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary
        )
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
            /*color = colorResource(R.color.white)*/
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text (
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary
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
            /*color = colorResource(R.color.white)*/
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = newComment,
            onValueChange = onCommentChange,
            label = { Text ("Write your opinions about the movie") },
            /*textStyle = LocalTextStyle.current.copy(Color.White),*/
            textStyle = LocalTextStyle.current.copy(MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.secondary
            )
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
                /*color = colorResource(R.color.white)*/
                color = MaterialTheme.colorScheme.primary
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RenderImageAndInfo(
    movieInfo: MovieInfo,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .padding(horizontal = 64.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(end = 16.dp),
        ) {
            sharedTransitionScope.run {
                AsyncImage(
                    model = movieInfo.movieImage,
                    contentDescription = movieInfo.movieTitle,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "image_${movieInfo.localId}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = { _, _ ->
                                tween(durationMillis = 300)
                            }
                        )
                        .size(180.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column {
            RenderTitle(movieInfo.movieTitle, TextAlign.Left)
            Spacer(modifier = Modifier.height(16.dp))
            RenderInfo(movieInfo)
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
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary,
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
                    /*tint = colorResource(R.color.white)*/
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(onClick = onAddToFavorites) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(R.string.add_to_favourites),
                    /*tint = colorResource(R.color.white)*/
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            /*containerColor = colorResource(R.color.purple_blue)*/
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}

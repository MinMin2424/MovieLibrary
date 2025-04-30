package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.ui.components.RenderSnackBar
import cz.cvut.fel.zan.movielibrary.ui.navigation.Routes
import cz.cvut.fel.zan.movielibrary.ui.viewModel.MovieViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import cz.cvut.fel.zan.movielibrary.data.repository.MovieResult

@Composable
fun AddMovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    var title by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold (
        topBar = { TopBarAddMovieScreen(navController) },
        bottomBar = { BottomBarMainScreen(navController) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                RenderSnackBar(data)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.dark_ocean))
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Enter a title to add a movie") },
                shape = RoundedCornerShape(10.dp),
                textStyle = LocalTextStyle.current.copy(Color.White),
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.fetchAndInsertMovieByTitle(title) { result ->
                        scope.launch {
                            when (result) {
                                MovieResult.SUCCESS -> {
                                    snackbarHostState.showSnackbar("Movie added!")
                                    navController.popBackStack()
                                }

                                MovieResult.MOVIE_ALREADY_EXISTS -> {
                                    snackbarHostState.showSnackbar("Movie already exists!")
                                }

                                MovieResult.MOVIE_NOT_FOUND -> {
                                    snackbarHostState.showSnackbar("Movie not found or failed!")
                                }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Add movie")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAddMovieScreen(
    navController: NavController
) {
    TopAppBar(
        title = { Text(
            text = "Add movie",
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
            IconButton(onClick = {
                /* Open profile */
                navController.navigate(Routes.Profile.route)
            }) {
                Icon(
                    Icons.Filled.AccountCircle,
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
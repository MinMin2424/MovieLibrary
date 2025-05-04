
package cz.cvut.fel.zan.movielibrary.ui.screens

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationManagerCompat
import cz.cvut.fel.zan.movielibrary.data.repository.MovieResult
import cz.cvut.fel.zan.movielibrary.ui.notification.showMovieAddedNotification
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.runtime.LaunchedEffect
import cz.cvut.fel.zan.movielibrary.ui.notification.scheduleMovieNotification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    var title by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                showPermissionDialog = true
            }
        }
    }

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
                                    scheduleMovieNotification(context, title)
//                                    snackbarHostState.showSnackbar("Movie added!")
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
        if (showPermissionDialog) {
            BasicAlertDialog(
                onDismissRequest = { showPermissionDialog = false },
                modifier = Modifier.wrapContentSize()
            ) {
                Surface(
                    modifier = Modifier
                        .padding(16.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = MaterialTheme.shapes.medium
                        ),
                    shape = MaterialTheme.shapes.medium,
                    tonalElevation = 8.dp,
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh
                ) {
                    Column (modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = "Notification Permission Required",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "To receive information about the new movie, please enable notifications in app settings",
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(24.dp))
                        Row (
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            TextButton(
                                onClick = {showPermissionDialog = false},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 2.dp,
                                    pressedElevation = 4.dp
                                )
                            ) {
                                Text("Cancel")
                            }
                            Spacer(Modifier.width(8.dp))
                            TextButton(
                                onClick = {
                                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                    }
                                    context.startActivity(intent)
                                    showPermissionDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                ),
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 2.dp,
                                    pressedElevation = 4.dp
                                )
                            ) {
                                Text(
                                    text = "Open Settings",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
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
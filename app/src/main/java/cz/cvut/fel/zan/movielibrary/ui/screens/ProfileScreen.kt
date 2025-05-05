package cz.cvut.fel.zan.movielibrary.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.data.local.UserInfo
import cz.cvut.fel.zan.movielibrary.ui.utils.isLandscape
import cz.cvut.fel.zan.movielibrary.ui.viewModel.ThemeViewModel

@Composable
fun ProfileScreen(
    userInfo: UserInfo,
    navController: NavController,
    onEditInfo: (String, String) -> Unit,
    themeViewModel: ThemeViewModel = viewModel()
) {

    var isEditing by rememberSaveable { mutableStateOf(false) }
    var editedName by rememberSaveable { mutableStateOf(userInfo.name) }
    var editedEmail by rememberSaveable { mutableStateOf(userInfo.email) }

    Scaffold (
        topBar = { TopBarProfileScreen(
            isEditing = isEditing,
            onEditClick = { isEditing = true },
            onCancelClick = {
                isEditing = false
                editedName = userInfo.name
                editedEmail = userInfo.email
            },
            themeViewModel = themeViewModel
        ) },
        bottomBar = { BottomBarMainScreen(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                /*.background(colorResource(R.color.dark_ocean))*/
                .background(MaterialTheme.colorScheme.background)
        ) {
            ProfileScreenContent(
                paddingValues = innerPadding,
                userInfo = userInfo,
                isEditing = isEditing,
                editedName = editedName,
                editedEmail = editedEmail,
                onNameChange = { editedName = it },
                onEmailChange = { editedEmail = it },
                onSaveChanges = {
                    onEditInfo(editedName, editedEmail)
                    isEditing = false
                },
                onCancelChanges = {
                    isEditing = false
                    editedName = userInfo.name
                    editedEmail = userInfo.email
                },
                onEditClick = { isEditing = true }
            )
        }
    }
}

@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues,
    userInfo: UserInfo,
    isEditing: Boolean,
    editedName: String,
    editedEmail: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSaveChanges: () -> Unit,
    onCancelChanges: () -> Unit,
    onEditClick: () -> Unit
) {
    if (isLandscape()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(vertical = 16.dp)
                .padding(horizontal = 64.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp),
            ) {
                Image(
                    painter = painterResource(userInfo.profileImage),
                    contentDescription = stringResource(R.string.profile_picture),
                    modifier = Modifier
                        .size(180.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                RenderProfileInfo(
                    userInfo = userInfo,
                    isEditing = isEditing,
                    editedName = editedName,
                    editedEmail = editedEmail,
                    onNameChange = onNameChange,
                    onEmailChange = onEmailChange,
                    onSaveChanges = onSaveChanges,
                    onCancelChanges = onCancelChanges,
                    onEditClick = onEditClick
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column {

                    RenderProfilePicture(userInfo.profileImage)
                    Spacer(modifier = Modifier.height(8.dp))
                    RenderProfileInfo(
                        userInfo = userInfo,
                        isEditing = isEditing,
                        editedName = editedName,
                        editedEmail = editedEmail,
                        onNameChange = onNameChange,
                        onEmailChange = onEmailChange,
                        onSaveChanges = onSaveChanges,
                        onCancelChanges = onCancelChanges,
                        onEditClick = onEditClick
                    )
                }
            }
        }
    }
}

@Composable
fun RenderProfilePicture(picture : Int) {
    Image(
        painter = painterResource(picture),
        contentDescription = stringResource(R.string.profile_picture),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp)
            .padding(vertical = 32.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RenderInfo(
    info : String,
    fontSize : Int,
    fontWeight : FontWeight
) {
    Text (
        text = info,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        /*color = Color.White,*/
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    )
}

@Composable
fun RenderStatisticItem(label: String, value: String) {
    Text(
        text = "$value  $label",
        fontSize = 16.sp,
        /*color = Color.White,*/
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    )
}

@Composable
fun RenderProfileInfo(
    userInfo: UserInfo,
    isEditing: Boolean,
    editedName: String,
    editedEmail: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSaveChanges: () -> Unit,
    onCancelChanges: () -> Unit,
    onEditClick: () -> Unit
) {
    if (isEditing) {
        OutlinedTextField(
            value = editedName,
            onValueChange = onNameChange,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp),
            label = { Text(text = "Name", color = MaterialTheme.colorScheme.primary, fontSize = 16.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    } else {
        RenderInfo(info = userInfo.name,
            fontSize = 20,
            fontWeight = FontWeight.Bold)
    }
    Spacer(modifier = Modifier.height(8.dp))

    if (isEditing) {
        OutlinedTextField(
            value = editedEmail,
            onValueChange = onEmailChange,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary, fontSize = 14.sp),
            label = { Text(text = "E-mail", color = MaterialTheme.colorScheme.primary, fontSize = 16.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )
    } else {
        RenderInfo(info = "E-mail: ${userInfo.email}",
            fontSize = 16,
            fontWeight = FontWeight.Normal)
    }
    Spacer(modifier = Modifier.height(8.dp))

    RenderInfo(info = "Registration date: ${userInfo.registrationDate}",
        fontSize = 16,
        fontWeight = FontWeight.Normal)
    Spacer(modifier = Modifier.height(8.dp))

    RenderInfo(info = "Statistic",
        fontSize = 16,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    RenderStatisticItem(
        label = "Favorite Movies",
        value = userInfo.favoriteMoviesCount.toString()
    )
    RenderStatisticItem(
        label = "Comments",
        value = userInfo.commentsCount.toString()
    )
    Spacer(modifier = Modifier.height(8.dp))
    if (isEditing) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onSaveChanges,
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Save changes")
            }
            Button(
                onClick = onCancelChanges,
                modifier = Modifier.width(150.dp)
            ) {
                Text(text = "Cancel")
            }
        }
    } else {
        Button(
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text(text = "Edit info")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfileScreen(
    isEditing : Boolean,
    onEditClick : () -> Unit,
    onCancelClick : () -> Unit,
    themeViewModel: ThemeViewModel
) {
    val isDarkMode by themeViewModel.isDarkMode
    TopAppBar(
        title = { Text(
            text = "User profile",
            /*color = colorResource(R.color.white),*/
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ) },
        navigationIcon = {
            IconButton(onClick = { /* TODO Open Nav Drawer */ }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.menu),
                    /*tint = colorResource(R.color.white)*/
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(onClick = {
                /* TODO Edit profile */
                if (isEditing) {
                    onCancelClick()
                } else {
                    onEditClick()
                }
            }) {
                Icon(
                    imageVector = if (isEditing) Icons.Filled.Close else Icons.Filled.Create,
                    contentDescription = if (isEditing) stringResource(R.string.cancel) else stringResource(
                        R.string.edit_profile
                    ),
                    /*tint = colorResource(R.color.white)*/
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = {
                themeViewModel.toggleTheme()
            }) {
                Icon(
                    painter = painterResource(
                        id = if (isDarkMode) R.drawable.light_mode else R.drawable.dark_mode
                    ),
                    contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
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
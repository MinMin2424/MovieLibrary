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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.cvut.fel.zan.movielibrary.ui.theme.MovieLibraryTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MovieLibraryTheme {
        ProfileScreen(
            UserInfo(
                name = "MinMin Tranova",
                email = "goldenmaknae2424@gmail.com",
                profileImage = R.drawable.profile_picture,
                registrationDate = "04.03.2025"
            ),
//            onEditInfo = { newName, newEmail ->
//
//            }
        )
    }
}

@Composable
fun ProfileScreen(
    userInfo: UserInfo,
//    onEditInfo: (String, String) -> Unit
) {
    Scaffold (
        topBar = { TopBarProfileScreen() },
        bottomBar = { BottomBarMainScreen() },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dark_ocean))
        ) {
            ProfileScreenContent(
                paddingValues = innerPadding,
                userInfo,
//                onEditInfo
            )
        }
    }
}

@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues,
    userInfo: UserInfo,
//    onEditInfo: (String, String) -> Unit
) {

    var isEditing by remember { mutableStateOf(true) }
    var editedName by remember { mutableStateOf(userInfo.name) }
    var editedEmail by remember { mutableStateOf(userInfo.email) }

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

                if (isEditing) {
                    OutlinedTextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
                        label = { Text(text = "Name", color = Color.White, fontSize = 16.sp) },
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
                        onValueChange = { editedEmail = it },
                        textStyle = TextStyle(color = Color.Gray, fontSize = 14.sp),
                        label = { Text(text = "E-mail", color = Color.White, fontSize = 16.sp) },
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
                Button(
                    onClick = { /* TODO Save changes or edit info */ },
                    modifier = Modifier.fillMaxWidth()
                        .padding(32.dp)
                ) {
                    Text(text = if (isEditing) "Save changes" else "Edit info")
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
        color = Color.White,
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
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfileScreen() {
    TopAppBar(
        title = { Text(
            text = "User profile",
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
            IconButton(onClick = { /* TODO Edit profile */ }) {
                Icon(
                    Icons.Filled.Create,
                    contentDescription = stringResource(R.string.edit_profile),
                    tint = colorResource(R.color.white)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.purple_blue)
        )
    )
}
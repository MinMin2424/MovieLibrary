package cz.cvut.fel.zan.movielibrary.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.cvut.fel.zan.movielibrary.R
import cz.cvut.fel.zan.movielibrary.ui.navigation.Routes

@Composable
fun RenderDrawer(
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(colorResource(R.color.purple_700))
            .padding(16.dp)
    ) {
        Text(
            text = "Movie Library",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        DrawerItem(
            icon = Icons.Filled.Home,
            label = stringResource(R.string.home),
            route = Routes.Main.route,
            navController = navController,
            onCloseDrawer = onCloseDrawer
        )
        DrawerItem(
            icon = Icons.Filled.Search,
            label = stringResource(R.string.genre),
            route = Routes.ListGenres.route,
            navController = navController,
            onCloseDrawer = onCloseDrawer
        )
        DrawerItem(
            icon = Icons.Filled.Star,
            label = stringResource(R.string.favorite_films),
            route = Routes.FavoriteMovies.route,
            navController = navController,
            onCloseDrawer = onCloseDrawer
        )
        DrawerItem(
            icon = Icons.Filled.AccountCircle,
            label = stringResource(R.string.profile),
            route = Routes.Profile.route,
            navController = navController,
            onCloseDrawer = onCloseDrawer
        )
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    route: String,
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
                onCloseDrawer()
            }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}
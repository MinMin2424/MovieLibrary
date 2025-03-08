package cz.cvut.fel.zan.movielibrary

import androidx.compose.material3.Button
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun DescriptionScreenPreview() {
    MovieLibraryTheme {
        DescriptionScreen(
            MovieInfo(
                movieTitle  = "Doraemon",
                movieImage = R.drawable.doraemon,
                rating = "5/5",
                episodes = 2300,
                country = "Japan",
                description = "Doraemon is a Japanese anime series created by Fujiko F. Fujio in 1969. It follows the adventures of a robotic cat from the 22nd century who travels back in time to assist a young boy named Nobita Nobi. Using various futuristic gadgets from his four-dimensional pocket, Doraemon helps Nobita navigate the challenges and mishaps he encounters. The series is renowned for its entertaining yet educational content, capturing the hearts of audiences across multiple generations."
            )
        )
    }
}

@Composable
fun DescriptionScreen(movieInfo: MovieInfo) {
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
            DescriptionScreenContent(
                paddingValues = innerPadding,
                movieInfo
            )
        }
    }
}

@Composable
fun DescriptionScreenContent(
    paddingValues: PaddingValues,
    movieInfo: MovieInfo
) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        item {
            RenderSubNavigation()
//            Spacer(modifier = Modifier.height(16.dp))
            RenderImage(movieInfo.movieImage, movieInfo.movieTitle)
            Spacer(modifier = Modifier.height(16.dp))
            RenderTitle(movieInfo.movieTitle)
            Spacer(modifier = Modifier.height(8.dp))
            RenderInfo(movieInfo)
            Spacer(modifier = Modifier.height(16.dp))
            RenderDescription(movieInfo.description)
            Spacer(modifier = Modifier.height(16.dp))
            RenderComments(movieInfo.comments)
        }
    }
}

@Composable
fun RenderSubNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { /* TODO */ }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(R.string.navigate_back),
                tint = colorResource(R.color.white)
            )
        }
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.add_to_favourites),
                tint = colorResource(R.color.white)
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
fun RenderComments(comments: List<String>) {
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
            value = "",
            onValueChange = { /* TODO */ },
            label = { Text ("Write your opinions about the movie") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* TODO */ },

                ) {
                Text("Send")
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


package cz.cvut.fel.zan.movielibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.cvut.fel.zan.movielibrary.ui.theme.MovieLibraryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieLibraryTheme {
//                MainScreen()
//                DescriptionScreen(
//                    Doraemon()
//                )
                FavoriteScreen(
//                    favoriteMovies = emptyList()
                    favoriteMovies = listOf(
                        Doraemon(),
                        Conan()
                    )
                )
            }
        }
    }
}

fun Doraemon() : MovieInfo {
    return MovieInfo(
        movieTitle  = "Doraemon",
        movieImage = R.drawable.doraemon,
        rating = "5/5",
        episodes = 2300,
        country = "Japan",
        description = "Doraemon is a Japanese anime series created by Fujiko F. Fujio in 1969. It follows the adventures of a robotic cat from the 22nd century who travels back in time to assist a young boy named Nobita Nobi. Using various futuristic gadgets from his four-dimensional pocket, Doraemon helps Nobita navigate the challenges and mishaps he encounters. The series is renowned for its entertaining yet educational content, capturing the hearts of audiences across multiple generations.",
        comments = listOf(
            "Doraemon is a timeless classic! The combination of adventure, humor, and heartfelt moments makes it enjoyable for all ages.",
            "I love how this movie teaches important life lessons while keeping the story fun and entertaining!",
            "The animation and storytelling are amazing. It's nostalgic yet fresh at the same time.",
            "Doraemon's gadgets are always fascinating. They spark the imagination of both kids and adults!",
            "This movie perfectly captures the essence of childhood dreams and friendships. A must-watch!"
        )
    )
}

fun Conan() : MovieInfo {
    return MovieInfo(
        movieTitle = "Detective Conan",
        movieImage = R.drawable.conan,
        rating = "4,5/5",
        episodes = 1100,
        country = "Japan",
        description = "The series intricately weaves mystery, suspense, and action, captivating audiences with its clever plotlines and character development. Each episode presents a unique case, showcasing Conan's deductive prowess as he navigates challenges posed by criminals and the enigmatic Black Organization.",
        comments = listOf(
            "Detective Conan offers a perfect blend of mystery and adventure. Each episode keeps you guessing until the end.",
            "The character development in this series is exceptional. Watching Conan's journey is truly engaging.",
            "With over a thousand episodes, Detective Conan maintains its quality and continues to surprise its audience.",
            "The intricate cases and clever resolutions make this series a must-watch for mystery enthusiasts.",
            "Detective Conan's ability to intertwine long-term story arcs with standalone episodes keeps the narrative fresh and exciting."
        )
    )
}
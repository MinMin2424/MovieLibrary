package cz.cvut.fel.zan.movielibrary.data.local

fun GetAllMovies() : List<MovieInfo> {
    return listOf(
        Alien_Romulus(),
        Jurassic_World(),
        Meg2(),
        /*Beast(),
        Conan(),
        The_Penthouse(),
        Doraemon(),
        Love_U_7_Times(),
        Hidden_Love(),
        Titanic(),
        Love_Game(),
        Dragon_Ball()*/
    )
}

fun Alien_Romulus() : MovieInfo {
    return MovieInfo(
        localId = 1,
        movieTitle = "Alien Romulus",
//        movieImage = R.drawable.alien_romulus,
        movieImage = "",
        rating = "5/5",
        totalSeasons = 1,
//        genre = listOf(Genre.SCI_FI, Genre.HORROR, Genre.THRILLER),
        country = "USA",
        description = "\"Alien: Romulus\" is a new installment in the iconic \"Alien\" franchise, returning to the terrifying roots of the series. The story is set in a dark and dangerous space environment, where a group of astronauts encounters deadly extraterrestrial creatures. The film combines tension, action, and the signature atmosphere of fear that the franchise is known for.",
        comments = listOf(
            "Fans of the series are anticipating a return to the original terrifying tone that made the first films famous.",
            "The direction and screenplay promise a modern take while respecting the legacy of the earlier movies.",
            "Expectations are high for groundbreaking visual effects and intense, claustrophobic scenes."
        )
    )
}

fun Jurassic_World() : MovieInfo {
    return MovieInfo(
        localId = 2,
        movieTitle = "Jurassic World: Dominion",
//        movieImage = R.drawable.jurassic_world,
        movieImage = "",
        rating = "3,5/5",
        totalSeasons = 1,
//        genre = listOf(Genre.ACTION, Genre.ADVENTURE, Genre.SCI_FI),
        country = "USA",
        description = "The \"Jurassic World\" series is a continuation of the iconic \"Jurassic Park\" franchise. It revolves around a fully functioning dinosaur theme park, where genetically engineered dinosaurs escape and wreak havoc. The films explore themes of human ambition, nature, and survival, with plenty of action and dinosaur-driven thrills.",
        comments = listOf(
            "Dominion brought the original cast back—what a treat!",
            "Fallen Kingdom had a darker, more intense vibe.",
            "The plots feel repetitive.",
            "Too much focus on action, not enough on story.",
            "Dominion tried to do too much."
        )
    )
}

fun Meg2() : MovieInfo {
    return MovieInfo(
        localId = 3,
        movieTitle = "Meg 2",
//        movieImage = R.drawable.meg_2,
        movieImage = "",
        rating = "4/5",
        totalSeasons = 1,
//        genre = listOf(Genre.ACTION, Genre.HORROR, Genre.SCI_FI),
        country = "USA, China (co-production)",
        description = "Meg 2: The Trench is the sequel to the 2018 film The Meg. It follows Jonas Taylor (Jason Statham) and a team of scientists as they explore the depths of the ocean, only to encounter not just one, but multiple megalodons and other prehistoric sea creatures. The film combines underwater adventure, survival horror, and over-the-top action as the team battles these ancient predators.",
        comments = listOf(
            "Jason Statham vs. Megalodons—what more could you want?",
            "Bigger, bolder, and more sharks!",
            "Great for a popcorn movie.",
            "Too much focus on action, not enough on story.",
            "The plot is paper-thin.",
            "Too many subplots, not enough focus.",
            "The human characters are forgettable."
        )
    )
}

/*
fun Beast() : MovieInfo {
    return MovieInfo(
        localId = 4,
        movieTitle = "Beast",
        movieImage = R.drawable.beast,
        rating = "5/5",
        episodes = 1,
        genre = listOf(Genre.ACTION, Genre.THRILLER, Genre.SURVIVAL),
        country = "USA",
        description = "Beast is a survival thriller starring Idris Elba as Dr. Nate Samuels, a recently widowed father who takes his two daughters on a trip to a South African game reserve. Their journey turns into a nightmare when they are hunted by a massive, rogue lion seeking revenge against humans for the destruction of its pride. The film focuses on their fight for survival against the relentless predator.",
        comments = listOf(
            "Idris Elba is fantastic!",
            "The lion is terrifying and realistic.",
            "Non-stop tension and thrills.",
            "Beautiful cinematography of the African wilderness.",
            "The plot is predictable.",
            "The lion’s motivation feels forced.",
            "Too much reliance on CGI."
        )
    )
}

fun Conan() : MovieInfo {
    return MovieInfo(
        localId = 5,
        movieTitle = "Detective Conan",
        movieImage = R.drawable.conan,
        rating = "4,5/5",
        episodes = 1100,
        genre = listOf(Genre.ANIMATION, Genre.ADVENTURE, Genre.DETECTIVE),
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

fun The_Penthouse() : MovieInfo {
    return MovieInfo(
        localId = 6,
        movieTitle = "The Penthouse",
        movieImage = R.drawable.the_penthouse,
        rating = "5/5",
        episodes = 3,
        genre = listOf(Genre.DRAMA, Genre.THRILLER, Genre.MYSTERY),
        country = "South Korea",
        description = "The Penthouse is a sensational Korean drama that revolves around the lives of wealthy families living in Hera Palace, a luxurious high-rise apartment building. The story focuses on their ambitions, secrets, and ruthless quest for power, wealth, and prestige. The drama is filled with shocking twists, betrayals, and intense confrontations, as characters go to extreme lengths to protect their interests and achieve their goals.",
        comments = listOf(
            "Addictive and full of twists!",
            "Incredible performances by the cast.",
            "A rollercoaster of emotions.",
            "Lavish and visually stunning.",
            "Over-the-top and unrealistic at times.",
            "Too many characters and subplots.",
            "The pacing slowed in Season 3."
        )
    )
}

fun Doraemon() : MovieInfo {
    return MovieInfo(
        localId = 7,
        movieTitle  = "Doraemon",
        movieImage = R.drawable.doraemon,
        rating = "5/5",
        episodes = 2300,
        genre = listOf(Genre.SCI_FI, Genre.ADVENTURE, Genre.ANIMATION, Genre.COMEDY),
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

fun Love_U_7_Times() : MovieInfo {
    return MovieInfo(
        localId = 8,
        movieTitle = "Love you 7 times",
        movieImage = R.drawable.love_you_seven_times,
        rating = "3/5",
        episodes = 38,
        genre = listOf(Genre.ROMANCE, Genre.FANTASY, Genre.COMEDY),
        country = "China",
        description = "Love You 7 Times is a romantic fantasy drama that follows the story of a young woman who gets the chance to relive her life seven times, each time experiencing a different romantic relationship. The concept is based on the idea of \"seven first kisses,\" where the protagonist explores different types of love and relationships across parallel timelines or alternate realities. Each timeline offers a unique romantic storyline, often featuring different male leads, showcasing various tropes and dynamics in romance.",
        comments = listOf(
            "Unique and creative concept!",
            "Great chemistry between the leads.",
            "Lighthearted and fun.",
            "Some storylines felt rushed."
        )
    )
}

fun Hidden_Love() : MovieInfo {
    return MovieInfo(
        localId = 9,
        movieTitle = "Hidden Love",
        movieImage = R.drawable.hidden_love,
        rating = "3/5",
        episodes = 25,
        genre = listOf(Genre.ROMANCE, Genre.DRAMA, Genre.YOUTH),
        country = "China",
        description = "Hidden Love is a heartwarming and emotional youth romance drama based on the popular novel Secretly Hidden by Zhu Yi. The story follows the journey of Sang Zhi, a young girl who develops a crush on her older brother’s best friend, Duan Jiaxu, when she is just a teenager. As she grows up, her feelings deepen, but she keeps them hidden due to their age difference and the complexities of their relationship. The drama beautifully portrays the innocence of first love, personal growth, and the challenges of pursuing a relationship that seems impossible.",
        comments = listOf(
            "A sweet and touching love story!",
            "Great chemistry between the main couple.",
            "Beautiful portrayal of youth and growth.",
            "Faithful adaptation of the novel.",
            "Some characters felt underdeveloped."
        )
    )
}

fun Titanic() : MovieInfo {
    return MovieInfo(
        localId = 10,
        movieTitle = "Titanic",
        movieImage = R.drawable.titanic,
        rating = "5/5",
        episodes = 1,
        genre = listOf(Genre.ROMANCE, Genre.DRAMA, Genre.HISTORICAL),
        country = "USA",
        description = "Titanic is a 1997 epic romance and disaster film directed by James Cameron. Set aboard the ill-fated RMS Titanic, the story follows Jack Dawson (Leonardo DiCaprio), a free-spirited artist, and Rose DeWitt Bukater (Kate Winslet), a young woman from a wealthy family, who fall in love despite their different social classes. Their romance unfolds against the backdrop of the Titanic's maiden voyage, which ends in tragedy when the ship strikes an iceberg and sinks. The film is known for its breathtaking visuals, emotional storytelling, and historical accuracy in depicting the ship's sinking.",
        comments = listOf(
            "Leonardo DiCaprio and Kate Winslet are phenomenal.",
            "A timeless love story!",
            "Visually stunning and technically groundbreaking."
        )
    )
}

fun Love_Game() : MovieInfo {
    return MovieInfo(
        localId = 11,
        movieTitle = "Love Game in Eastern Fantasy",
        movieImage = R.drawable.love_game,
        rating = "5/5",
        episodes = 30,
        genre = listOf(Genre.ROMANCE, Genre.DRAMA, Genre.HISTORICAL, Genre.FANTASY),
        country = "China",
        description = "A mischievous girl guides a tsundere, embarking on a journey through challenges to create a new world filled with love. Along the way, they level up by capturing demons and defeating monsters, forming friendships. The grudges between humans and demons finally has a resolution in their quest, serving both as the redemption of love and the salvation of the world!",
        comments = listOf(
            "The world-building is stunning!",
            "The romance is swoon-worthy!",
            "Perfect mix of action and emotion.",
            "The costumes and sets are breathtaking."
        )
    )
}

fun Dragon_Ball() : MovieInfo {
    return MovieInfo(
        localId = 12,
        movieTitle = "Dragon Ball",
        movieImage = R.drawable.dragon_ball,
        rating = "5/5",
        episodes = 680,
        genre = listOf(Genre.ACTION, Genre.ADVENTURE, Genre.COMEDY, Genre.FANTASY),
        country = "Japan",
        description = "Dragon Ball is one of the most influential and beloved anime and manga series of all time. It follows the adventures of Son Goku, a pure-hearted and powerful martial artist with a monkey tail, as he searches for the mystical Dragon Balls—seven magical orbs that, when gathered, summon a dragon capable of granting any wish. Along the way, Goku makes friends, battles formidable enemies, and trains to become the strongest fighter in the universe.",
        comments = listOf(
            "Iconic characters and transformations.",
            "Epic battles and power-ups.",
            "Inspiring themes of perseverance and friendship.",
            "Underdeveloped female characters."
        )
    )
}*/

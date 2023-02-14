package com.nrahmatd.moviecatalogue

import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nrahmatd.moviecatalogue.data.MovieRepository
import com.nrahmatd.moviecatalogue.model.MovieModel
import com.nrahmatd.moviecatalogue.model.NavigationItem
import com.nrahmatd.moviecatalogue.model.Screen
import com.nrahmatd.moviecatalogue.ui.component.HomeContent
import com.nrahmatd.moviecatalogue.ui.theme.MovieCatalogueTheme
import com.nrahmatd.moviecatalogue.viewmodel.MoviesViewModel
import com.nrahmatd.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay

object NavDestionation {
    val SplashScreenRoute = "splash_screen"
    val MainScreenRoute = "main_screen"
}

@Composable
fun StartNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavDestionation.SplashScreenRoute,
        modifier = Modifier
    ) {
        composable(route = NavDestionation.SplashScreenRoute) {
            SplashScreen(navController = navController)
        }
        composable(route = NavDestionation.MainScreenRoute) {
            MainScreen()
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.2f,
            // Tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }
            )
        )
        // Custom delay time
        delay(3000L)
        navController.navigate(NavDestionation.MainScreenRoute) {
            popUpTo(NavDestionation.SplashScreenRoute) {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.colorPrimaryDark),
                        colorResource(id = R.color.colorAccent)
                    )
                )
            )
    ) {
        // Change the logo
        Image(
            painter = painterResource(id = R.drawable.ic_the_movie_db),
            contentDescription = stringResource(id = R.string.title_splash_screen)
        )
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { movieId ->
                        navController.navigate(Screen.Detail.createRoute(movieId))
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("movieId") ?: -1
                DetailScreen(
                    movieId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            )
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                    },
                    label = { Text(text = item.title) },
                    selected = true,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    Column {
        Spacer(modifier = modifier.height(16.dp))
        HomeContent(
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id =R.drawable.ic_the_movie_db),
            contentDescription = null,
            modifier = modifier
                .padding(top = 50.dp)
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = colorResource(id = R.color.colorAccent),
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp)
        ) {
            Column{
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    text = stringResource(id = R.string.app_name),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                )
                Text(
                    fontSize = 16.sp,
                    color = Color.White,
                    text = stringResource(id = R.string.app_version),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                )
                Text(
                    fontStyle = FontStyle.Italic,
                    color = Color.White,
                    text = stringResource(id = R.string.dicoding_academy),
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }

        Text(
            text = stringResource(id = R.string.powered_by),
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp)
        ) {
           Card(
               modifier = modifier
                   .size(100.dp),
               backgroundColor = colorResource(id = R.color.colorAccent)
           ) {
               Image(
                   modifier = modifier
                       .size(85.dp),
                   painter = painterResource(id = R.drawable.profile),
                   contentDescription = null)
           }

            Card(
                modifier = modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                backgroundColor = colorResource(id = R.color.colorAccent)
            ) {
                Column {
                    Text(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(start = 10.dp, top = 20.dp),
                        text = stringResource(id = R.string.developer)
                    )
                    Text(
                        color = Color.White,
                        modifier = modifier.padding(start = 10.dp, top = 10.dp),
                        text = stringResource(id = R.string.email)
                    )
                }
            }
        }
        Text(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            text = stringResource(id = R.string.thanks_you),
            modifier = modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
        Image(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.dicoding),
            contentDescription = null
        )
    }
}

@Composable
fun DetailScreen(
    movieId: Int,
    moviesViewModel: MoviesViewModel = viewModel(
        factory = ViewModelFactory(MovieRepository())
    ),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val movies by moviesViewModel.movies.collectAsState()
    val movie = movies.first() {
        it.id == movieId
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = movie.imgMovie),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(width = 200.dp, height = 300.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.movie_title_label),
                    modifier = modifier.padding(top = 8.dp, start = 8.dp)
                )
                Text(
                    modifier = modifier.padding(8.dp),
                    text = movie.titleMovie
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.movie_released_information_label),
                    modifier = modifier.padding(8.dp)
                )
                Text(
                    modifier = modifier.padding(8.dp),
                    text = movie.dateMovie
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.movie_original_language),
                    modifier = modifier.padding(8.dp)
                )
                Text(
                    modifier = modifier.padding(8.dp),
                    text = movie.langMovie
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.movie_runtime_label),
                    modifier = modifier.padding(8.dp)
                )
                Text(
                    modifier = modifier.padding(8.dp),
                    text = movie.runtimeMovie
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
        ) {
            val context = LocalContext.current
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = null,
                modifier = modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clickable {
                        Toast.makeText(context, context.resources.getString(R.string.coming_soon), Toast.LENGTH_LONG).show()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.instagram),
                contentDescription = null,
                modifier = modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clickable {
                        Toast.makeText(context, context.resources.getString(R.string.coming_soon), Toast.LENGTH_LONG).show()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = null,
                modifier = modifier
                    .padding(8.dp)
                    .size(50.dp)
                    .clickable {
                        Toast.makeText(context, context.resources.getString(R.string.coming_soon), Toast.LENGTH_LONG).show()
                    }
            )
        }
        Text(
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.movie_genres_label),
            modifier = modifier.padding(8.dp)
        )
        Text(
            modifier = modifier.padding(8.dp),
            text = movie.genresMovie
        )
        Text(
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.movie_overview_label),
            modifier = modifier.padding(8.dp)
        )
        Text(
            modifier = modifier.padding(8.dp),
            text = movie.descMovie
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieCatalogueTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPreview() {
    AboutScreen()
}

@Preview(showBackground = true)
@Composable
fun DefaultDetail() {
    val movie = MovieModel(
        1012,
        R.drawable.poster_blackpanter,
        "Pacific Rim: Uprising",
        "March 21, 2018",
        "It has been ten years since The Battle of the Breach and the oceans are still, but restless. Vindicated by the victory at the Breach, the Jaeger program has evolved into the most powerful global defense force in human history. The PPDC now calls upon the best and brightest to rise up and become the next generation of heroes when the Kaiju threat returns.",
        "English",
        "1h 51m",
        "https://www.facebook.com/pacificrimmovie",
        "https://twitter.com/pacificrim",
        "https://www.instagram.com/pacificrimmovie",
        "Action"
    )
    MovieCatalogueTheme {
//        DetailScreen(movie)
    }
}

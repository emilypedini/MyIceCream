package com.example.myicecream.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myicecream.data.database.IceCreamDatabase
import com.example.myicecream.data.database.UserEntity
import com.example.myicecream.data.repositories.LikeRepository
import com.example.myicecream.data.repositories.NotificationRepository
import com.example.myicecream.data.repositories.PostRepository
import com.example.myicecream.data.repositories.UserRepository
import com.example.myicecream.ui.composable.AppTopBar
import com.example.myicecream.ui.composable.ToolBar
import com.example.myicecream.ui.composable.NavBar
import com.example.myicecream.ui.screen.favorite.FavoritePostsScreen
import com.example.myicecream.ui.screen.favorite.FavoritePostsViewModel
import com.example.myicecream.ui.screen.home.HomeScreen
import com.example.myicecream.ui.screen.home.HomeViewModel
import com.example.myicecream.ui.screen.map.MapScreen
import com.example.myicecream.ui.screen.map.MapViewModel
import com.example.myicecream.ui.screen.notifications.NotificationScreen
import com.example.myicecream.ui.screen.notifications.NotificationsViewModel
import com.example.myicecream.ui.screen.posts.CreatePostScreen
import com.example.myicecream.ui.screen.posts.PostViewModel
import com.example.myicecream.ui.screen.profile.ProfileScreen
import com.example.myicecream.ui.screen.profile.ProfileViewModel
import com.example.myicecream.ui.screen.theme.ThemeViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myicecream.ui.screen.posts.PostDetailScreen
import com.example.myicecream.ui.screen.posts.PostDetailViewModel

import com.example.myicecream.utils.location.LocationService

@Composable
fun MainScreen(
    rootNavController: NavController,
    themeViewModel: ThemeViewModel,
    loggedUser: UserEntity
) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val db = remember { IceCreamDatabase.getDatabase(context) }
    val userRepository = remember { UserRepository(db.userDAO()) }

    val profileViewModel = remember {
        ProfileViewModel(userRepository, userId = loggedUser.id)
    }

    val notificationsViewModel = remember {
        NotificationsViewModel(
            notificationRepository = NotificationRepository(db.notificationDAO()),
            userId = loggedUser.id
        )
    }

    val likeRepository = remember {
        LikeRepository(
            likeDAO = db.likeDAO(),
            notificationRepository = NotificationRepository(db.notificationDAO()),
            postDAO = db.postDAO(),
            userDAO = db.userDAO()
        )
    }

    val homeViewModel = remember {
        HomeViewModel(
            postRepository = PostRepository(db.postDAO(), db.notificationDAO()),
            likeRepository = likeRepository,
            likeDAO = db.likeDAO(),
            loggedUserId = loggedUser.id
        )
    }

    val postViewModel = remember {
        PostViewModel(
            postRepository = PostRepository(db.postDAO(), db.notificationDAO()),
            userId = loggedUser.id
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showTopBar = currentRoute in listOf(
        NavBar.Home.route,
        "notifications",
        "favorites"
    )

    val showBottomBar = currentRoute in listOf(
        NavBar.Home.route,
        NavBar.Map.route,
        NavBar.Add.route,
        NavBar.Search.route,
        NavBar.Profile.route
    )

    val favoritePostsViewModel = remember {
        FavoritePostsViewModel(
            likeRepository = likeRepository,
            userId = loggedUser.id
        )
    }

    Scaffold(
        topBar = {
            if(showTopBar) {
                AppTopBar(
                    title = "Nuvole di Gelato",
                    innerNavController = navController
                )
            }
        },
        bottomBar = {
            if(showBottomBar) {
                ToolBar(navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = NavBar.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavBar.Home.route) {
                HomeScreen(
                    navController = rootNavController,
                    homeViewModel = homeViewModel
                )
            }

            composable(NavBar.Map.route) {
                val context = LocalContext.current
                val locationService = remember { LocationService(context) }
                val mapViewModel = remember { MapViewModel(locationService) }

                MapScreen(viewModel = mapViewModel)
            }


            composable(NavBar.Profile.route) {
                ProfileScreen(
                    navController = rootNavController,
                    viewModel = profileViewModel
                )
            }

            composable(NavBar.Add.route) {
                CreatePostScreen(
                    navController = rootNavController,
                    postViewModel = postViewModel
                )
            }

            composable("notifications") {
                NotificationScreen(
                    viewModel = notificationsViewModel
                )
            }

            composable("favorites") {
                FavoritePostsScreen(
                    navController = navController,
                    favoritePostsViewModel = favoritePostsViewModel
                )
            }

            composable(
                route = "postDetail/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.IntType })
            ) { backStackEntry ->

                val postId = backStackEntry.arguments!!.getInt("postId")

                val postDetailViewModel = remember {
                    PostDetailViewModel(
                        postRepository = PostRepository(db.postDAO(), db.notificationDAO()),
                        likeRepository = likeRepository,
                        loggedUserId = loggedUser.id,
                        postId = postId
                    )
                }

                PostDetailScreen(postDetailViewModel)
            }
        }
    }
}


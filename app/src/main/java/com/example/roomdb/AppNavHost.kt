package com.example.roomdb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roomdb.repository.Repository
import com.example.roomdb.room.StudentsDB
import com.example.roomdb.viewmodel.StudentViewModel
import kotlinx.coroutines.delay

enum class ROUTE_NAME_SCREEN {
    Splash,
    Main,
    Detail
}

@Composable
fun SplashScreen(navController: NavController) {
    BoardingScreen(navController)

    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(ROUTE_NAME_SCREEN.Main.name) {
            popUpTo(ROUTE_NAME_SCREEN.Splash.name) { inclusive = true }
        }
    }
}

@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val db = StudentsDB.getIntance(context)
    val repository = Repository(db)
    val myViewModel = StudentViewModel(repository)
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ROUTE_NAME_SCREEN.Splash.name) {
        composable(ROUTE_NAME_SCREEN.Splash.name) {
            SplashScreen(navController)
        }
        composable(ROUTE_NAME_SCREEN.Main.name) {
            MainScreen(viewModel = myViewModel, navController)
        }
        composable("${ROUTE_NAME_SCREEN.Detail.name}/{studentId}/{hoTen}/{mssv}/{img}/{diemTB}/{daRaTruong}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")
            val hoTen = backStackEntry.arguments?.getString("hoTen") ?: ""
            val mssv = backStackEntry.arguments?.getString("mssv") ?: ""
            val img = backStackEntry.arguments?.getString("img") ?: ""
            val diemTB = backStackEntry.arguments?.getString("diemTB") ?: ""
            val daRaTruong = backStackEntry.arguments?.getString("daRaTruong") ?: ""

            DetailScreen(
                navController = navController,
                viewModel = myViewModel,
                studentId = studentId,
                hoTen = hoTen,
                mssv = mssv,
                img = img,
                diemTB = diemTB,
                daRaTruong = daRaTruong
            )
        }
    }
}

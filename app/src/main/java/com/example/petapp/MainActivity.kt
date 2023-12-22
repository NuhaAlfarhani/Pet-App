package com.example.petapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petapp.Page.Account
import com.example.petapp.Page.Artikel.Artikel
import com.example.petapp.Page.Artikel.DetailArtikel
import com.example.petapp.Page.Artikel.Konsultasi
import com.example.petapp.Page.Login
import com.example.petapp.Page.Register
import com.example.petapp.Page.OnBoarding1
import com.example.petapp.Page.OnBoarding2
import com.example.petapp.Page.OnBoarding3
import com.example.petapp.Page.HomePage
import com.example.petapp.Page.Penitipan.Penitipan
import com.example.petapp.Page.Profil.Profil
import com.example.petapp.response.Artikel
import com.example.petapp.response.ArtikelRespon

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val preferencesManager = remember { PreferencesManager(context = LocalContext.current) }
            val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
            val navController = rememberNavController()

            var startDestination: String
            var jwt = sharedPreferences.getString("jwt", "")
            if(jwt.equals("")){
                startDestination = "ob1"
            }else{
                startDestination = "homepage"
            }

            NavHost(navController, startDestination = startDestination) {
                composable(route = "ob1") {
                    OnBoarding1(navController)
                }
                composable(route = "ob2") {
                    OnBoarding2(navController)
                }
                composable(route = "ob3") {
                    OnBoarding3(navController)
                }
                composable(route = "login") {
                    Login(navController)
                }
                composable(route = "homepage") {
                    HomePage(navController)
                }
                composable(route = "register") {
                    Register(navController)
                }
                composable(route = "artikel") {
                    Artikel(navController)
                }
                composable("detailartikel/{artikelId}/{judul_artikel}/{deskripsi_artikel}/{tgl_publikasi}") { backStackEntry ->
                    DetailArtikel(navController,
                        backStackEntry.arguments?.getString("artikelId"),
                        backStackEntry.arguments?.getString("judul_artikel"),
                        backStackEntry.arguments?.getString("deskripsi_artikel"),
                        backStackEntry.arguments?.getString("tgl_publikasi"),
                    )
                }
                composable(route = "konsultasi") {
                    Konsultasi(navController)
                }
                composable(route = "penitipan") {
                    Penitipan(navController)
                }
                composable(route = "profil") {
                    Profil(navController)
                }
                composable(route = "account") {
                    Account(navController)
                }
//                composable(
//                    route = "account/{userid}/{nama_lengkap}/{username}/{email}/{password}/{no_hp}/{alamat}",
//                ) {backStackEntry ->
//                    Account(navController,
//                        backStackEntry.arguments?.getString("userid"),
//                        backStackEntry.arguments?.getString("nama_lengkap"),
//                        backStackEntry.arguments?.getString("username"),
//                        backStackEntry.arguments?.getString("email"),
//                        backStackEntry.arguments?.getString("password"),
//                        backStackEntry.arguments?.getString("no_hp"),
//                        backStackEntry.arguments?.getString("alamat"),
//                        )
//                }
            }
        }
    }
}
@Composable
fun BottomNavigation(navController: NavController) {
    NavigationBar {
        val bottomNavigation = listOf(
            BottomNavItem(
                label = "Home",
                icon = Icons.Default.Home,
                route = "homepage"
            ),
            BottomNavItem(
                label = "Pesanan",
                icon = Icons.Default.ShoppingCart,
                route = "pesanan"
            ),
            BottomNavItem(
                label = "Chat",
                icon = Icons.Default.Email,
                route = "konsultasi"
            ),
            BottomNavItem(
                label = "Profile",
                icon = Icons.Default.AccountCircle,
                route = "profil"
            )
        )
        bottomNavigation.map {
            NavigationBarItem(
                selected = navController.currentDestination?.route == it.route,
                onClick = { navController.navigate(it.route) },
                icon = { Icon(imageVector = it.icon, contentDescription = it.label) },
                label = {Text(text = it.label)},
            )
        }
    }
}
data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)






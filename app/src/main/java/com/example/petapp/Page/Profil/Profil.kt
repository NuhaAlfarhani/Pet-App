package com.example.petapp.Page.Profil

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.BottomNavigation
import com.example.petapp.PreferencesManager
import com.example.petapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profil(navController: NavController, context: Context = LocalContext.current) {
    val baseColor = Color(0xFF00676C)
    val profil = painterResource(id = R.drawable.chat1)
    val preferencesManager = remember { PreferencesManager(context = context) }
//    val sharedPreferences: SharedPreferences = LocalContext.current.getSharedPreferences("auth", Context.MODE_PRIVATE)
    var nama = preferencesManager.getData("namaUser")
    var email = preferencesManager.getData("email")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile", modifier = Modifier.padding(top = 5.dp), fontWeight = FontWeight.Bold, fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseColor,
                    titleContentColor = Color.White,
                ),
            )
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .background(baseColor, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(14.dp)
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        painter = profil,
                        contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .clip(CircleShape)
                    )
                    
                    Column (
                        modifier = Modifier.padding(start = 18.dp)
                    ){
                        Text(text = nama,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            color = Color.White,
                            fontSize = 14.sp)
                        Text(text = email,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color.White,
                            fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(12.dp)
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navController.navigate("account")
                    }
                ) {
                    IconButton(onClick = {
                        navController.navigate("account")
                    }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Account",
                            tint = Color.Black
                        )
                    }

                    Text(
                        text = "My Account",
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = Color.Black,
                        fontSize = 14.sp
                    )

                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 167.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(12.dp)
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        preferencesManager.saveData("jwt", "")
                        preferencesManager.saveData("username", "")
                        preferencesManager.saveData("email", "")
                        preferencesManager.saveData("namaUser", "")
                        preferencesManager.saveData("noHp", "")
                        preferencesManager.saveData("alamat", "")
                        preferencesManager.saveData("peran", "")
                        navController.navigate("login")
                    }
                ) {
                    IconButton(onClick = {
                        preferencesManager.saveData("jwt", "")
                        preferencesManager.saveData("username", "")
                        preferencesManager.saveData("email", "")
                        preferencesManager.saveData("namaUser", "")
                        preferencesManager.saveData("noHp", "")
                        preferencesManager.saveData("alamat", "")
                        preferencesManager.saveData("peran", "")
                        navController.navigate("login")
                    }) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Sign Out",
                            tint = Color.Black
                        )
                    }

                    Text(
                        text = "Sign Out",
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        color = Color.Black,
                        fontSize = 14.sp
                    )

                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = "Account",
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 200.dp)
                    )

                }
            }
        }
    }
}

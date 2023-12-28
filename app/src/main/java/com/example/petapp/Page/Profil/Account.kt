package com.example.petapp.Page

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.PreferencesManager
import com.example.petapp.R
import com.example.petapp.data.RegisterData
import com.example.petapp.response.LoginRespon
import com.example.petapp.service.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(navController: NavController, context: Context = LocalContext.current){
    val baseColor = Color(0xFF00676C)
    val preferencesManager = remember { PreferencesManager(context = context) }
    var nama = preferencesManager.getData("namaUser")
    var email = preferencesManager.getData("email")
    var nohp = preferencesManager.getData("noHp")
    var alamat = preferencesManager.getData("alamat")
    var username = preferencesManager.getData("username")

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("profil") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(text = "My Account", fontWeight = FontWeight.Bold, fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)))
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseColor,
                    titleContentColor = Color.White,
                ),
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { newText ->
                    nama = newText
                },
                label = { Text(text = "Nama Lengkap", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Nama",
                    )
                }
            )

            OutlinedTextField(
                value = username,
                onValueChange = { newText ->
                    username = newText
                },
                label = { Text(text = "Username", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.AccountCircle,
                        contentDescription = "Username",
                    )
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { newText ->
                    email = newText
                },
                label = { Text(text = "Email", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email",
                    )
                }
            )

            OutlinedTextField(
                value = nohp,
                onValueChange = { newText ->
                    nohp = newText
                },
                label = { Text(text = "Nomor Telepon", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Nohp",
                    )
                }
            )

            OutlinedTextField(
                value = alamat,
                onValueChange = { newText ->
                    alamat = newText
                },
                label = { Text(text = "Alamat", fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    modifier = Modifier.padding(top = 8.dp)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp)
                    .height(80.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = "Alamat",
                    )
                }
            )

            ElevatedButton(onClick = {

            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = baseColor,
                    contentColor = Color.White),
            ) {
                Text(text = "Edit Account", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.poppins_medium)) )
            }
        }
    }
}
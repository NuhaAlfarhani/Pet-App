package com.example.petapp.Page

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Lock
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
import com.example.petapp.data.LoginData
import com.example.petapp.data.UserRole
import com.example.petapp.response.LoginRespon
import com.example.petapp.service.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, context: Context = LocalContext.current) {

    val baseColor = Color(0xFF00676C)
    val imagePainter = painterResource(id = R.drawable.my_image)
    val preferencesManager = remember { PreferencesManager(context = context) }
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val eyeOpen = painterResource(id = R.drawable.view)
    val eyeClose = painterResource(id = R.drawable.hidden)
    var passwordVisibility by remember { mutableStateOf(false) }
    var baseUrl = "http://10.0.2.2:1337/api/"
    //var baseUrl = "http://10.217.17.11:1337/api/"
    var jwt by remember { mutableStateOf("") }
    var namaUser by remember { mutableStateOf("") }
    var usernameUser by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var noHp by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    jwt = preferencesManager.getData("jwt")

    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Sign In", fontWeight = FontWeight.Bold, fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold))) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
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
            Image(
                painter = imagePainter,
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .height(350.dp)
                    .width(250.dp)
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
                        contentDescription = null,
                    )
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { newText ->
                    password = newText
                },
                label = { Text(text = "Password", fontFamily = FontFamily(Font(R.font.poppins_regular))) },
                visualTransformation =
                if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 38.dp),
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        val icon = if (passwordVisibility) eyeOpen else eyeClose
                        Image(
                            painter = icon,
                            contentDescription = if (passwordVisibility) "Hide Password" else "Show Password",
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }
            )

            ElevatedButton(onClick = {
                //navController.navigate("pagetwo")
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(LoginService::class.java)
                val call = retrofit.getData(LoginData(username.text, password.text))
                call.enqueue(object : Callback<LoginRespon> {
                    override fun onResponse(call: Call<LoginRespon>, response: Response<LoginRespon>) {
//                        print(response.code())
                        if(response.code() == 200){
                            val body = response.body()
                            val role = body?.user?.peran?: UserRole.User
//                            println(body)

                            jwt = body?.jwt ?: ""
                            preferencesManager.saveData("jwt", jwt)

                            usernameUser = body?.user?.username.orEmpty()
                            email = body?.user?.email.orEmpty()
                            namaUser = body?.user?.namaLengkap.orEmpty()
                            noHp = body?.user?.noHp.orEmpty()
                            alamat = body?.user?.alamat.orEmpty()

                            preferencesManager.saveData("username", usernameUser)
                            preferencesManager.saveData("email", email)
                            preferencesManager.saveData("namaUser", namaUser)
                            preferencesManager.saveData("noHp", noHp)
                            preferencesManager.saveData("alamat", alamat)

                            when (role) {
                                UserRole.Toko -> navController.navigate("homepagetoko")
                                else -> navController.navigate("homepage")
                            }
                        }else if(response.code() == 400){
                            print("error login")
                            var toast = Toast.makeText(context, "Username atau password salah", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<LoginRespon>, t: Throwable) {
                        print(t.message)
                    }
                })
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = baseColor,
                    contentColor = Color.White),
            ) {
                Text(text = "Sign In", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = FontFamily(Font(R.font.poppins_medium)) )
            }
//            Text(text = jwt)

            Row {
                Text(text = "Don't have an account ? ", fontFamily = FontFamily(Font(R.font.poppins_regular)),color = Color.Gray)
                ClickableText(text = AnnotatedString("Sign Up"), onClick = {navController.navigate("register")})
            }
        }
    }

}
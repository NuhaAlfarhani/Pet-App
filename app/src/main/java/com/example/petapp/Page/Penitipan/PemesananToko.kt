package com.example.petapp.Page.Penitipan

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.BottomNavigation
import com.example.petapp.R
import com.example.petapp.data.UserRole
import com.example.petapp.response.Artikel
import com.example.petapp.response.ArtikelRespon
import com.example.petapp.response.Pemesanan
import com.example.petapp.response.PemesananRespon
import com.example.petapp.response.PenitipanRespon
import com.example.petapp.response.Produk
import com.example.petapp.response.ProdukRespon
import com.example.petapp.response.UserRespon
import com.example.petapp.service.ArtikelService
import com.example.petapp.service.PemesananService
import com.example.petapp.service.PenitipanService
import com.example.petapp.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemesananToko(navController: NavController, context: Context = LocalContext.current){
    val baseColor = Color(0xFF00676C)
    val profil = painterResource(id = R.drawable.pppenitipan)
    var search by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val listPemesanan = remember { mutableStateListOf<PemesananRespon>() }
    //var listUser: List<UserRespon> by remember { mutableStateOf(List<UserRespon>()) }
    var baseUrl = "http://10.0.2.2:1337/api/"
    //var baseUrl = "http://10.217.17.11:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PemesananService::class.java)
    val call = retrofit.getData("*")
    call.enqueue(object : Callback<Pemesanan<List<PemesananRespon>>> {
        override fun onResponse(
            call: Call<Pemesanan<List<PemesananRespon>>>,
            response: Response<Pemesanan<List<PemesananRespon>>>
        ) {
            if (response.isSuccessful) {
                listPemesanan.clear()
                response.body()?.data!!.forEach{ pemesananRespon : PemesananRespon ->
                    listPemesanan.add(pemesananRespon)
                }
            } else {
                print("Error getting data. Code: ${response.code()}")
                Toast.makeText(
                    context,
                    "Error getting data. Code: ${response.code()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun onFailure(call: Call<Pemesanan<List<PemesananRespon>>>, t: Throwable) {
            print(t.message)
            Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pemesanan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.poppins_semibold
                            )
                        )
                    )
                }
            },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseColor,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = search, onValueChange = { newText ->
                search = newText
            },
                label = {
                    Text(text = "Search",
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        // Handle the search action
                    }) {
                        Icon(Icons.Default.Search,
                            contentDescription = "Search",
                            tint = baseColor
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = baseColor,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = baseColor
                )
            )

            LazyColumn {
                listPemesanan?.forEach { pemesanan ->
                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable { navController.navigate("penitipandetail") }
                        ) {
                            Column (
                                modifier = Modifier.padding(start = 16.dp)
                            ){
                                Text(
                                    text = "Nama Pemesan : " + pemesanan.attributes.namaPemesan,
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                    fontSize = 16.sp,
                                )

                                Text(
                                    text = "Layanan : " + pemesanan.attributes.produks?.data?.attributes?.namaProduk,
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )

                                Text(
                                    text = "Rp " + pemesanan.attributes.total.toString(),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )

                                Text(
                                    text = "Jam : " + pemesanan.attributes.jamPemesan,
                                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                )
                            }
                        }


                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }

    }
}
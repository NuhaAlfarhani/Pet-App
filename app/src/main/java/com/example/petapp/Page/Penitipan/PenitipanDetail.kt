package com.example.petapp.Page.Penitipan

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.PreferencesManager
import com.example.petapp.R
import com.example.petapp.response.Produk
import com.example.petapp.response.ProdukRespon
import com.example.petapp.service.ProdukService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenitipanDetail(navController: NavController, context: Context = LocalContext.current) {
    val baseColor = Color(0xFF00676C)
    //var listUser: List<UserRespon> = remember
    var search by remember { mutableStateOf(TextFieldValue("")) }
    val preferencesManager = remember { PreferencesManager(context = context) }

    val listProduk = remember { mutableStateListOf<ProdukRespon>() }
    var baseUrl = "http://10.0.2.2:1337/api/"
    //var baseUrl = "http://10.217.17.11:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProdukService::class.java)
    val call = retrofit.getData()
    call.enqueue(object : Callback<Produk<List<ProdukRespon>>> {
        override fun onResponse(
            call: Call<Produk<List<ProdukRespon>>>,
            response: Response<Produk<List<ProdukRespon>>>
        ) {
            if (response.isSuccessful) {
                listProduk.clear()
                response.body()?.data!!.forEach{ produkRespon : ProdukRespon->
                    listProduk.add(produkRespon)
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

        override fun onFailure(call: Call<Produk<List<ProdukRespon>>>, t: Throwable) {
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
                    IconButton(onClick = { navController.navigate("penitipan") }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Penitipan Hewan",
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

            Text(
                text = "Daftar list Treatment dan Harga",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            LazyColumn {
                listProduk?.forEach { produk ->
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 18.dp, bottom = 18.dp)
                        ) {

                            Column {
                                Text(
                                    text = produk.attributes.namaProduk,
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                                )

                                Text(
                                    text = "Rp " + produk.attributes.harga.toString(),
                                    fontFamily = FontFamily(Font(R.font.poppins_regular))
                                )
                            }

                            Column(modifier = Modifier.padding(top = 5.dp)) {
                                Row {
                                    IconButton(onClick = {
                                        navController.navigate("pemesanan/"
                                                + produk.id
                                                + "/" + produk.attributes.namaProduk
                                                + "/" + produk.attributes.harga
                                                + "/" + produk.attributes.deskripsiProduk)                                    }) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Booking",
                                            tint = baseColor
                                        )
                                    }
                                }
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

package com.example.petapp.Page.Produk

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.PreferencesManager
import com.example.petapp.data.ProdukData
import com.example.petapp.data.ProdukDataWrapper
import com.example.petapp.response.ProdukRespon
import com.example.petapp.service.ProdukService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduk(navController: NavController,  produkid : String?, namaParameter: String?, hargaParameter: String?, deskripsiParameter: String?, context: Context = LocalContext.current) {
    val preferencesManager = remember { PreferencesManager(context = context) }
    val baseColor = Color(0xFF00676C)
    var namaProduk by remember { mutableStateOf(namaParameter?: "") }
    var harga by remember { mutableStateOf(hargaParameter?: "") }
    var deskrispiProduk by remember { mutableStateOf(deskripsiParameter?: "") }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("homepagetoko") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(text = "Edit Produk", fontWeight = FontWeight.Bold, fontSize = 28.sp)
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
                value = namaProduk,
                onValueChange = { newText ->
                    namaProduk = newText
                },
                label = { Text(text = "Nama Produk") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )

            OutlinedTextField(
                value = harga,
                onValueChange = { newText ->
                    harga = newText
                },
                label = { Text(text = "Harga") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
            )

            OutlinedTextField(
                value = deskrispiProduk,
                onValueChange = { newText ->
                    deskrispiProduk = newText
                },
                label = { Text(text = "Deskripsi Produk") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 38.dp),
            )

            ElevatedButton(onClick = {
                var baseUrl = "http://10.0.2.2:1337/api/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ProdukService::class.java)
                val produkData = ProdukDataWrapper(ProdukData(namaProduk, harga.toInt(), deskrispiProduk))
                val call = retrofit.save(produkid, produkData)
                call.enqueue(object : Callback<ProdukRespon> {
                    override fun onResponse(
                        call: Call<ProdukRespon>,
                        response: Response<ProdukRespon>
                    ) {
                        print(response.code())
                        if (response.isSuccessful) {
                            navController.navigate("homepagetoko")
                        } else {
                            print("error update")
                            var toast = Toast.makeText(
                                context,
                                "Error updating: ${response.errorBody()?.string()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ProdukRespon>, t: Throwable) {
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
                Text(text = "Edit", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp )
            }
        }
    }

}


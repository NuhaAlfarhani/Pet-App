package com.example.petapp.Page.Penitipan

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import com.example.petapp.data.PemesananData
import com.example.petapp.data.PemesananDataWrapper
import com.example.petapp.response.PemesananRespon
import com.example.petapp.service.PemesananService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenitipanPemesanan(navController: NavController,  produkid : String?, namaParameter: String?, hargaParameter: String?, deskripsiParameter: String?, context: Context = LocalContext.current) {
    val preferencesManager = remember { PreferencesManager(context = context) }
    val baseColor = Color(0xFF00676C)
    var namaProduk by remember { mutableStateOf(namaParameter?: "") }
    var harga by remember { mutableStateOf(hargaParameter?: "") }
    var deskrispiProduk by remember { mutableStateOf(deskripsiParameter?: "") }

    val mCalendar = Calendar.getInstance()

    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]

    var jamPemesanan by remember { mutableStateOf("Select Time") }
    var jamPemesanan2 by remember { mutableStateOf("Select Time") }

    val mTimePickerDialog = TimePickerDialog(
        context,
        {_, mHour : Int, mMinute: Int ->
            jamPemesanan = String.format("%02d:%02d:00.000", mHour, mMinute)
            jamPemesanan2 = String.format("%02d:%02d", mHour, mMinute)
        }, mHour, mMinute, true
    )

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("penitipandetail") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(text = "Pemesanan", fontWeight = FontWeight.Bold, fontSize = 28.sp)
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
                    .padding(bottom = 6.dp),
                enabled = false,
            )


            OutlinedTextField(
                value = deskrispiProduk,
                onValueChange = { newText ->
                    deskrispiProduk = newText
                },
                label = { Text(text = "Deskripsi Produk") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                enabled = false,
            )

            OutlinedTextField(
                value = harga,
                onValueChange = { newText ->
                    harga = newText
                },
                label = { Text(text = "Total") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
            )

            OutlinedTextField(
                value = jamPemesanan2,
                onValueChange = {
                    jamPemesanan2 = it
                },
                label = { Text(text = "Jam") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 36.dp),

                trailingIcon = {
                    IconButton(onClick = { mTimePickerDialog.show()  }) {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = "Time Picker",
                            tint = baseColor
                        )
                    }
                }
            )

            ElevatedButton(onClick = {
                var baseUrl = "http://10.0.2.2:1337/api/"
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PemesananService::class.java)
                var nama = preferencesManager.getData("namaUser")
                val pemesananData = PemesananDataWrapper(
                    PemesananData(
                        nama,
                        harga.toInt(),
                        jamPemesanan,
                        produkid!!.toInt()
                    )
                )
                val call = retrofit.createPemesanan(pemesananData)
                call.enqueue(object : Callback<PemesananRespon> {
                    override fun onResponse(
                        call: Call<PemesananRespon>,
                        response: Response<PemesananRespon>
                    ) {
                        print(response.code())
                        if (response.isSuccessful) {
                            val resp = response.body()
                            navController.navigate("pemesanan")
                        } else {
                            print("error create")
                            var toast = Toast.makeText(
                                context,
                                "Error creating: ${response.errorBody()?.string()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<PemesananRespon>, t: Throwable) {
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
                Text(text = "Pesan", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp )
            }
        }
    }

}


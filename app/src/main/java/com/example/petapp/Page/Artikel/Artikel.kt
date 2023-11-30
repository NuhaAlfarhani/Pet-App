package com.example.petapp.Page.Artikel

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.response.ArtikelRespon
import com.example.petapp.service.ArtikelService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Artikel(navController: NavController, context: Context = LocalContext.current) {
    val baseColor = Color(0xFF00676C)
    val artikel1 = painterResource(id = R.drawable.artikel1)
    val artikel2 = painterResource(id = R.drawable.artikel2)
    var search by remember { mutableStateOf(TextFieldValue("")) }

    val listArtikel = remember { mutableStateListOf<ArtikelRespon>() }
    var baseUrl = "http://10.0.2.2:1337/api/"
    //var baseUrl = "http://10.217.17.11:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ArtikelService::class.java)
    val call = retrofit.getData()
    call.enqueue(object : Callback<List<ArtikelRespon>> {
        override fun onResponse(
            call: Call<List<ArtikelRespon>>,
            response: Response<List<ArtikelRespon>>
        ) {
            if (response.isSuccessful) {
                listArtikel.clear()
                response.body()?.forEach { artikelRespon ->
                    listArtikel.add(artikelRespon)
                }
            } else {
                val message = response.errorBody()?.string() ?: "Unknown error"
                print("Error: $message")

                Toast.makeText(context, "Terjadi kesalahan: $message", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<List<ArtikelRespon>>, t: Throwable) {
            print(t.message)
        }

    })


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("homepage") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "Artikel", fontWeight = FontWeight.Bold, fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold))
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseColor,
                    titleContentColor = Color.White,
                ),
            )
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

            OutlinedTextField(
                value = search,
                onValueChange = { newText ->
                    search = newText
                },
                label = {
                    Text(
                        text = "Search",
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        // Handle the search action
                    }) {
                        Icon(
                            Icons.Default.Search,
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
                listArtikel.forEach { artikel ->
                    item {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("detailartikel") }
                        ) {
                            Box {
                                Image(
                                    painter = artikel1,
                                    contentDescription = null,
                                    alignment = Alignment.Center,
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(100.dp)
                                        .padding(end = 12.dp)
                                        .clickable { navController.navigate("detailartikel") }
                                )
                            }
                            Text(
                                text = artikel.judul_artikel,
                                fontFamily = FontFamily(Font(R.font.poppins_medium))
                            )
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


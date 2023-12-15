package com.example.petapp.Page.Penitipan

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.response.PenitipanRespon
import com.example.petapp.service.PenitipanService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenitipanDetil(navController: NavController, context: Context = LocalContext.current){
    val baseColor = Color(0xFF00676C)
    val artikel1 = painterResource(id = R.drawable.artikel1)
    val toko = painterResource(id = R.drawable.toko)
    var search by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val listProduk = remember {
        mutableStateListOf<PenitipanRespon>()
    }
    var baseUrl = "http://10.0.2.2:1337/api/"
//    var baseUrl =  "http://10.217.17.11:1337/api/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PenitipanService::class.java)
    val call = retrofit.getData()
    call.enqueue(object : Callback<List<PenitipanRespon>>{
        override fun onResponse(
            call: Call<List<PenitipanRespon>>,
            response: Response<List<PenitipanRespon>>
        ) {
            if(response.isSuccessful){
                listProduk.clear()
                response.body()?.forEach{penitipanRespon -> listProduk.add(penitipanRespon) }
            } else{
                val message = response.errorBody()?.string()?: "Unknown error"
                print("Error: $message")

                Toast.makeText(context, "Terjadi Kesalahan: $message", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<List<PenitipanRespon>>, t: Throwable) {
            print(t.message)
        }
    })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row {
                    IconButton(onClick = { navController.navigate("homepage") }) {
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
                        fontFamily = FontFamily(Font(R.font.poppins_semibold))
                    )
                }
            },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = baseColor,
                    titleContentColor = Color.White
                )
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
                onValueChange = { newText -> search = newText },
                label = { Text(
                    text = "Search",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)))
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                trailingIcon = {
                    IconButton(onClick = {
                    // Handle Search Action
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box {
                        Image(
                            painter = toko,
                            contentDescription = null,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)

                        )
                    }
                }
            }

//            LazyColumn(
//                modifier = Modifier.weight(1f)
//            ){
//                listProduk.forEach{produk ->
//                    item {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp)
//                        ) {
//                            Box(
//                                modifier = Modifier
//                                    .size(100.dp)
//                                    .clip(MaterialTheme.shapes.medium)
//                            ) {
//                                Image(painter = artikel1,
//                                    contentDescription = null,
//                                    alignment = Alignment.Center,
//                                    modifier = Modifier
//                                        .height(100.dp)
//                                        .width(100.dp)
//                                        .padding(end = 12.dp)
//                                )
//                            }
//                            Spacer(modifier = Modifier.width(16.dp))
//                            Text(
//                                text = produk.nama_penitipan,
//                                fontFamily = FontFamily(Font(R.font.poppins_medium))
//                            )
//                        }
//
//                        Divider(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(1.dp)
//                                .background(Color.Gray)
//                        )
//                    }
//                }
//            }
        }
    }
}

@Preview
@Composable
fun PenitipanDetilPreview(){
    PenitipanDetil(navController = NavController(context = LocalContext.current))
}
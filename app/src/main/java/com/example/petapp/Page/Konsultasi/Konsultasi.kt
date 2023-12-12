package com.example.petapp.Page.Artikel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petapp.BottomNavigation
import com.example.petapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Konsultasi(navController: NavController) {
    val baseColor = Color(0xFF00676C)
    val chat1 = painterResource(id = R.drawable.chat1)

    var search by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigate("homepage") }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Text(text = "Chat", fontWeight = FontWeight.Bold, fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)))
                    }
                },
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

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ){
                Column {
                    Box{
                        Image(
                            painter = chat1,
                            contentDescription = null,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .height(70.dp)
                                .width(70.dp)
                        )
                    }
                }

                Column{
                    Text(text = "Dr. Adi Pramono", fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins_semibold)))

                    Text(text = "Dokter Hewan", fontFamily = FontFamily(Font(R.font.poppins_medium)))
                }

                Column(modifier = Modifier.padding(top = 25.dp)) {
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = baseColor,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Chat", fontFamily = FontFamily(Font(R.font.poppins_medium)))
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
            ){
                Column {
                    Box{
                        Image(
                            painter = chat1,
                            contentDescription = null,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .height(70.dp)
                                .width(70.dp)
                        )
                    }
                }

                Column {
                    Text(text = "Dr. Mawar Hitam", fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins_semibold)))

                    Text(text = "Dokter Hewan", fontFamily = FontFamily(Font(R.font.poppins_medium)))
                }

                Column(modifier = Modifier.padding(top = 25.dp)) {
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = baseColor,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(text = "Chat", fontFamily = FontFamily(Font(R.font.poppins_medium)))
                    }
                }
            }
        }
    }
}

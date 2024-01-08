package com.example.petapp.Page

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.petapp.PreferencesManager
import com.example.petapp.R
import com.example.petapp.data.RegisterData
import com.example.petapp.data.UpdateData
import com.example.petapp.response.LoginRespon
import com.example.petapp.response.UserRespon
import com.example.petapp.service.ImgService
import com.example.petapp.service.UploadResponseList
import com.example.petapp.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Account(navController: NavController,  context: Context = LocalContext.current){
    val baseColor = Color(0xFF00676C)
    val baseUrl = "http://10.0.2.2:1337/api/"
    val preferencesManager = remember { PreferencesManager(context = context) }
    var nama = preferencesManager.getData("namaUser")
    var email = preferencesManager.getData("email")
    var nohp = preferencesManager.getData("noHp")
    var alamat = preferencesManager.getData("alamat")
    var username = preferencesManager.getData("username")
    val currentValue = preferencesManager.getData("img") ?: ""
    val newUrl = currentValue.replace("::uploads::", "/uploads/")

    var selectedImageFile by remember { mutableStateOf<File?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val resolver = context.contentResolver
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                resolver.openInputStream(selectedImageUri!!)?.let { inputStream ->
                    val originalFileName = context.contentResolver.query(
                        selectedImageUri!!, null, null, null, null
                    )?.use { cursor ->
                        if (cursor.moveToFirst()) {
                            val displayNameIndex =
                                cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            if (displayNameIndex != -1) {
                                cursor.getString(displayNameIndex)
                            } else {
                                null
                            }
                        } else {
                            null
                        }
                    }
                    val file = File(context.cacheDir, originalFileName ?: "temp_img.jpg")
                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING)
                    selectedImageFile = file
                }
            }
        })

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

            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {

                Column(modifier = Modifier
                    .size(200.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                )
                {
                    Box(modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(1000.dp))
                        .clickable { pickImageLauncher.launch("image/*") }
                        , contentAlignment = Alignment.Center) {
                        if (selectedImageUri != null) {
                            Image(
                                painter = rememberImagePainter(data = selectedImageUri, builder = {
                                    transformations(CircleCropTransformation())
                                }),
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(8.dp))
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter("http://10.0.2.2:1337$newUrl"),
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .size(200.dp)
                                    .clip(shape = RoundedCornerShape(8.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (selectedImageUri != null) {
                        IconButton(
                            onClick = { selectedImageUri = null }, modifier = Modifier.size(48.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Image")
                        }
                    }
                }
            }

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
//                val baseUrl = "http://10.0.2.2:1337/api/"
//                val retrofit = Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(UserService::class.java)
//                val call = retrofit.save(UpdateData(nama, email, username, nohp, alamat, peran="User"))
//                call.enqueue(object : Callback<LoginRespon> {
//                    override fun onResponse(
//                        call: Call<LoginRespon>,
//                        response: Response<LoginRespon>
//                    ) {
//                        if (response.code() == 200) {
//                            preferencesManager.saveData(nama),
//                            preferencesManager.saveData(email)
//                            preferencesManager.saveData(username)
//                            preferencesManager.saveData(nohp)
//                            preferencesManager.saveData(alamat)
//                            preferencesManager.saveData("birth", mDate)
//                            val file = selectedImageFile
//                            val mimeType =
//                                MimeTypeMap.getSingleton().getMimeTypeFromExtension(
//                                    file!!.extension
//                                )
//                            val refRequestBody =s
//                                "plugin::users-permissions.user".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                            val refIdRequestBody = iduser
//                                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                            val fieldRequestBody =
//                                "img".toRequestBody("multipart/form-data".toMediaTypeOrNull())
//                            val fileRequestBody = MultipartBody.Part.createFormData(
//                                "files",
//                                file.name,
//                                file.asRequestBody(mimeType?.toMediaTypeOrNull())
//                            )
//
//                            val retrofit23 = Retrofit.Builder().baseUrl("http://10.0.2.2:1337/api/")
//                                .addConverterFactory(GsonConverterFactory.create()).client(
//                                    OkHttpClient.Builder().addInterceptor(
//                                        HttpLoggingInterceptor().setLevel(
//                                            HttpLoggingInterceptor.Level.BODY
//                                        )
//                                    ).build()
//                                )
//                                .build().create(ImgService::class.java)
//                            val call23 = retrofit23.uploadImage(
//                                refRequestBody,
//                                refIdRequestBody,
//                                fieldRequestBody,
//                                fileRequestBody
//                            )
//                            call23.enqueue(object : Callback<UploadResponseList> {
//                                override fun onResponse(
//                                    call23: Call<UploadResponseList>,
//                                    response23: Response<UploadResponseList>
//                                ) {
//                                    if (response23.isSuccessful) {
//                                        navController.navigate("profil")
//                                    } else {
//                                        Toast.makeText(
//                                            context,
//                                            "Error: ${response23.code()} - ${response23.message()}",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                                override fun onFailure(
//                                    call12: Call<UploadResponseList>, t: Throwable
//                                ) {
//                                    Toast.makeText(
//                                        context,
//                                        "Error: " + t.message,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            })
//                        } else if (response.code() == 400) {
//                            var toast = Toast.makeText(
//                                context,
//                                "Kolom Harus Terisi Semua",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<LoginRespon>, t: Throwable) {
//                        print(t.message)
//                    }
//
//                })
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
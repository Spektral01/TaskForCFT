@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.taskforcft.compose_class

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskforcft.MainActivity
import com.example.taskforcft.MainScreen
import com.example.taskforcft.ui.theme.DateTransformation
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainActivityCompose {

    var dateGlobal: String = ""

    @Composable
    fun Greeting( context: Context) {
        val btnColor = remember {
            mutableStateOf(Color.Blue)
        }
        var name by remember { mutableStateOf("") }
        var surname by remember { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var confPassword by rememberSaveable { mutableStateOf("") }

        var nameHint by remember { mutableStateOf(false) }
        var passMismatch by remember { mutableStateOf(false) }
        var passHint by remember { mutableStateOf(false) }
        var fieldIsEmpty by remember { mutableStateOf(false) }


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (nameHint) {
                    Text(
                        text = "Неверный формат имени или фамилии",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )
                }

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Имя") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(10.dp),
                )
                TextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Фамилия") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),
                )

                DatePickerScreen()

                if (passHint) {
                    Text(
                        text = "Неверный формат пароля (добавьте заглавную букву, цифру, спец символ, длиною > 8)",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )
                }

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),

                    )
                if (passMismatch) {
                    Text(
                        text = "Пароли не совпадают",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )
                }
                TextField(
                    value = confPassword,
                    onValueChange = { confPassword = it },
                    label = { Text("Подтвердите пароль") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),

                    )
                if (fieldIsEmpty) {
                    Text(
                        text = "Заполните все поля",
                        color = Color.Red,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )
                }

                
                Button(
                    onClick = {
                        if (password.isNotEmpty() && name.isNotEmpty()
                            && surname.isNotEmpty() && confPassword.isNotEmpty()
                            && dateGlobal.isNotEmpty() && name[0].isUpperCase()
                            && name.length > 2 && surname[0].isUpperCase()
                            && surname.length > 2 && password == confPassword && isValidPassword(
                                password
                            )
                        ) {

                            MainActivity.saveMyValue(context, name)
                            MainActivity.setLogin(context, true)

                            val intent = Intent(context, MainScreen::class.java)
                            intent.putExtra("name", name)
                            context.startActivity(intent)
                        } else {
                            if (name.isNotEmpty() && surname.isNotEmpty()) {
                                nameHint =
                                    !(name[0].isUpperCase() && name.length > 2 && surname[0].isUpperCase() && surname.length > 2)
                            }
                            if (password.isNotEmpty() && confPassword.isNotEmpty()) {
                                passMismatch = password != confPassword
                                passHint = !isValidPassword(password)
                            }
                        }
                        fieldIsEmpty =
                            (password.isEmpty() || name.isEmpty() || surname.isEmpty() || confPassword.isEmpty() || dateGlobal.isEmpty())
                    },
                    colors = ButtonDefaults.buttonColors(
                        Color.Transparent
                    ),
                    modifier = Modifier
                        .padding(30.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(btnColor.value)
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = "Регистрация", color = Color.Black, fontSize = 20.sp
                    )
                }

            }
        }
    }



    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }
                .firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }
                .firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

    @Composable
    fun DatePickerScreen() {
        val calendar = Calendar.getInstance()
        calendar.clear()

        // set the initial date
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = calendar.timeInMillis,
            initialDisplayMode = DisplayMode.Picker
        )


        var showDatePicker by remember {
            mutableStateOf(false)
        }

        var selectedDate by remember {
            mutableLongStateOf(calendar.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
        }
        Row(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp),

            ) {
            val formatter = SimpleDateFormat("ddMMyyyy", Locale.ROOT)
            var value by remember { mutableStateOf("") }
            dateGlobal = value
            val maxChar = 8
            if (showDatePicker) {
                selectedDate = datePickerState.selectedDateMillis!!
                value = formatter.format(Date(selectedDate))
            }
            TextField(
                value = value,
                onValueChange = {
                    if (it.length <= maxChar) value = it
                },
                visualTransformation = DateTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Дата") },
                maxLines = 1,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier.width(225.dp),
            )


            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePicker = false
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDatePicker = false
                            selectedDate = datePickerState.selectedDateMillis!!
                        }) {
                            Text(text = "Confirm")
                        }
                    },
                ) {
                    DatePicker(
                        state = datePickerState, showModeToggle = false
                    )
                }
            }

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                IconButton(modifier = Modifier
                    .size(56.dp)
                    .background(Color.Blue),
                    onClick = { showDatePicker = true }) {
                    Icon(
                        Icons.Filled.DateRange,
                        "contentDescription",
                    )
                }
            }
        }
    }
}
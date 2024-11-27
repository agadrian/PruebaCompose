package es.pruebapmdm.pantallas

import android.util.Patterns
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.pruebapmdm.R
import es.pruebapmdm.navegacion.AppScreen


/**
 * Fucnion principal de la App.
 * Comrpuebo que sea un email, contraseña numerica y dni validos, anntes de activar el botón.
 * Una vez activo, se cambia de pantalla al hacer click, limpiando los datos de los texfields.
 */
@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black)),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        // Estados para recordar las variables

        var email by rememberSaveable { mutableStateOf("") }
        var isValidEmail by rememberSaveable { mutableStateOf(false) }

        var password by rememberSaveable { mutableStateOf("") }
        var isValidPassword by rememberSaveable { mutableStateOf(false) }
        var isVisiblePassword by rememberSaveable { mutableStateOf(false) }

        var dni by rememberSaveable { mutableStateOf("") }
        var isValidDni by rememberSaveable { mutableStateOf(false) }


        Header()

        Spacer(Modifier.height(40.dp))

        EmailTextField(
            email = email,
            emailChange = {
                email = it
                isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            },
            isValidEmail = isValidEmail
        )

        Spacer(Modifier.height(20.dp))

        TextFieldPass(
            password = password,
            passwordChange = {
                password = it
                isValidPassword = password.length > 8 && checkNumericPassword(password) // Comprobar longitud y que son numeros
            },
            passwordVisible = isVisiblePassword,
            passwordVisibleChange = {
                isVisiblePassword =! isVisiblePassword
            },
            isValidPassword = isValidPassword

        )

        Spacer(Modifier.height(20.dp))

        TextFieldDNI(
            dni = dni,
            dniChange = {
                dni = it
                isValidDni = checkValidDni(dni)
            },
            isValidDni = isValidDni
        )

        Spacer(Modifier.height(200.dp))

        RegisterButton(
            onClick = {

                navController.navigate(
                    route = AppScreen.WelcomeScreen.createRoute(
                        email = email,
                        dni = dni
                    )
                )
                // Limpiar los campos texfield
                email = ""
                password = ""
                dni = ""
            },
            isValidInfo = isValidEmail && isValidPassword && isValidDni
        )
    }
}


@Composable
fun Header(){
    Text(
        text = "Bienvenido a myApp.\nPor favor, regístrese",
        fontSize = 25.sp,
        color = colorResource(R.color.white),
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(30.dp)
    )

    Spacer(Modifier.height(10.dp))

    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo myapp",
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
    )
}


@Composable
fun EmailTextField(
    email: String,
    emailChange: (String) -> Unit,
    isValidEmail: Boolean
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center

    ) {

        Text(
            text = "Email",
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(5.dp))


        OutlinedTextField(
            value = email,
            onValueChange = emailChange,
            placeholder = { Text("Email") },
            maxLines = 1, // Una sola linea
            singleLine = true, // Una sola linea
            textStyle = TextStyle(
                color = colorResource(R.color.white)
            ),
            colors = if(isValidEmail){
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.white),
                    unfocusedBorderColor = colorResource(R.color.gray)
                )
            }else{
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.red),
                    cursorColor = colorResource(R.color.white),
                    unfocusedBorderColor = colorResource(R.color.gray)
                )
            },
            modifier = Modifier
                .fillMaxWidth()

        )

        // Mensaje que aparece si no es correcto los datos introducidos
        if (!isValidEmail && email.isNotEmpty() ){
            Text(
                text = "Introduce un correo válido",
                fontSize = 13.sp,
                color = Color.Red
            )
        }
    }
}


@Composable
fun TextFieldPass(
    password: String,
    passwordChange: (String) -> Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean
){

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Contraseña",
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = password,
            onValueChange = passwordChange,
            placeholder = { Text(text = "Contraseña (solo numérica)") },
            maxLines = 1, // Una sola linea
            singleLine = true, // Una sola linea
            textStyle = TextStyle(
                color = colorResource(R.color.white)
            ),
            trailingIcon = {
                val image = if(passwordVisible){
                    Icons.Filled.VisibilityOff
                }else{
                    Icons.Filled.Visibility
                }

                // Ojito para ver contraseña
                IconButton(
                    onClick = passwordVisibleChange,
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = "Ver contraseña",
                    )
                }
            },

            // Accion de Mostrar / Ocultar contraseña
            visualTransformation = if (passwordVisible){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },

            modifier = Modifier
                .fillMaxWidth(),

            // Color del textdield dependiendo de si es valida la contraseña o no
            colors = if(isValidPassword){
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.white),
                    unfocusedBorderColor = colorResource(R.color.gray)
                )
            }else{
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.red),
                    cursorColor = colorResource(R.color.white),
                    unfocusedBorderColor = colorResource(R.color.gray)
                )
            }
        )

        // Mensaje que aparece si no es correcto los datos introducidos
        if (!isValidPassword && password.isNotEmpty()){
            Text(
                text = "La contraseña debe tener 8+ caracteres numéricos",
                fontSize = 13.sp,
                color = colorResource(R.color.red)
            )
        }
    }
}



@Composable
fun TextFieldDNI(
    dni: String,
    dniChange: (String) -> Unit,
    isValidDni: Boolean
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "DNI",
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = dni,
            onValueChange = dniChange,
            placeholder = { Text(text = "DNI") },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = colorResource(R.color.white)
            ),
            colors = if(isValidDni){
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.white),
                    unfocusedBorderColor = colorResource(R.color.gray)
                )
            }else{
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.red),
                    cursorColor = colorResource(R.color.white),
                    unfocusedBorderColor = colorResource(R.color.gray)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Mensaje que aparece si no es correcto los datos introducidos
        if (!isValidDni && dni.isNotEmpty()){
            Text(
                text = "Introduce un dni válido",
                fontSize = 13.sp,
                color = colorResource(R.color.red)
            )
        }

    }
}

/**
 * Se activo cuando los tres campos de datos son validos. Se comprueba mediante el Boolean isValidInfo, que al ser llamado comprueba los 3 isValidxxx
 */
@Composable
fun RegisterButton(
    onClick: () -> Unit,
    isValidInfo: Boolean
){

    // Crear el efecto de pulsar un botón. (Algo extra que use en una práctica)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val sizeScale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "Efecto click size")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            enabled = isValidInfo,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isPressed){
                    colorResource(R.color.greenButtonPressed)
                }else{
                    colorResource(R.color.greenButton)
                },
                disabledContainerColor = colorResource(R.color.gray),

                contentColor = colorResource(R.color.black)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer(
                    scaleX = sizeScale,
                    scaleY = sizeScale
                ),
            interactionSource = interactionSource
        ) {
            Text(
                text = "Resgistrarse",
                fontWeight = FontWeight.Bold
            )
        }
    }
}



/**
 * Comprobar que la contraseña es numerica, mirando cada caracter si es digit.
 */
fun checkNumericPassword(password: String): Boolean{
    return password.all { it.isDigit() }
}

/**
 * Comprobar si un DNI es real y valido (Usado en una practica de ADA)
 */
fun checkValidDni(dni: String): Boolean{
    // Comprobar que el formato sean 8 num + 1 letra
    val dniRegex = "^[0-9]{8}[A-Za-z]$".toRegex()
    if (!dni.matches(dniRegex)) {
        return false
    }

    // Obtener numeros y letras (8-1)
    val numero = dni.substring(0, 8).toLong()
    val letra = dni[8].uppercaseChar()

    val letrasValidas = listOf('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E')

    val residuo = (numero % 23).toInt()

    // Retornamos si la letra calculada es igual a la introdducida o no
    return letrasValidas[residuo] == letra
}

@Composable
@Preview
fun PreviewMain(){
    MainScreen(
        navController = rememberNavController()
    )
}
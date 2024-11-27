package es.pruebapmdm.pantallas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.pruebapmdm.R
import es.pruebapmdm.customToast


@Composable
fun WelcomeScreen(
    navController: NavController,
    email: String,
    dni: String,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))
    ) {

        Header(
            onBackClick = { navController.navigateUp() } // Volver a la pantalla anterior
        )

        Body(
            email,
            dni
        )

        Spacer(Modifier.height(250.dp))

        ContinueButton(
            onClick = {
                customToast(context, "Próximamente...")
            }
        )
    }
}

@Composable
fun Header(
    onBackClick: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 30.dp, end = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier
                    .size(40.dp),
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = colorResource(R.color.white),
                    modifier = Modifier
                        .size(40.dp)
                )
            }

            Text(
                text = "Bienvenid@!",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.white)

            )
        }

        Image(
            painter = painterResource(R.drawable.tick),

            contentDescription = "Tick",
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
        )
    }
}


@Composable
fun Body(
    email: String,
    dni: String
){
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Text(
            text = "Su cuenta ha sido registrada correctamente.",
            fontSize = 18.sp,
            color = colorResource(R.color.white),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(80.dp))

        Text(
            text = "Datos",
            fontSize = 20.sp,
            color = colorResource(R.color.white),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(15.dp))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Text(
            text = "Email: $email",
            fontSize = 18.sp,
            color = colorResource(R.color.white)
        )

        Spacer(Modifier.height(15.dp))

        Text(
            text = "Dni: $dni",
            fontSize = 18.sp,
            color = colorResource(R.color.white)
        )
    }
}




@Composable
fun ContinueButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    // Crear el efecto de pulsar un botón. (Algo extra que use en una práctica)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val sizeScale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "Efecto click size")


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
    ) {
        Button(
            onClick = onClick,
            enabled = true,
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
                text = "Acceso App",
                fontWeight = FontWeight.Bold
            )
        }
    }
}



@Composable
@Preview
fun PreviewWelcome(){
    WelcomeScreen(
        rememberNavController(),
        email = "adfsdfg@fgfdgdfg.com",
        dni = "4534535345"
    )
}
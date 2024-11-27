package es.pruebapmdm.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.pruebapmdm.pantallas.MainScreen
import es.pruebapmdm.pantallas.WelcomeScreen


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
){
    val navControlador = rememberNavController()

    NavHost(
        navController = navControlador,
        startDestination = AppScreen.RegisterScreen.route
    ) {

        // Ruta especifica de MainScreen
        composable(
            route = AppScreen.RegisterScreen.route
        ){
            MainScreen(
                navController = navControlador,
                modifier
            )
        }

        // Ruta especifica de WelcomeScreen
        composable (
            route = AppScreen.WelcomeScreen.route,
            // Configura el argumento que recibe
            arguments = listOf(
                navArgument(name = "email") {type = NavType.StringType},
                navArgument(name = "dni") {type = NavType.StringType}
            )
        ) {
            // Recuperamos los argumentos para pasarselo y darle uso en la funcion WelcomeScreen

            val email = it.arguments?.getString("email") ?: "Sin email"
            val dni = it.arguments?.getString("dni") ?: "Sin dni"


            WelcomeScreen(
                navController = navControlador,
                email = email,
                dni = dni,
                modifier = modifier
            )
        }
    }
}


package es.pruebapmdm.navegacion

sealed class AppScreen(
    val route: String
) {
    object RegisterScreen: AppScreen("RegisterScreen")
    object WelcomeScreen : AppScreen("WelcomeScreen/{email}/{dni}") {
        /**
         * Permite navegar directamente a la ruta del contacto que se le pase.
         */
        fun createRoute(
            email: String,
            dni: String
        ): String {
            return "WelcomeScreen/$email/$dni"
        }
    }
}
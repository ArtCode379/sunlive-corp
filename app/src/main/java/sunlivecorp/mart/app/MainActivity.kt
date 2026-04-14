package sunlivecorp.mart.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import sunlivecorp.mart.app.ui.composable.approot.AppRoot
import sunlivecorp.mart.app.ui.theme.ProductAppCPNSLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppCPNSLTheme {
                AppRoot()
            }
        }
    }
}
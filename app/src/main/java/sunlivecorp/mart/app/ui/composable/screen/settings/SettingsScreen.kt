package sunlivecorp.mart.app.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sunlivecorp.mart.app.ui.theme.BronzeAccent
import sunlivecorp.mart.app.ui.theme.CharcoalPrimary
import sunlivecorp.mart.app.ui.theme.BorderLight
import sunlivecorp.mart.app.ui.theme.SurfaceVariantCream
import sunlivecorp.mart.app.ui.theme.TextMuted

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceVariantCream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SectionLabel("Preferences")
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ToggleRow(Icons.Default.Notifications, "Push Notifications", notificationsEnabled) { notificationsEnabled = it }
            Divider()
            ToggleRow(Icons.Default.DarkMode, "Dark Mode", darkModeEnabled) { darkModeEnabled = it }
        }

        Spacer(modifier = Modifier.height(20.dp))
        SectionLabel("About")
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoRow(Icons.Default.Info, "Company", "Sunlive Corp Ltd")
            Divider()
            InfoRow(Icons.Default.Info, "Version", "1.0.0")
        }

        Spacer(modifier = Modifier.height(20.dp))
        SectionLabel("Legal & Support")
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LinkRow(Icons.Default.Headset, "Customer Support") {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://sunliivecorp.uk")))
            }
            Divider()
            LinkRow(Icons.Default.Policy, "Privacy Policy") {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://sunliivecorp.uk/privacy")))
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionLabel(label: String) {
    Text(text = label.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TextMuted, letterSpacing = 1.sp)
}

@Composable
private fun Divider() {
    HorizontalDivider(color = BorderLight, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
private fun ToggleRow(icon: ImageVector, label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = BronzeAccent, modifier = Modifier.size(20.dp))
        Text(text = label, fontSize = 14.sp, color = CharcoalPrimary, modifier = Modifier.weight(1f).padding(start = 12.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CharcoalPrimary))
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = BronzeAccent, modifier = Modifier.size(20.dp))
        Text(text = label, fontSize = 14.sp, color = CharcoalPrimary, modifier = Modifier.weight(1f).padding(start = 12.dp))
        Text(text = value, fontSize = 14.sp, color = TextMuted)
    }
}

@Composable
private fun LinkRow(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = BronzeAccent, modifier = Modifier.size(20.dp))
        Text(text = label, fontSize = 14.sp, color = CharcoalPrimary, modifier = Modifier.weight(1f).padding(start = 12.dp))
        Icon(Icons.Default.ChevronRight, null, tint = TextMuted, modifier = Modifier.size(20.dp))
    }
}

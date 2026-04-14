package sunlivecorp.mart.app.ui.composable.screen.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sunlivecorp.mart.app.data.entity.OrderEntity
import sunlivecorp.mart.app.ui.theme.BronzeAccent
import sunlivecorp.mart.app.ui.theme.CharcoalPrimary
import sunlivecorp.mart.app.ui.theme.SurfaceVariantCream
import sunlivecorp.mart.app.ui.theme.TextMuted

@Composable
fun CheckoutDialog(
    order: OrderEntity,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(text = "Order Confirmed! 🎉", fontWeight = FontWeight.SemiBold, color = CharcoalPrimary, fontSize = 18.sp)
        },
        text = {
            Column {
                Text(
                    text = "Thank you for choosing pre-loved! Your items are reserved and will be ready for collection within 24 hours.",
                    color = TextMuted,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceVariantCream, RoundedCornerShape(10.dp))
                        .padding(14.dp)
                ) {
                    Text(text = "Order #${order.orderNumber}", fontWeight = FontWeight.SemiBold, color = CharcoalPrimary, fontSize = 14.sp)
                    Text(text = "Total: £%.2f".format(order.price), color = BronzeAccent, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text(text = "${order.customerFirstName} ${order.customerLastName}", color = TextMuted, fontSize = 13.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = CharcoalPrimary)
            ) {
                Text(text = "View My Orders", color = Color.White)
            }
        }
    )
}

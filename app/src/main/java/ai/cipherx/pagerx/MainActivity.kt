package ai.cipherx.pagerx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF05080A)
private val Panel = Color(0xFF0B1116)
private val Green = Color(0xFF00FF88)
private val Cyan = Color(0xFF22D3EE)
private val Dim = Color(0xFF7C97A0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(5,8,10)
        window.navigationBarColor = android.graphics.Color.rgb(5,8,10)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    background = Bg,
                    surface = Panel,
                    primary = Green
                )
            ) {
                Dashboard()
            }
        }
    }
}

@Composable
fun Dashboard() {
    var host by remember { mutableStateOf("172.16.52.1") }
    var port by remember { mutableStateOf("22") }

    Column(
        Modifier
            .fillMaxSize()
            .background(Bg)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "CIPHERX",
            color = Green,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
        Text(
            "PAGER-X ADVANCED // COMMAND DECK",
            color = Cyan,
            fontSize = 11.sp
        )

        HorizontalDivider(color = Green.copy(alpha = .4f))

        Card(
            colors = CardDefaults.cardColors(containerColor = Panel),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("DEVICE CONNECTION", color = Green)
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = host,
                    onValueChange = { host = it },
                    label = { Text("Pager IP address") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = port,
                    onValueChange = { port = it },
                    label = { Text("SSH Port") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "SSH integration is not activated yet.",
                    color = Dim,
                    fontSize = 12.sp
                )
            }
        }

        Text("SYSTEM TELEMETRY", color = Green)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard("CPU LOAD", "—")
            MetricCard("MEMORY", "—")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard("CLIENTS", "—")
            MetricCard("UPTIME", "—")
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Panel),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("SECURITY CENTER", color = Cyan)
                Spacer(Modifier.height(10.dp))
                Text("Approval gate: ENABLED", color = Green)
                Text("Host key: NOT VERIFIED", color = Dim)
                Text("Audit: LOCAL ONLY", color = Dim)
                Text("Payload execution: DISABLED", color = Green)
            }
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Panel),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("CONNECTION TARGET", color = Cyan)
                Text("$host:$port", color = Green)
                Text("WiFi Pineapple Pager Mark VII", color = Dim)
            }
        }

        Text(
            "CIPHERX // INITIAL PROJECT BUILD",
            color = Dim,
            fontSize = 10.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun RowScope.MetricCard(title: String, value: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Panel),
        modifier = Modifier.weight(1f)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(title, color = Dim, fontSize = 10.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                value,
                color = Green,
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

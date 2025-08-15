import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.deepplan.R
import com.example.deepplan.ui.theme.DeepPlanTheme

@Composable
fun CookiePlaceholder(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.four_sided_cookie),
        contentDescription = "Loading Shape",
        modifier = modifier.size(175.dp)
    )
}

@Composable
fun ProjectLoadingScreen(
    modifier: Modifier = Modifier,
    shapeContent: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            shapeContent()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Please wait while we summarize\nyour project",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProjectLoadingScreen() {
    ProjectLoadingScreen {
        CookiePlaceholder()
    }
}


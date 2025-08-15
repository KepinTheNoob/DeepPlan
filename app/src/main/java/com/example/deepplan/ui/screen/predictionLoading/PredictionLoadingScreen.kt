package com.example.deepplan.ui.screen.predictionLoading

@Composable
fun CloverPlaceholder() {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProjectLoadingScreen() {
    ProjectLoadingScreen {
        CloverPlaceholder() // Replace this with your animation composable
    }
}

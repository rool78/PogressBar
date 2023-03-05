package com.rool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rool.ui.theme.ProgressBarTheme

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeProgressBar()
        }
    }
}

@Composable
fun ComposeProgressBar() {
    LinearProgressIndicator(modifier = Modifier.height(20.dp))
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    ProgressBarTheme {
        ComposeProgressBar()
    }
}
package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButtonArrow(
    modifier: Modifier = Modifier,
    textResId: Int,
    drawResId: Int,
    height: Dp = 60.dp,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(height),
        onClick = onClick,
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = stringResource(textResId),
            style = style
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = modifier.padding(end = 8.dp),
            painter = painterResource(drawResId),
            contentDescription = null
        )
    }
}

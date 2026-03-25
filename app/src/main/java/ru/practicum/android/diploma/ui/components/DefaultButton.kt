package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    textResId: Int,
    height: Dp = 60.dp,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit,
    textColor: Color = Color.Black,
    backgroundColor: Color? = null,
    roundedCorner: Dp = 12.dp
) {
    Button(
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        onClick = onClick,
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = backgroundColor ?: ButtonDefaults.buttonColors().containerColor
        ),
        shape = RoundedCornerShape(roundedCorner)
    ) {
        Text(
            text = stringResource(textResId),
            style = style,
            fontWeight = FontWeight.Bold
        )
    }
}

package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R

@Composable
fun SimpleTopBarTempl(
    text: Int,
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    navigationIcon: Int = R.drawable.ic_arrow_back,
    onNavigationClick: (() -> Unit)? = null,
    navigationContentDescription: String = "",
    primaryAction: SimpleTopBarAction? = null,
    secondaryAction: SimpleTopBarAction? = null,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.titleMedium
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onNavigationClick != null) {
                IconButton(
                    onClick = onNavigationClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(navigationIcon),
                        contentDescription = navigationContentDescription,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                text = stringResource(text),
                modifier = Modifier
                    .weight(1f),
                style = style
            )

            Row {
                primaryAction?.let { action ->
                    IconButton(
                        onClick = action.onClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(action.icon),
                            contentDescription = action.contentDescription,
                            tint = action.tint ?: MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                secondaryAction?.let { action ->
                    IconButton(
                        onClick = action.onClick,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(action.icon),
                            contentDescription = action.contentDescription,
                            tint = action.tint ?: MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

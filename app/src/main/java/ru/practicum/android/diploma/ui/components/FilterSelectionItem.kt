package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.additionalColors

const val Weight095 = 0.95f
const val Weight005 = 0.05f

@Composable
fun FilterSelectionItem(
    modifier: Modifier = Modifier,
    title: String,
    selectedValue: String?,
    onItemClick: () -> Unit,
    onClearClick: () -> Unit
) {
    val isSelected = !selectedValue.isNullOrEmpty()

    Button(
        modifier = modifier.height(60.dp),
        onClick = onItemClick,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(Weight095, fill = false)) {
                FilterSelectionItemTitle(title, isSelected)
                if (isSelected) {
                    FilterSelectionItemValue(selectedValue!!)
                }
            }

            Spacer(modifier = Modifier.weight(Weight005))

            Icon(
                modifier = modifier
                    .padding(end = 8.dp)
                    .clickable { if (isSelected) onClearClick() else onItemClick() },
                painter = painterResource(if (isSelected) R.drawable.ic_clear else R.drawable.ic_button_arrow_right),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun FilterSelectionItemTitle(title: String, isSelected: Boolean) {
    Text(
        text = title,
        style = if (isSelected) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.additionalColors.gray
    )
}

@Composable
private fun FilterSelectionItemValue(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

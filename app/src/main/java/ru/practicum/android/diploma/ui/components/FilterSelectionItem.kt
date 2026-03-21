package ru.practicum.android.diploma.ui.components

// import androidx.compose.foundation.clickable
// import androidx.compose.foundation.layout.Arrangement
// import androidx.compose.foundation.layout.Column
// import androidx.compose.foundation.layout.PaddingValues
// import androidx.compose.foundation.layout.Row
// import androidx.compose.foundation.layout.Spacer
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.height
// import androidx.compose.foundation.layout.padding
// import androidx.compose.material3.Button
// import androidx.compose.material3.Icon
// import androidx.compose.material3.MaterialTheme
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.ui.Alignment
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.res.painterResource
// import androidx.compose.ui.text.style.TextOverflow
// import androidx.compose.ui.unit.dp
// import ru.practicum.android.diploma.R
//
// import ru.practicum.android.diploma.ui.theme.additionalColors
//
// const val ColumnWeight0x95f = 0.95f
// const val SpacerWeight0x05f = 0.05f
//
// @Composable
// fun FilterSelectionItem(
//    modifier: Modifier = Modifier,
//    title: String,
//    selectedValue: String?,
//    onItemClick: () -> Unit,
//    onCLearClick: () -> Unit
// ) {
//    Button(
//        modifier = modifier.height(60.dp),
//        onClick = onItemClick,
//        contentPadding = PaddingValues(horizontal = 16.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(ColumnWeight0x95f, fill = false)
//            ) {
//                Text(
//                    text = title,
//                    style = if (selectedValue.isNullOrEmpty()) {
//                        MaterialTheme.typography.bodyMedium
//                    } else {
//                        MaterialTheme.typography.bodySmall
//                    },
//                    color = if (selectedValue.isNullOrEmpty()) {
//                        MaterialTheme.colorScheme.onPrimary
//                        MaterialTheme.additionalColors.gray
//                    } else {
//                        MaterialTheme.colorScheme.onPrimary
//                    }
//                )
//                if (!selectedValue.isNullOrEmpty()) {
//                    Text(
//                        text = selectedValue,
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.weight(SpacerWeight0x05f))
//
//            Icon(
//                modifier = modifier
//                    .padding(end = 8.dp)
//                    .clickable {
//                        if (!selectedValue.isNullOrEmpty()) {
//                            onCLearClick()
//                        } else {
//                            onItemClick()
//                        }
//                    },
//                painter = painterResource(
//                    if (!selectedValue.isNullOrEmpty()) {
//                        R.drawable.ic_clear
//                    } else {
//                        R.drawable.ic_button_arrow_right
//                    }
//                ),
//                contentDescription = null
//            )
//        }
//    }
// }

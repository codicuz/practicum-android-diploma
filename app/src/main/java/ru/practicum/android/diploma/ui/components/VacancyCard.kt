package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun VacancyItem(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {
        EmployerLogo(
            logoUrl = vacancy.employerLogoUrl,
            employerName = vacancy.employerName
        )
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${vacancy.name}, ${vacancy.areaName}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = vacancy.employerName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formatSalary(vacancy),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun EmployerLogo(
    logoUrl: String?,
    employerName: String
) {
    var isError by remember { mutableStateOf(false) }
    var isLoaded by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(12.dp)

    if (!logoUrl.isNullOrEmpty() && !isError) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(logoUrl)
                .crossfade(true)
                .build(),
            contentDescription = employerName,
            modifier = Modifier
                .size(48.dp)
                .clip(shape)
                .background(Color.White),
            contentScale = ContentScale.Fit,
            onError = {
                isError = true
            },
            onSuccess = {
                isLoaded = true
            }
        )
    } else {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(shape)
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.additionalColors.lightGray,
                    shape = shape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.placeholder_32px),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun formatSalary(vacancy: Vacancy): String {
    val from = vacancy.salaryFrom
    val to = vacancy.salaryTo
    val currency = getCurrencySymbol(vacancy.salaryCurrency)

    return when {
        from != null && to != null -> stringResource(R.string.salary_from_to, from, to, currency)
        from != null -> stringResource(R.string.salary_from, from, currency)
        to != null -> stringResource(R.string.salary_to, to, currency)
        else -> stringResource(R.string.salary_not_specified)
    }
}

fun getCurrencySymbol(currency: String?): String {
    return when (currency) {
        "RUR", "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        "KZT" -> "₸"
        "UAH" -> "₴"
        "BYR" -> "Br"
        "GEL" -> "₾"
        "AZN" -> "₼"
        "UZS" -> "сўм"
        else -> currency ?: ""
    }
}

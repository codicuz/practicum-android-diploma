package ru.practicum.android.diploma.ui.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.CountryItemUi
import ru.practicum.android.diploma.presentation.filter.JobLocationViewModel
import ru.practicum.android.diploma.ui.components.CircularIndicator
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl

@Composable
fun CountrySelectScreen(
    viewModel: JobLocationViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onCountrySelected: (Int, String) -> Unit
) {
    val isLoading by viewModel.isCountriesLoading.collectAsState()
    val countries by viewModel.areas.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAreas()
    }

    CountrySelectContent(
        title = R.string.country_select,
        onNavigationClick = onNavigateBack,
        countries = countries,
        isLoading = isLoading,
        onCountrySelected = onCountrySelected
    )
}

@Composable
fun CountrySelectContent(
    title: Int,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    countries: List<CountryItemUi>,
    isLoading: Boolean,
    onCountrySelected: (Int, String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        SimpleTopBarTempl(
            modifier = modifier.padding(start = 4.dp),
            text = title,
            onNavigationClick = onNavigationClick
        )

        when {
            isLoading -> {
                CircularIndicator()
            }

            countries.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Список стран пуст")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(countries) { country ->
                        Button(
                            onClick = {
                                onCountrySelected(country.id, country.name)
                            }
                        ) {
                            val weight = 1f
                            Text(country.name)
                            Spacer(modifier = Modifier.weight(weight))
                            Icon(
                                painter = painterResource(R.drawable.ic_button_arrow_right),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

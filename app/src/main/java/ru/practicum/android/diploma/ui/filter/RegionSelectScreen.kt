package ru.practicum.android.diploma.ui.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.RegionItemUi
import ru.practicum.android.diploma.presentation.filter.JobLocationViewModel
import ru.practicum.android.diploma.ui.components.CircularIndicator
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun RegionSelectScreen(
    navController: NavHostController,
    viewModel: JobLocationViewModel = koinViewModel(),
    countryId: Int?,
    onNavigateBack: () -> Unit,
    onRegionSelected: (Int, String) -> Unit
) {
    val regions by viewModel.regions.collectAsState()
    val isLoading by viewModel.isRegionsLoading.collectAsState()
    val initialCountryId = remember { countryId }
    val effectiveCountryId = initialCountryId ?: countryId

    LaunchedEffect(effectiveCountryId) {
        viewModel.loadRegions(effectiveCountryId)
    }

    RegionSelectContent(
        title = R.string.region_select,
        modifier = Modifier,
        regions = regions,
        isLoading = isLoading,
        onNavigationClick = onNavigateBack,
        onRegionSelected = { regionId, regionName ->
            navController.previousBackStackEntry?.savedStateHandle?.apply {
                set("selected_region_id", regionId)
                set("selected_region_name", regionName)
                val selectedCountry = viewModel.areas.value.find { country ->
                    country.regions.any { it.id == regionId }
                }
                selectedCountry?.let {
                    set("selected_country_id", it.id)
                    set("selected_country_name", it.name)
                }
            }
            onRegionSelected(regionId, regionName)
        }
    )
}

@Composable
fun RegionSelectContent(
    title: Int,
    modifier: Modifier = Modifier,
    regions: List<RegionItemUi>,
    onNavigationClick: () -> Unit,
    isLoading: Boolean,
    onRegionSelected: (Int, String) -> Unit
) {
    val textFieldState = rememberTextFieldState()
    val filteredRegions by remember(regions, textFieldState) {
        derivedStateOf {
            val query = textFieldState.text.toString()
            if (query.isEmpty()) {
                regions
            } else {
                regions.filter { region ->
                    region.name.contains(query, ignoreCase = true)
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SimpleTopBarTempl(
            modifier = modifier.padding(start = 4.dp),
            text = title,
            onNavigationClick = onNavigationClick
        )

        RegionSearchTextField(
            textFieldState = textFieldState
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegionContentState(
            isLoading = isLoading,
            filteredRegions = filteredRegions,
            searchQuery = textFieldState.text.toString(),
            onRegionSelected = onRegionSelected
        )
    }
}

@Composable
private fun RegionSearchTextField(
    textFieldState: TextFieldState
) {
    TextField(
        state = textFieldState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp),
        lineLimits = TextFieldLineLimits.SingleLine,
        contentPadding = PaddingValues(start = 16.dp),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.additionalColors.black
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_region),
                color = MaterialTheme.additionalColors.placeHolder,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.additionalColors.cursor
        ),
        trailingIcon = {
            val icon = if (textFieldState.text.isEmpty()) {
                painterResource(R.drawable.search_24px)
            } else {
                painterResource(R.drawable.ic_clear)
            }
            IconButton(
                onClick = {
                    if (textFieldState.text.isNotEmpty()) {
                        textFieldState.edit {
                            replace(0, length, "")
                        }
                    }
                }
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "",
                    tint = MaterialTheme.additionalColors.black
                )
            }
        }
    )
}

@Composable
private fun RegionContentState(
    isLoading: Boolean,
    filteredRegions: List<RegionItemUi>,
    searchQuery: String,
    onRegionSelected: (Int, String) -> Unit
) {
    when {
        isLoading -> {
            CircularIndicator()
        }

        filteredRegions.isEmpty() -> {
            RegionEmptyState(searchQuery = searchQuery)
        }

        else -> {
            RegionListState(
                regions = filteredRegions,
                onRegionSelected = onRegionSelected
            )
        }
    }
}

@Composable
private fun RegionEmptyState(searchQuery: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (searchQuery.isNotEmpty()) {
                "Ничего не найдено"
            } else {
                "Список регионов пуст"
            }
        )
    }
}

@Composable
private fun RegionListState(
    regions: List<RegionItemUi>,
    onRegionSelected: (Int, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(regions) { region ->
            RegionItem(
                region = region,
                onClick = { onRegionSelected(region.id, region.name) }
            )
        }
    }
}

@Composable
private fun RegionItem(
    region: RegionItemUi,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick
    ) {
        val weight = 1f
        Text(region.name)
        Spacer(modifier = Modifier.weight(weight))
        Icon(
            painter = painterResource(R.drawable.ic_button_arrow_right),
            contentDescription = null
        )
    }
}

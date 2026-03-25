package ru.practicum.android.diploma.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.JobLocationState
import ru.practicum.android.diploma.presentation.filter.JobLocationViewModel
import ru.practicum.android.diploma.ui.components.FilterSelectionItem
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl
import ru.practicum.android.diploma.ui.theme.additionalColors

private const val KEY_COUNTRY_ID = "selected_country_id"
private const val KEY_COUNTRY_NAME = "selected_country_name"
private const val KEY_REGION_ID = "selected_region_id"
private const val KEY_REGION_NAME = "selected_region_name"
private const val KEY_REGION_COUNTRY_ID = "region_country_id"
private const val KEY_REGION_COUNTRY_NAME = "region_country_name"
@Composable
fun JobLocationScreen(
    navController: NavHostController,
    viewModel: JobLocationViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onJobCountryClick: () -> Unit,
    onJobRegionClick: (Int?) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val backStackEntry = navController.currentBackStackEntry
    val savedStateHandle = backStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.let { handle ->
            val countryId = handle.get<Int>(KEY_COUNTRY_ID)
            val countryName = handle.get<String>(KEY_COUNTRY_NAME)

            if (countryId != null && countryName != null) {
                viewModel.selectCountry(countryId, countryName)
                handle.remove<Int>(KEY_COUNTRY_ID)
                handle.remove<String>(KEY_COUNTRY_NAME)
            }

            val regionId = handle.get<Int>(KEY_REGION_ID)
            val regionName = handle.get<String>(KEY_REGION_NAME)

            if (regionId != null && regionName != null) {
                val regionCountryId = handle.get<Int>(KEY_REGION_COUNTRY_ID)
                val regionCountryName = handle.get<String>(KEY_REGION_COUNTRY_NAME)

                if (regionCountryId != null && regionCountryName != null) {
                    viewModel.selectCountry(regionCountryId, regionCountryName)
                    handle.remove<Int>(KEY_REGION_COUNTRY_ID)
                    handle.remove<String>(KEY_REGION_COUNTRY_NAME)
                }

                viewModel.selectRegion(regionId, regionName)
                handle.remove<Int>(KEY_REGION_ID)
                handle.remove<String>(KEY_REGION_NAME)
            }
        }
    }

    fun onConfirmClick() {
        navController.previousBackStackEntry?.savedStateHandle?.apply {
            state.selectedCountryId?.let { set(KEY_COUNTRY_ID, it) }
            state.selectedCountryName?.let { set(KEY_COUNTRY_NAME, it) }
            state.selectedRegionId?.let { set(KEY_REGION_ID, it) }
            state.selectedRegionName?.let { set(KEY_REGION_NAME, it) }
        }
        navController.navigateUp()
    }

    JobLocationContent(
        R.string.job_location_select,
        modifier = Modifier,
        onNavigationClick = onNavigateBack,
        state = state,
        onJobCountryClick = onJobCountryClick,
        onJobCountryClear = {
            viewModel.clearCountry()
            navController.previousBackStackEntry?.savedStateHandle?.apply {
                set(KEY_COUNTRY_ID, null)
                set(KEY_COUNTRY_NAME, null)
                set(KEY_REGION_ID, null)
                set(KEY_REGION_NAME, null)
            }
        },
        onJobRegionClick = {
            onJobRegionClick(state.selectedCountryId)
        },
        onJobRegionClear = {
            viewModel.clearRegion()
            navController.previousBackStackEntry?.savedStateHandle?.apply {
                set(KEY_REGION_ID, null)
                set(KEY_REGION_NAME, null)
            }
        },
        onConfirmClick = ::onConfirmClick
    )
}

@Composable
fun JobLocationContent(
    title: Int,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    state: JobLocationState,
    onJobCountryClick: () -> Unit,
    onJobCountryClear: () -> Unit,
    onJobRegionClick: (Int?) -> Unit,
    onJobRegionClear: () -> Unit,
    onConfirmClick: () -> Unit
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

        FilterSelectionItem(
            title = stringResource(R.string.job_country),
            selectedValue = state.selectedCountryName,
            onItemClick = onJobCountryClick,
            onClearClick = onJobCountryClear
        )

        FilterSelectionItem(
            title = stringResource(R.string.job_region),
            selectedValue = state.selectedRegionName,
            onItemClick = {
                onJobRegionClick(state.selectedCountryId)
            },
            onClearClick = onJobRegionClear
        )

        val weight = 1f
        Spacer(modifier = Modifier.weight(weight))

        if (state.selectedCountryId != null) {
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = onConfirmClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.additionalColors.blue)

            ) {
                Text(
                    text = stringResource(R.string.choose),
                    color = MaterialTheme.additionalColors.white,
                    style = MaterialTheme.typography.bodyMedium

                )
            }
            Spacer(modifier = Modifier.height(44.dp))
        }
    }
}

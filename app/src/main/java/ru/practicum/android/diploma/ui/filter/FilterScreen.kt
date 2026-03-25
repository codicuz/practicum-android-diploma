package ru.practicum.android.diploma.ui.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filter.FilterState
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.hasActiveFilters
import ru.practicum.android.diploma.ui.components.DefaultButton
import ru.practicum.android.diploma.ui.components.FilterSelectionItem
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun FilterScreen(
    navController: NavHostController,
    viewModel: FilterViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onJobLocationClick: () -> Unit = {},
    onIndustryClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    val backStackEntry = navController.currentBackStackEntry
    val savedStateHandle = backStackEntry?.savedStateHandle

    LaunchedEffect(savedStateHandle) {
        savedStateHandle?.let { handle ->
            val countryId = handle.get<Int>("selected_country_id")
            val countryName = handle.get<String>("selected_country_name")

            if (countryId != null && countryName != null) {
                viewModel.updateCountry(countryId, countryName)
                handle.remove<Int>("selected_country_id")
                handle.remove<String>("selected_country_name")
            }

            val regionId = handle.get<Int>("selected_region_id")
            val regionName = handle.get<String>("selected_region_name")

            if (regionId != null && regionName != null) {
                viewModel.updateRegion(regionId, regionName)
                handle.remove<Int>("selected_region_id")
                handle.remove<String>("selected_region_name")
            }

            val industryId = handle.get<Int>("selected_industry_id")
            val industryName = handle.get<String>("selected_industry_name")

            if (industryId != null && industryName != null) {
                viewModel.updateIndustry(industryId, industryName)
                handle.remove<Int>("selected_industry_id")
                handle.remove<String>("selected_industry_name")
            }
        }
    }

    fun onApplyClick() {
        navController.navigateUp()
    }

    FilterContent(
        title = R.string.filter_settings,
        modifier = Modifier,
        showResetButton = state.hasActiveFilters(),
        onNavigationClick = onNavigateBack,
        onJobLocationClick = onJobLocationClick,
        onIndustryClick = onIndustryClick,
        state = state,
        onSalaryChange = viewModel::updateSalary,
        onWithoutSalaryHidden = viewModel::updateWithoutSalaryHidden,
        onResetButtonClick = viewModel::resetFilters,
        onIndustryClear = viewModel::clearIndustry,
        onCountryClear = viewModel::clearCountry,
        onRegionClear = viewModel::clearRegion,
        onApplyClick = ::onApplyClick
    )
}

@Composable
fun FilterContent(
    title: Int,
    modifier: Modifier = Modifier,
    showResetButton: Boolean,
    onNavigationClick: () -> Unit,
    onJobLocationClick: () -> Unit,
    onIndustryClick: () -> Unit,
    state: FilterState,
    onSalaryChange: (String) -> Unit,
    onWithoutSalaryHidden: (Boolean) -> Unit,
    onResetButtonClick: () -> Unit,
    onIndustryClear: () -> Unit,
    onCountryClear: () -> Unit,
    onRegionClear: () -> Unit,
    onApplyClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val textFieldState = rememberTextFieldState(initialText = state.salary)

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .collect { newText ->
                onSalaryChange(newText)
            }
    }

    LaunchedEffect(state.salary) {
        if (textFieldState.text.toString() != state.salary) {
            textFieldState.edit {
                replace(0, length, state.salary)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) {
                focusManager.clearFocus()
            }
    ) {
        SimpleTopBarTempl(
            modifier = modifier.padding(start = 4.dp),
            text = title,
            onNavigationClick = onNavigationClick
        )

        val locationDisplay = when {
            state.selectedRegionName != null && state.selectedCountryName != null ->
                "${state.selectedCountryName}, ${state.selectedRegionName}"

            state.selectedCountryName != null -> state.selectedCountryName
            else -> null
        }

        FilterSelectionItem(
            title = stringResource(R.string.job_location),
            selectedValue = locationDisplay,
            onItemClick = onJobLocationClick,
            onClearClick = onCountryClear,
        )

        FilterSelectionItem(
            title = stringResource(R.string.industry),
            selectedValue = state.selectedIndustryName,
            onItemClick = onIndustryClick,
            onClearClick = onIndustryClear
        )

        Spacer(modifier = Modifier.height(24.dp))

        val digitsOnlyTransformation = remember {
            InputTransformation.byValue { _, proposed ->
                proposed.filter { it.isDigit() }
            }
        }
        TextField(
            state = textFieldState,
            inputTransformation = digitsOnlyTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .height(51.dp)
                .padding(horizontal = 16.dp),
            lineLimits = TextFieldLineLimits.SingleLine,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.additionalColors.black
            ),
            labelPosition = TextFieldLabelPosition.Attached(alwaysMinimize = true),
            label = {
                Text(
                    text = stringResource(R.string.wanted_salary),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_salary),
                    color = MaterialTheme.additionalColors.placeHolder,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            trailingIcon = {
                val icon = painterResource(R.drawable.ic_clear)
                if (textFieldState.text.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            textFieldState.edit {
                                replace(0, length, "")
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
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedLabelColor = MaterialTheme.additionalColors.blue,
                unfocusedLabelColor = if (textFieldState.text.isEmpty()) {
                    MaterialTheme.additionalColors.placeHolder
                } else {
                    MaterialTheme.additionalColors.black
                },
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.additionalColors.cursor
            ),
            shape = RoundedCornerShape(12.dp),
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable {
                    focusManager.clearFocus()
                    onWithoutSalaryHidden(!state.isWithoutSalayrHidden)
                }
                .padding(horizontal = 16.dp)
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.do_not_show_without_salary),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Checkbox(
                checked = state.isWithoutSalayrHidden,
                onCheckedChange = { newValue ->
                    focusManager.clearFocus()
                    onWithoutSalaryHidden(newValue)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.additionalColors.blue,
                    uncheckedColor = MaterialTheme.additionalColors.blue,
                    checkmarkColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            if (showResetButton) {
                DefaultButton(
                    textResId = R.string.apply,
                    onClick = onApplyClick,
                    backgroundColor = MaterialTheme.additionalColors.blue,
                    textColor = MaterialTheme.additionalColors.white
                )

                Spacer(modifier = Modifier.height(8.dp))

                DefaultButton(
                    textResId = R.string.reset,
                    onClick = onResetButtonClick,
                    textColor = MaterialTheme.additionalColors.red
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

package ru.practicum.android.diploma.ui.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.IndustryItemUi
import ru.practicum.android.diploma.presentation.filter.IndustrySelectState
import ru.practicum.android.diploma.presentation.filter.IndustrySelectViewModel
import ru.practicum.android.diploma.ui.components.CircularIndicator
import ru.practicum.android.diploma.ui.components.DefaultButton
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun IndustrySelectScreen(
    viewModel: IndustrySelectViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onIndustryConfirmed: (Int, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadIndustries()
    }

    IndustrySelectContent(
        title = R.string.industry_select,
        modifier = Modifier,
        onNavigationClick = onNavigateBack,
        state = state,
        onSearchQueryChange = viewModel::updateTextFilterField,
        onIndustrySelected = viewModel::selectIndustry,
        onConfirmClick = {
            state.selectedIndustryId?.let { industryId ->
                val industryName = state.industries.find { it.id == industryId }?.name ?: ""
                onIndustryConfirmed(industryId, industryName)
            }
        }
    )
}

@Composable
fun IndustrySelectContent(
    title: Int,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    state: IndustrySelectState,
    onSearchQueryChange: (String) -> Unit,
    onIndustrySelected: (Int) -> Unit,
    onConfirmClick: () -> Unit
) {
    val textFieldState = rememberTextFieldState(initialText = state.searchQuery)

    LaunchedEffect(textFieldState) {
        snapshotFlow { textFieldState.text.toString() }
            .collect { newText ->
                onSearchQueryChange(newText)
            }
    }

    LaunchedEffect(state.searchQuery) {
        if (textFieldState.text.toString() != state.searchQuery) {
            textFieldState.edit {
                replace(0, length, state.searchQuery)
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

        IndustrySearchTextField(
            textFieldState = textFieldState
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            IndustryContentState(
                state = state,
                onIndustrySelected = onIndustrySelected
            )
        }

        IndustryConfirmButton(
            selectedIndustryId = state.selectedIndustryId,
            onConfirmClick = onConfirmClick
        )
    }
}

@Composable
private fun IndustrySearchTextField(
    textFieldState: androidx.compose.foundation.text.input.TextFieldState
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
                text = stringResource(R.string.enter_industry),
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
private fun IndustryContentState(
    state: IndustrySelectState,
    onIndustrySelected: (Int) -> Unit
) {
    when {
        state.isLoading -> {
            IndustryLoadingState()
        }

        state.error != null -> {
            IndustryErrorState(error = state.error)
        }

        state.filteredIndustries.isEmpty() -> {
            IndustryEmptyState(searchQuery = state.searchQuery)
        }

        else -> {
            IndustryListState(
                industries = state.filteredIndustries,
                selectedIndustryId = state.selectedIndustryId,
                onIndustrySelected = onIndustrySelected
            )
        }
    }
}

@Composable
private fun IndustryLoadingState() {
    CircularIndicator()
}

@Composable
private fun IndustryErrorState(error: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Ошибка: $error",
            color = MaterialTheme.additionalColors.red
        )
    }
}

@Composable
private fun IndustryEmptyState(searchQuery: String) {
    Text(
        text = if (searchQuery.isNotEmpty()) {
            "Ничего не найдено"
        } else {
            "Список индустрий пуст"
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
private fun IndustryListState(
    industries: List<IndustryItemUi>,
    selectedIndustryId: Int?,
    onIndustrySelected: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(industries) { industry ->
            IndustryRadioItem(
                industry = industry,
                isSelected = industry.id == selectedIndustryId,
                onIndustrySelected = { onIndustrySelected(industry.id) }
            )
        }
    }
}

@Composable
private fun IndustryConfirmButton(
    selectedIndustryId: Int?,
    onConfirmClick: () -> Unit
) {
    if (selectedIndustryId != null) {
        Spacer(modifier = Modifier.height(8.dp))

        DefaultButton(
            textResId = R.string.choose,
            onClick = onConfirmClick,
            backgroundColor = MaterialTheme.additionalColors.blue,
            textColor = MaterialTheme.additionalColors.white,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun IndustryRadioItem(
    industry: IndustryItemUi,
    isSelected: Boolean,
    onIndustrySelected: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onIndustrySelected() }
                .padding(vertical = 8.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = industry.name,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(60.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyMedium
            )

            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = androidx.compose.material3.RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.additionalColors.blue
                )
            )
        }
    }
}

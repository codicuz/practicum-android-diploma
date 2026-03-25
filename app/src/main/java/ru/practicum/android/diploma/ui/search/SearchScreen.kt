package ru.practicum.android.diploma.ui.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.hasActiveFilters
import ru.practicum.android.diploma.presentation.search.SearchScreenState
import ru.practicum.android.diploma.presentation.search.SearchToastEvent
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    filterViewModel: FilterViewModel = koinViewModel(),
    onVacancyClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val noInternetMessage = stringResource(R.string.toast_no_internet)
    val errorMessage = stringResource(R.string.toast_error)

    val filterState by filterViewModel.state.collectAsState()
    val hasActiveFilters = filterState.hasActiveFilters()

    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { event ->
            val message = when (event) {
                SearchToastEvent.NO_INTERNET -> noInternetMessage
                SearchToastEvent.SERVER_ERROR -> errorMessage
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    SearchContent(
        state = state,
        searchQuery = searchQuery,
        onQueryChanged = viewModel::onSearchQueryChanged,
        onClearSearch = viewModel::onClearSearch,
        onLoadNextPage = viewModel::onLoadNextPage,
        onVacancyClick = onVacancyClick,
        onFilterClick = onFilterClick,
        hasActiveFilter = hasActiveFilters
    )
}

@Composable
fun SearchContent(
    state: SearchScreenState = SearchScreenState.Default,
    searchQuery: String = "",
    onQueryChanged: (String) -> Unit = {},
    onClearSearch: () -> Unit = {},
    onLoadNextPage: () -> Unit = {},
    onVacancyClick: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    hasActiveFilter: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Заголовок + фильтр В ОДНОЙ СТРОКЕ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.search_screen_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Card(
                modifier = Modifier.size(24.dp),
                shape = RoundedCornerShape(4.dp),
                colors = if (!hasActiveFilter) {
                    CardDefaults.cardColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = Color.Transparent
                    )
                } else {
                    CardDefaults.cardColors(
                        contentColor = MaterialTheme.additionalColors.white,
                        containerColor = MaterialTheme.additionalColors.blue
                    )
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onFilterClick()
                            },
                        painter = painterResource(R.drawable.filter_24px),
                        contentDescription = null,
                    )
                }
            }
        }

        // Строка поиска — без фильтра
        SearchBar(
            query = searchQuery,
            onQueryChanged = onQueryChanged,
            onClearClick = onClearSearch
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is SearchScreenState.Default -> DefaultPlaceholder()
            is SearchScreenState.Loading -> LoadingState()
            is SearchScreenState.Content -> ContentState(
                vacancies = state.vacancies,
                found = state.found,
                isNextPageLoading = state.isNextPageLoading,
                onVacancyClick = onVacancyClick,
                onLoadNextPage = onLoadNextPage
            )

            is SearchScreenState.Empty -> EmptyState()
            is SearchScreenState.NoInternet -> NoInternetState()
            is SearchScreenState.Error -> ErrorState()
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Текстовое поле — слева, занимает всё место
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            ),
            cursorBrush = SolidColor(MaterialTheme.additionalColors.cursor),
            modifier = Modifier.weight(1f),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_hint),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.additionalColors.placeHolder
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Справа: лупа или крестик
        if (query.isEmpty()) {
            Icon(
                painter = painterResource(R.drawable.search_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.close_24px),
                contentDescription = stringResource(R.string.search_clear),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClearClick()
                        focusManager.clearFocus()
                    }
            )
        }
    }
}

// ==================== СОСТОЯНИЯ ====================

@Composable
private fun DefaultPlaceholder() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.glasses),
            contentDescription = null,
            modifier = Modifier.size(272.dp)
        )
    }
}

@Composable
private fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(36.dp),
            color = MaterialTheme.additionalColors.blue,
            trackColor = Color.Transparent,
        )
    }
}

const val VisibleItemNumber = 5
@Composable
private fun ContentState(
    vacancies: List<Vacancy>,
    found: Int,
    isNextPageLoading: Boolean,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit
) {
    val listState = rememberLazyListState()

    // Определяем, нужно ли подгружать следующую страницу
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem >= totalItems - VisibleItemNumber
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadNextPage()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Количество найденных вакансий
        Text(
            text = stringResource(R.string.search_found_vacancies, found),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = vacancies,
                key = { it.id }
            ) { vacancy ->
                VacancyItem(
                    vacancy = vacancy,
                    onClick = { onVacancyClick(vacancy.id) }
                )
            }

            // Индикатор подгрузки следующей страницы
            if (isNextPageLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(36.dp),
                            color = MaterialTheme.additionalColors.blue,
                            trackColor = Color.Transparent,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.cat),
            contentDescription = null,
            modifier = Modifier.size(272.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_empty_result),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun NoInternetState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.skull),
            contentDescription = null,
            modifier = Modifier.size(272.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_no_internet),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ErrorState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.error),
            contentDescription = null,
            modifier = Modifier.size(272.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.search_server_error),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

// ==================== КАРТОЧКА ВАКАНСИИ ====================

@Composable
private fun VacancyItem(
    vacancy: Vacancy,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Лого работодателя
        AsyncImage(
            model = vacancy.employerLogoUrl,
            contentDescription = null,
            placeholder = painterResource(R.drawable.placeholder_employer),
            error = painterResource(R.drawable.placeholder_employer),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Название вакансии + город
            Text(
                text = "${vacancy.name}, ${vacancy.areaName}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Работодатель
            Text(
                text = vacancy.employerName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Зарплата
            Text(
                text = formatSalary(vacancy),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun formatSalary(vacancy: Vacancy): String {
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

private fun getCurrencySymbol(currency: String?): String {
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

package ru.practicum.android.diploma.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.presentation.favorite.FavoriteState
import ru.practicum.android.diploma.ui.favorite.FavoriteContent

@Preview
@Composable
fun FavoriteScreenPreviewEmpty() {
    DefaultPreviewContainer {
        FavoriteContent(FavoriteState.Empty, {})
    }
}

@Preview
@Composable
fun FavoriteScreenPreviewError() {
    DefaultPreviewContainer {
        FavoriteContent(FavoriteState.Error, {})
    }
}

@Preview
@Composable
fun FavoriteScreenPreviewContent() {
    DefaultPreviewContainer {
        FavoriteContent(FavoriteState.Success(dummyVacancies()), {})
    }
}

const val TenThousand = 10000
const val TwentyThousand = 20000
const val ThirstyThousand = 30000
const val FiftyThousand = 50000

const val Russia = "Россия"
@Composable
fun dummyVacancies(): List<Vacancy> {
    return listOf(
        Vacancy(
            "001",
            "менеджер по клинингу",
            "ООО 'Рюмашка'",
            null,
            Russia,
            TenThousand,
            TenThousand,
            "RUB"
        ),
        Vacancy(
            "002",
            "старший помощник младшего дворника",
            "ООО 'Рюмашка'",
            null,
            Russia,
            TwentyThousand,
            ThirstyThousand,
            "RUB"
        ),
        Vacancy(
            "003",
            "специалист ОТК",
            "ЗАО 'Ликероводчный завод имени трудового красного знамени'",
            null,
            Russia,
            FiftyThousand,
            FiftyThousand,
            "RUB"
        ),
    )
}

package ru.practicum.android.diploma.ui.vacancy

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vacancy.VacancyDetailState
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel
import ru.practicum.android.diploma.ui.components.ErrorState
import ru.practicum.android.diploma.ui.components.NoInternetState
import ru.practicum.android.diploma.ui.components.SimpleTopBarAction
import ru.practicum.android.diploma.ui.components.SimpleTopBarTempl
import ru.practicum.android.diploma.ui.components.getCurrencySymbol
import ru.practicum.android.diploma.ui.theme.additionalColors

@Composable
fun VacancyScreen(
    vacancyId: String,
    onNavigateBack: () -> Unit,
    viewModel: VacancyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(vacancyId) {
        viewModel.loadVacancy(vacancyId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        VacancyTopBar(
            state = state,
            context = context,
            onNavigateBack = onNavigateBack
        )

        when (state) {
            is VacancyDetailState.Loading -> VacancyLoadingState()
            is VacancyDetailState.Content -> VacancyContentState(
                vacancy = (state as VacancyDetailState.Content).vacancy,
                context = context
            )

            is VacancyDetailState.Error -> ErrorState()
            is VacancyDetailState.NoInternet -> NoInternetState()
        }
    }
}

@Composable
private fun VacancyTopBar(
    state: VacancyDetailState,
    context: Context,
    onNavigateBack: () -> Unit
) {
    val vacancy = (state as? VacancyDetailState.Content)?.vacancy

    SimpleTopBarTempl(
        text = R.string.vacancy_detail_title,
        modifier = Modifier.padding(start = 4.dp),
        onNavigationClick = onNavigateBack,
        primaryAction = SimpleTopBarAction(
            icon = R.drawable.sharing_24px,
            contentDescription = "Поделиться",
            onClick = {
                vacancy?.url?.let { url -> shareVacancy(context, url) }
            }
        ),
        secondaryAction = SimpleTopBarAction(
            icon = R.drawable.favorites_off__24px,
            contentDescription = "В избранное",
            onClick = { /* !!!!экрааааааааан избранных вакансий, ждем....!!!!! */ }
        )
    )
}

@Composable
private fun VacancyLoadingState() {
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

@Composable
private fun VacancyContentState(
    vacancy: VacancyDetail,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        VacancyHeader(vacancy)
        Spacer(modifier = Modifier.height(16.dp))
        EmployerCard(vacancy)
        Spacer(modifier = Modifier.height(24.dp))
        ExperienceSection(vacancy)
        EmploymentScheduleSection(vacancy)
        DescriptionSection(vacancy)
        SkillsSection(vacancy)
        ContactsSectionBlock(vacancy, context)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun VacancyHeader(vacancy: VacancyDetail) {
    Text(
        text = vacancy.name,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = formatDetailSalary(vacancy),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun ExperienceSection(vacancy: VacancyDetail) {
    if (vacancy.experience.isNullOrEmpty()) return

    Text(
        text = stringResource(R.string.vacancy_detail_experience),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = vacancy.experience,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun EmploymentScheduleSection(vacancy: VacancyDetail) {
    val text = buildEmploymentSchedule(vacancy)
    if (text.isEmpty()) return

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun DescriptionSection(vacancy: VacancyDetail) {
    if (vacancy.description.isEmpty()) return

    Text(
        text = stringResource(R.string.vacancy_detail_description),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = vacancy.description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun SkillsSection(vacancy: VacancyDetail) {
    if (vacancy.skills.isEmpty()) return

    KeySkillsSection(skills = vacancy.skills)
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ContactsSectionBlock(vacancy: VacancyDetail, context: Context) {
    if (!hasContacts(vacancy)) return

    ContactsSection(vacancy = vacancy, context = context)
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun EmployerCard(vacancy: VacancyDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EmployerLogo(
            logoUrl = vacancy.employerLogo,
            employerName = vacancy.employerName
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = vacancy.employerName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = vacancy.address ?: vacancy.areaName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
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
            onError = { isError = true }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun KeySkillsSection(skills: List<String>) {
    Text(
        text = stringResource(R.string.vacancy_detail_key_skills),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )

    Spacer(modifier = Modifier.height(8.dp))

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        skills.forEach { skill ->
            SkillChip(text = skill)
        }
    }
}

@Composable
private fun SkillChip(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ContactsSection(vacancy: VacancyDetail, context: Context) {
    Text(
        text = stringResource(R.string.vacancy_detail_contacts),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )

    Spacer(modifier = Modifier.height(8.dp))

    if (!vacancy.contactName.isNullOrEmpty()) {
        ContactLabel(label = stringResource(R.string.vacancy_detail_contact_person))
        Text(
            text = vacancy.contactName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (!vacancy.contactEmail.isNullOrEmpty()) {
        ContactLabel(label = stringResource(R.string.vacancy_detail_email))
        Text(
            text = vacancy.contactEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.additionalColors.blue,
            modifier = Modifier.clickable {
                openEmail(context, vacancy.contactEmail)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (vacancy.contactPhones!!.isNotEmpty()) {
        ContactLabel(label = stringResource(R.string.vacancy_detail_phone))
        vacancy.contactPhones.forEach { phone ->
            Text(
                text = phone.formatted,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.additionalColors.blue,
                modifier = Modifier.clickable {
                    openDialer(context, phone.formatted)
                }
            )
            if (!phone.comment.isNullOrEmpty()) {
                Text(
                    text = phone.comment,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun ContactLabel(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.additionalColors.gray
    )
    Spacer(modifier = Modifier.height(2.dp))
}


@Composable
private fun formatDetailSalary(vacancy: VacancyDetail): String {
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

private fun buildEmploymentSchedule(vacancy: VacancyDetail): String {
    val parts = mutableListOf<String>()
    if (!vacancy.employment.isNullOrEmpty()) parts.add(vacancy.employment)
    if (!vacancy.schedule.isNullOrEmpty()) parts.add(vacancy.schedule)
    return parts.joinToString(", ")
}

private fun hasContacts(vacancy: VacancyDetail): Boolean {
    return !vacancy.contactName.isNullOrEmpty() ||
        !vacancy.contactEmail.isNullOrEmpty() ||
        vacancy.contactPhones!!.isNotEmpty()
}

private fun shareVacancy(context: Context, url: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

private fun openEmail(context: Context, email: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
    }
    context.startActivity(Intent.createChooser(intent, null))
}

private fun openDialer(context: Context, phone: String) {
    val cleanPhone = phone.replace(Regex("[^+0-9]"), "")
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$cleanPhone")
    }
    context.startActivity(intent)
}

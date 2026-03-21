package ru.practicum.android.diploma.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.usecases.GetTeamMemberUseCase

class TeamViewModel(
    private val getTeamMembersUseCase: GetTeamMemberUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TeamState())
    val state: StateFlow<TeamState> = _state.asStateFlow()

    init {
        loadScreen()
    }

    private fun loadScreen() {
        viewModelScope.launch {
            val domainMembers = getTeamMembersUseCase()

            val uiMembers = domainMembers.map { member ->
                TeamMemberUi(
                    name = member.nameResId,
                    role = member.roleResId
                )
            }
            _state.update {
                it.copy(
                    members = uiMembers
                )
            }
        }
    }
}

package ru.quipy.logic

import ru.quipy.api.project.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*
import java.util.UUID

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    lateinit var projectTitle: String
    lateinit var creatorId: UUID
    var tasks = mutableSetOf<UUID>()
    var participants = mutableSetOf<UUID>()
    var statuses = mutableMapOf<UUID, StatusEntity>()

    override fun getId() = projectId

    // State transition functions which is represented by the class member function
    @StateTransitionFunc
    fun createProject(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        creatorId = event.creatorId
    }

    @StateTransitionFunc
    fun addParticipantToProject(event: AddParticipantToProjectEvent){
        participants.add(event.participantId)
    }

    @StateTransitionFunc
    fun createStatus(event: StatusCreatedEvent) {
        statuses[event.statusId] = StatusEntity(event.statusId, event.statusName, event.color, 0)
    }

    @StateTransitionFunc
    fun changeStatus(event: ChangeStatusNameEvent){
        val qtyTask = statuses[event.statusId]?.taskQuantity
        val colorStatus = statuses[event.statusId]?.color
        statuses[event.statusId] = StatusEntity(event.statusId, event.statusName, colorStatus, qtyTask)
    }

    @StateTransitionFunc
    fun deleteStatus(event: DeleteStatusEvent){
        statuses.remove(event.statusId)
    }

    @StateTransitionFunc
    fun addTaskToProject(event: TaskCreatedEvent) {
        tasks.add(event.taskId)
    }

    @StateTransitionFunc
    fun deleteTaskFromProject(event: TaskDeletedEvent) {
        tasks.remove(event.taskId)
    }
}

data class StatusEntity(
    val statusId: UUID,
    val statusName: String,
    val color: String?,
    val taskQuantity: Int?
){
    companion object {
        const val DEFAULT_STATUS = "CREATED"
    }
}




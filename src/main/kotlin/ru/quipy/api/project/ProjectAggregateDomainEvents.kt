package ru.quipy.api.project

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val ADD_PARTICIPANT_TO_PROJECT_EVENT = "ADD_PARTICIPANT_TO_PROJECT_EVENT"
const val STATUS_CREATED_EVENT = "STATUS_CREATED_EVENT"
const val CHANGE_STATUS_NAME_EVENT = "CHANGE_STATUS_NAME_EVENT"
const val DELETE_STATUS_EVENT = "DELETE_STATUS_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val TASK_DELETED_EVENT = "TASK_DELETED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val taskId: UUID
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT
)

@DomainEvent(name = TASK_DELETED_EVENT)
class TaskDeletedEvent(
    val projectId: UUID,
    val taskId: UUID,
) : Event<ProjectAggregate>(
    name = TASK_DELETED_EVENT
)

@DomainEvent(name = ADD_PARTICIPANT_TO_PROJECT_EVENT)
class AddParticipantToProjectEvent(
    val projectId: UUID,
    val participantId: UUID,
) : Event<ProjectAggregate>(
    name = ADD_PARTICIPANT_TO_PROJECT_EVENT
)

@DomainEvent(name = STATUS_CREATED_EVENT)
class StatusCreatedEvent(
    val projectId: UUID,
    val statusId: UUID,
    val statusName: String,
    val color: String,
    val taskQuantity: Int,
) : Event<ProjectAggregate>(
    name = STATUS_CREATED_EVENT
)

@DomainEvent(name = CHANGE_STATUS_NAME_EVENT)
class ChangeStatusNameEvent(
    val projectId: UUID,
    val statusId: UUID,
    val statusName: String
) : Event<ProjectAggregate>(
    name = CHANGE_STATUS_NAME_EVENT
)

@DomainEvent(name = DELETE_STATUS_EVENT)
class DeleteStatusEvent(
    val projectId: UUID,
    val statusId: UUID,
) : Event<ProjectAggregate>(
    name = DELETE_STATUS_EVENT
)

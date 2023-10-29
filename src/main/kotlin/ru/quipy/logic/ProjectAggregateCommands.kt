package ru.quipy.logic

import ru.quipy.api.project.*
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.createProject(id: UUID, title: String, creatorId: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.addParticipantToProject(userId: UUID): AddParticipantToProjectEvent {
    return AddParticipantToProjectEvent(
        projectId = this.getId(),
        participantId = userId
    )
}

fun ProjectAggregateState.createStatus(name: String, color: String): StatusCreatedEvent {
    return StatusCreatedEvent(
        projectId = this.getId(),
        statusId = UUID.randomUUID(),
        statusName = name,
        color = color,
        taskQuantity = 0
    )
}

fun ProjectAggregateState.deleteStatus(statusId: UUID): DeleteStatusEvent{
    return DeleteStatusEvent(
        projectId = this.getId(),
        statusId = statusId
    )
}

fun ProjectAggregateState.changeStatusName(statusId: UUID, statusName: String): ChangeStatusNameEvent{
    return ChangeStatusNameEvent(
        projectId = this.getId(),
        statusId = statusId,
        statusName = statusName
    )
}

fun ProjectAggregateState.addTasktoProject(taskId: UUID): TaskCreatedEvent {
    return TaskCreatedEvent(
        projectId = this.getId(),
        taskId = taskId
    )
}

fun ProjectAggregateState.delleteTaskFromProject(taskId: UUID): TaskDeletedEvent {
    return TaskDeletedEvent(
        projectId = this.getId(),
        taskId = taskId
    )
}

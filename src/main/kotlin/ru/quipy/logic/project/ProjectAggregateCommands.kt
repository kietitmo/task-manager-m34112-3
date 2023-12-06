package ru.quipy.logic.project

import ru.quipy.api.project.*
import ru.quipy.logic.project.*
import java.util.*
import ru.quipy.projections.*

// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions
fun ProjectAggregateState.createProject(id: UUID, title: String, creatorId: UUID): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = id,
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.addParticipantToProject(userId: UUID, userProjectionRepository: UserProjectionRepo): AddParticipantToProjectEvent {
    // check Is participant already in project
    if (participants.contains(userId)) {
        throw IllegalArgumentException("User already exists: $userId")
    }

    if (!userProjectionRepository.existsById(userId)) {
        throw IllegalArgumentException("User is not exists: $userId")
    }

    return AddParticipantToProjectEvent(
        projectId = this.getId(),
        participantId = userId
    )
}

fun ProjectAggregateState.createStatus(name: String, color: String): StatusCreatedEvent {
    if (statuses.values.any { it.statusName == name }) {
        throw IllegalArgumentException("Status already exists: $name")
    }

    return StatusCreatedEvent(
        projectId = this.getId(),
        statusId = UUID.randomUUID(),
        statusName = name,
        color = color,
        taskQuantity = 0
    )
}

fun ProjectAggregateState.deleteStatus(statusId: UUID): DeleteStatusEvent{
    if (statuses.containsKey(statusId)) {
        throw IllegalArgumentException("Project doesn't contains this status")
    }

    if (statuses[statusId]?.taskQuantity!! > 0) {
        throw IllegalArgumentException("Status has some tasks")
    }

    return DeleteStatusEvent(
        projectId = this.getId(),
        statusId = statusId
    )
}

fun ProjectAggregateState.changeStatusName(statusId: UUID, newStatusName: String): ChangeStatusNameEvent{
    if (statuses[statusId]?.statusName == newStatusName) {
        throw IllegalArgumentException("New status name is the same as old status name")
    }

    if (statuses.values.any { it.statusName == newStatusName }) {
        throw IllegalArgumentException("Status name already exists: $newStatusName")
    }

    return ChangeStatusNameEvent(
        projectId = this.getId(),
        statusId = statusId,
        newStatusName = newStatusName
    )
}

fun ProjectAggregateState.addTasktoProject(taskId: UUID): TaskAddedInProjectEvent {
    return TaskAddedInProjectEvent(
        projectId = this.getId(),
        taskId = taskId,
    )
}

fun ProjectAggregateState.delleteTaskFromProject(taskId: UUID): TaskDeletedFromProjectEvent {
    return TaskDeletedFromProjectEvent(
        projectId = this.getId(),
        taskId = taskId
    )
}


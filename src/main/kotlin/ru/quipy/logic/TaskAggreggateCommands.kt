package ru.quipy.logic

import ru.quipy.api.task.*
import java.util.UUID

fun TaskAggregateState.createTask(projectId: UUID, taskId: UUID, taskName: String): TaskCreatedEvent{
    return  TaskCreatedEvent(
        projectId = projectId,
        taskId = taskId,
        taskName = taskName
    )
}

fun TaskAggregateState.changeTaskTitle(newTaskName: String) : TaskNameChangeEvent {
    return TaskNameChangeEvent(
        taskId = this.getId(),
        oldTaskName = this.taskName,
        newTaskName = newTaskName
    )
}

fun TaskAggregateState.addExecutorToTask(userId: UUID) : AssignedExcutorToTaskEvent {
    return AssignedExcutorToTaskEvent(
        taskId = this.getId(),
        userId = userId
    )
}

fun TaskAggregateState.changeTaskStatus(statusId: UUID): TaskStatusChangeEvent {
    return TaskStatusChangeEvent(
        taskId = this.getId(),
        newStatusId = statusId,
        oldStatusId = this.statusId
    )
}


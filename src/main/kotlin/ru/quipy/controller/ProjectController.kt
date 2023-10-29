package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.project.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>
) {
    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: String) : ProjectCreatedEvent {
        return projectEsService.create { it.createProject(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectAggregateState? {
        return projectEsService.getState(projectId)
    }

    @PostMapping("/{projectId}/statuses/")
    fun addStatus(@PathVariable projectId: UUID, @RequestParam statusName: String, @RequestParam color: String) : StatusCreatedEvent {
        return projectEsService.update(projectId) {
            it.createStatus(statusName, color)
        }
    }

    @DeleteMapping("/{projectId}/statuses/{status}")
    fun deleteStatus(@PathVariable projectId: UUID, @PathVariable status: UUID) : DeleteStatusEvent {
        return projectEsService.update(projectId) {
            it.deleteStatus(status)
        }
    }

    @PostMapping("/{projectId}/status/changeStatus/{statusId}/{newStatusName}")
    fun changeStatus(@PathVariable projectId: UUID, @PathVariable statusId: UUID, @PathVariable newStatusName: String) : ChangeStatusNameEvent{
        return projectEsService.update(projectId) {
            it.changeStatusName(statusId, newStatusName)
        }
    }

    @PostMapping("/{projectId}/participants")
    fun addParticipantToProject(@PathVariable projectId: UUID, @RequestParam participant: UUID) : AddParticipantToProjectEvent {
        return projectEsService.update(projectId) {
            it.addParticipantToProject(participant)
        }
    }

    @PostMapping("/{projectId}/tasks/{taskId}")
    fun createTask(@PathVariable projectId: UUID, @PathVariable taskId: UUID) : TaskCreatedEvent {
        return projectEsService.update(projectId) {
            it.addTasktoProject(taskId)
        }
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    fun deleteTask(@PathVariable projectId: UUID, @PathVariable taskId: UUID) : TaskDeletedEvent {
        return projectEsService.update(projectId) {
            it.delleteTaskFromProject(taskId)
        }
    }
}

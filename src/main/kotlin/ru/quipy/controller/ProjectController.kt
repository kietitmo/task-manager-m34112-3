package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.project.*
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.project.*
import ru.quipy.projections.service.ProjectionsService
import ru.quipy.projections.*
import java.lang.IllegalArgumentException
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
    val projectionsService: ProjectionsService,
) {
    @PostMapping()
    fun getAllProjectInfor() : List<ProjectProjection>
    {
        return projectionsService.getAllProjectInfor();
    }

    @PostMapping("/{projectTitle}")
    fun createProject(@PathVariable projectTitle: String, @RequestParam creatorId: UUID) : ProjectCreatedEvent {
        return projectEsService.create { it.createProject(UUID.randomUUID(), projectTitle, creatorId) }
    }

    @GetMapping("/{projectId}")
    fun getProject(@PathVariable projectId: UUID) : ProjectProjection {
        return projectionsService.getProjectById(projectId);
    }

    @PostMapping("addStatus/{projectId}")
    fun addStatus(@PathVariable projectId: UUID, @RequestParam statusName: String, @RequestParam color: String) : StatusCreatedEvent {
        return projectEsService.update(projectId) {
            it.createStatus(statusName, color);
        }
    }

    @DeleteMapping("/statuses/{status}")
    fun deleteStatus(@PathVariable projectId: UUID, @PathVariable status: UUID) : DeleteStatusEvent {
        return projectEsService.update(projectId) {
            it.deleteStatus(status);
        }
    }

    @PostMapping("/{projectId}/status/changeStatus/{statusId}/{newStatusName}")
    fun changeStatus(@PathVariable projectId: UUID, @PathVariable statusId: UUID, @PathVariable newStatusName: String) : ChangeStatusNameEvent{
        return projectEsService.update(projectId) {
            it.changeStatusName(statusId, newStatusName);
        }
    }

    @PostMapping("participants/{projectId}")
    fun addParticipantToProject(@PathVariable projectId: UUID, @RequestParam participantId: UUID) : AddParticipantToProjectEvent {
        return projectEsService.update(projectId) {
            it.addParticipantToProject(participantId, projectionsService.userProjectionRepo);
        }
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    fun deleteTask(@PathVariable projectId: UUID, @PathVariable taskId: UUID) : TaskDeletedFromProjectEvent {
        return projectEsService.update(projectId) {
            it.delleteTaskFromProject(taskId);
        }
    }
}

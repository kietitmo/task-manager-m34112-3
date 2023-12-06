package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.projections.*
import ru.quipy.projections.service.ProjectionsService
import java.util.*

@RestController
@RequestMapping("/projections")
class ProjectionsController (val projectionsService: ProjectionsService) {
    @GetMapping("/isLoginExist/{login}")
    fun isLoginExist(@PathVariable login: String): Boolean {
        return projectionsService.isLoginExist(login);
    }

    @GetMapping("/getUserByName/{name}")
    fun getUserByName(@PathVariable name: String): List<UserProjection>? {
        return projectionsService.getUserByName(name);
    }

    @GetMapping("/getParticipantOfProjectByID/{projectId}")
    fun getParticipantOfProjectByID(@PathVariable projectId : UUID) : List<ProjectUserProjection> {
        return projectionsService.getParticipantOfProjectByID(projectId);
    }

    @GetMapping("/getAllProjectOfUserById/{userId}")
    fun getAllProjectOfUserById(@PathVariable userId : UUID) : List<ProjectUserProjection> {
        return projectionsService.getAllProjectOfUserById(userId);
    }

    @GetMapping("/getTasksOfProjectByID/{projectId}")
    fun getTasksOfProjectByID(@PathVariable projectId : UUID) : List<ProjectTasksProjection> {
        return projectionsService.getTasksOfProjectByID(projectId);
    }

    @GetMapping("/getAllStatusOfProject/{projectId}")
    fun getAllStatusOfProject(@PathVariable projectId : UUID) : List<StatusProjectProjection> {
        return projectionsService.getAllStatusOfProject(projectId);

    }
}
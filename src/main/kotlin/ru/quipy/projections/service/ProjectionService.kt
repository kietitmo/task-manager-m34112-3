package ru.quipy.projections.service

import org.springframework.stereotype.Service
import ru.quipy.projections.*
import java.util.UUID

@Service
class ProjectionsService constructor(val userProjectionRepo: UserProjectionRepo,
                                     val projectTaskProjectionRepo: ProjectTaskProjectionRepo,
                                     val projectUserProjectionRepo: ProjectUserProjectionRepo,
                                     val statusProjectProjectionRepo: StatusProjectProjectionRepo){

    fun isLoginExist(login: String): Boolean {
        return userProjectionRepo.existsByLogin(login);
    }

    fun getUserByName(displayName: String): List<UserProjection>? {
        return userProjectionRepo.findAllByDisplayName(displayName);
    }

    fun getParticipantOfProjectByID(projectId : UUID) : List<ProjectUserProjection> {
        return projectUserProjectionRepo.findAllByProjectIdNotNull(projectId);
    }

    fun getAllProjectOfUserById(userId : UUID) : List<ProjectUserProjection> {
        return projectUserProjectionRepo.findAllByUserIdNotNull(userId);
    }

    fun getTasksOfProjectByID(projectId : UUID) : List<ProjectTasksProjection> {
        return projectTaskProjectionRepo.findAllByProjectIdNotNull(projectId);
    }

    fun getAllStatusOfProject(projectId : UUID) : List<StatusProjectProjection> {
        return statusProjectProjectionRepo.findAllByProjectIdNotNull(projectId);
    }
}
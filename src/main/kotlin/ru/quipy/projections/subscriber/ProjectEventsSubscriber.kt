package ru.quipy.projections.subscriber

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.project.*
import ru.quipy.logic.project.*
import ru.quipy.api.task.*
import ru.quipy.logic.task.*
import ru.quipy.streams.AggregateSubscriptionsManager
import ru.quipy.core.EventSourcingService
import java.util.*

import javax.annotation.PostConstruct

@Service
class ProjectEventsSubscriber(
    private val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
) {

    val logger: Logger = LoggerFactory.getLogger(ProjectEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "project::project-subscriber") {

            `when`(ProjectCreatedEvent::class) { event ->
                logger.info("Project created: {}", event.title)
            }

            `when`(StatusCreatedEvent::class) { event ->
                logger.info("Project {} - status created: {}", event.projectId, event.statusName)
            }
        }

        subscriptionsManager.createSubscriber(TaskAggregate::class, "project::task-subcriber") {
            `when` (TaskCreatedEvent::class) {  event ->
                projectEsService.create { it.addTasktoProject(event.taskId) }
                logger.info("Task was ADDED {} in project {}", event.taskId, event.projectId)
            }
        }
    }
}
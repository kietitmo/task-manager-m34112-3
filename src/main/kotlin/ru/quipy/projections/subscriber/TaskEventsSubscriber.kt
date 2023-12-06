package ru.quipy.projections.subscriber

import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.task.*
import ru.quipy.logic.task.*
import ru.quipy.streams.AggregateSubscriptionsManager
import ru.quipy.core.EventSourcingService
import javax.annotation.PostConstruct
import java.util.*


@Service
class TaskEventsSubscriber (
    private val taskEsService: EventSourcingService<UUID, TaskAggregate, TaskAggregateState>,
) {
    val logger: Logger = LoggerFactory.getLogger(TaskEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(TaskAggregate::class, "task::task-subscriber") {

            `when`(TaskNameChangeEvent::class) { event ->
                logger.info("Task {} : {} was renamed old name to {}", event.taskId, event.oldTaskName, event.newTaskName)
            }

            `when`(AssignedExcutorToTaskEvent::class) { event ->
                logger.info("User {} assigned to task {}", event.userId, event.taskId)
            }

            `when`(TaskStatusChangeEvent::class) {event ->
                logger.info("Task Status {} : {} was changed old name to {}", event.oldStatusId, event.newStatusId)
            }
        }
    }
}
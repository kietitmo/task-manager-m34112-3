package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import ru.quipy.streams.AggregateSubscriptionsManager
import ru.quipy.api.project.*
import java.util.*
import javax.annotation.PostConstruct

@Component
class StatusProjectRelation(
    private val statusProjectProjectionRepo: StatusProjectProjectionRepo
) {

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "status-projet-projection") {

            `when`(StatusCreatedEvent::class) { event ->
                statusProjectProjectionRepo.save(StatusProjectProjection(event.statusId, event.statusName, event.projectId))
            }
            `when`(DeleteStatusEvent::class) { event ->
                val status = statusProjectProjectionRepo.findById(event.statusId).get()
                statusProjectProjectionRepo.delete(status)
            }
            `when`(ChangeStatusNameEvent::class) { event ->
                val status = statusProjectProjectionRepo.findById(event.statusId).get()
                status.statusName = event.newStatusName
                statusProjectProjectionRepo.save(status)
            }
        }

    }
}

@Document("status-project-projection")
data class StatusProjectProjection(
    var statusId: UUID,
    var statusName: String?,
    var projectId: UUID,
)
@Repository
interface StatusProjectProjectionRepo : MongoRepository<StatusProjectProjection, UUID>{
    fun findAllByProjectIdNotNull(projectId: UUID): List<StatusProjectProjection>
}
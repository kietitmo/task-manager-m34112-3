package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import ru.quipy.api.user.UserAggregate
import ru.quipy.api.user.UserCreatedEvent
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class UserRelation(
    private val userProjectionRepository: UserProjectionRepo
) {

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "User-Projection") {
            `when`(UserCreatedEvent::class) { event ->
                userProjectionRepository.save(UserProjection(event.userId, event.login, event.displayName))
            }
        }
    }
}

@Document("user-projection")
data class UserProjection(
    @Id
    val userId: UUID,
    val login: String,
    val displayName: String,
)

@Repository
interface UserProjectionRepo : MongoRepository<UserProjection, UUID>{
    fun findAllByDisplayName(displayName: String): List<UserProjection>?
    fun existsByLogin(login: String): Boolean;
}
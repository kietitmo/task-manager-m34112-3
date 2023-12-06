package ru.quipy.projections.subscriber

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import ru.quipy.api.user.*
import javax.annotation.PostConstruct

@Service
class UserEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(UserEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "user::user-subscriber") {

            `when`(UserCreatedEvent::class) { event ->
                logger.info("User {} created ", event.userId)
            }

            `when`(ChangeDisplayNameEvent::class) { event ->
                logger.info("User {} changed display name to {} ", event.userId, event.displayName)
            }
        }
    }
}
package ru.quipy.api.user

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.UUID

const val CHANGE_DISPLAY_NAME_EVENT = "CHANGE_DISPLAY_NAME_EVENT"
const val USER_CREATED_EVENT = "USER_CREATED_EVENT"

@DomainEvent(name = USER_CREATED_EVENT)
class UserCreatedEvent(
    val userId: UUID,
    val login: String,
    val password: String,
    val displayName: String
) : Event<UserAggregate>(
    name = USER_CREATED_EVENT
)

@DomainEvent(name = CHANGE_DISPLAY_NAME_EVENT)
class ChangeDisplayNameEvent(
    val userId: UUID,
    val displayName: String,
) : Event<UserAggregate>(
    name = CHANGE_DISPLAY_NAME_EVENT
)
package ru.quipy.logic

import ru.quipy.api.user.*
import java.util.UUID

fun UserAggregateState.createUser(userId: UUID,
                                  login: String,
                                  password: String,
                                  displayName: String): UserCreatedEvent{
    return  UserCreatedEvent(
        userId = userId,
        login = login,
        password = password,
        displayName = displayName
    )
}

fun UserAggregateState.changeDisplayName(userId: UUID, newDisplayName: String) : ChangeDisplayNameEvent {
    return ChangeDisplayNameEvent(
        userId = userId,
        displayName = newDisplayName
    )
}
package ru.quipy.logic.user

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
    if (this.displayName == newDisplayName ) {
        throw IllegalArgumentException("New display name is the same as old name")
    }

    return ChangeDisplayNameEvent(
        userId = userId,
        displayName = newDisplayName
    )
}
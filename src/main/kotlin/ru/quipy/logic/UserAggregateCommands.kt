package ru.quipy.logic

import ru.quipy.api.*
import java.util.UUID

fun UserAggregateState.createUser(userId: UUID,
                                  login: String,
                                  password: String,
                                  role: String): UserCreatedEvent{
    return  UserCreatedEvent(userId = userId, login = login, password = password, role = role)
}
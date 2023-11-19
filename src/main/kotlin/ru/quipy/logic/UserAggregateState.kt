package ru.quipy.logic

import ru.quipy.api.user.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*
import java.util.UUID

class UserAggregateState : AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    lateinit var login: String
    lateinit var password: String
    lateinit var displayName: String

    override fun getId() :UUID = userId

    @StateTransitionFunc
    fun createUser(event: UserCreatedEvent) {
        userId = event.userId
        login = event.login
        password = event.password
        displayName = event.displayName
    }

    @StateTransitionFunc
    fun changeDisplayName(event: ChangeDisplayNameEvent){
        userId = event.userId
        displayName = event.displayName
    }
}

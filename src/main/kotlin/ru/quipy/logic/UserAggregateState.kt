package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class UserAggregateState : AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    lateinit var login: String
    lateinit var password: String
    lateinit var role: String
    override fun getId() = userId

    @StateTransitionFunc
    fun createUser(event: UserCreatedEvent) {
        userId = event.userId
        login = event.login
        password = event.password
        role = event.role
    }

    @StateTransitionFunc
    fun changeUserName(event: ChangeUserNameEvent){
        userId = event.userId
        login = event.login
    }

    @StateTransitionFunc
    fun changeRoleUser(event: ChangeRoleUserEvent){
        userId = event.userId
        role = event.newRole
    }
}

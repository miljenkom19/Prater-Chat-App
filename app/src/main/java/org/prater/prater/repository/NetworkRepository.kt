package org.prater.prater.repository

import org.prater.prater.api.RetrofitInstance
import org.prater.prater.model.*
import retrofit2.Response

object NetworkRepository {

    //User
    suspend fun getAllUsers(): Response<List<User>> {
        return RetrofitInstance.api.getAllUsers()
    }

    suspend fun userLogin(username: String, password: String): Response<User> {
        return RetrofitInstance.api.userLogin(username, password)
    }

    suspend fun getUserById(userId: Int): Response<User> {
        return RetrofitInstance.api.getUserById(userId)
    }

    suspend fun getUserByUsername(username: String): Response<User> {
        return RetrofitInstance.api.getUserByUsername(username)
    }

    suspend fun userRegister(user: User): Response<User> {
        return RetrofitInstance.api.userRegister(user.username, user.password)
    }

    suspend fun updateUser(user: User): Response<User> {
        return RetrofitInstance.api.updateUser(user)
    }

    suspend fun deleteUser(user: User): Response<User> {
        return RetrofitInstance.api.deleteUser(user)
    }

    //Message
    suspend fun getMessagesByConversationId(conversationId: Int): Response<List<Message>> {
        return RetrofitInstance.api.getMessagesByConversationId(conversationId)
    }

    suspend fun postMessage(message: Message): Response<Message> {
        return RetrofitInstance.api.postMessage(
            message.id ?: 0,
            message.content,
            message.userId,
            message.conversationId
        )
    }

    //Conversation
    suspend fun getAllConversationForUser(userId: Int): Response<List<Conversation>> {
        return RetrofitInstance.api.getAllConversationsForUser(userId)
    }

    suspend fun postConversation(conversation: Conversation): Response<Conversation> {
        return RetrofitInstance.api.postConversation(
            conversation.id ?: 0,
            conversation.user1,
            conversation.user2
        )
    }
}
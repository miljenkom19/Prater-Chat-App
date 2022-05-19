package org.prater.prater.api

import org.prater.prater.model.*
import retrofit2.Response
import retrofit2.http.*

interface PraterApi {

    @GET("/users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("/users/login")
    suspend fun userLogin(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<User>

    @GET("/users/id/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Int
    ): Response<User>

    @GET("/users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username: String
    ): Response<User>

    @POST("/users/register")
    suspend fun userRegister(
        @Body user: User
    ): Response<User>

    @PUT("/users/{userId}")
    suspend fun updateUser(
        @Body user: User
    ): Response<User>

    @DELETE("/users/{userId}")
    suspend fun deleteUser(
        @Body user: User
    ): Response<User>

    //Message
    @GET("/messages/{conversationId}")
    suspend fun getMessagesByConversationId(
        @Path("conversationId") conversationId: Int
    ): Response<List<Message>>

    @POST("/messages")
    suspend fun postMessage(
        @Query("id") id: Int,
        @Query("content") content: String,
        @Query("userId") userId: Int,
        @Query("conversationId") conversationId: Int
    ): Response<Message>

    //Conversation
    @GET("/conversations/{userId}")
    suspend fun getAllConversationsForUser(
        @Path("userId") userId: Int
    ): Response<List<Conversation>>

    @POST("/conversations")
    suspend fun postConversation(
        @Body conversation: Conversation
    ): Response<Conversation>
}
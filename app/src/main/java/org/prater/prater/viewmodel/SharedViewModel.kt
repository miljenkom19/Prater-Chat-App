package org.prater.prater.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.prater.prater.model.*
import org.prater.prater.repository.NetworkRepository
import retrofit2.Response

class SharedViewModel : ViewModel() {

    private val networkRepository: NetworkRepository = NetworkRepository

    private val _loginResponse: MutableLiveData<Response<User>> = MutableLiveData()
    var loginResponse: LiveData<Response<User>> = _loginResponse

    private val _user: MutableLiveData<Response<User>> = MutableLiveData()
    var user: LiveData<Response<User>> = _user

    private val _userById: MutableLiveData<Response<User>> = MutableLiveData()
    var userById: LiveData<Response<User>> = _userById

    private val _userByUsername: MutableLiveData<Response<User>> = MutableLiveData()
    var userByUsername: LiveData<Response<User>> = _userByUsername

    private val _users: MutableLiveData<Response<List<User>>> = MutableLiveData()
    var users: LiveData<Response<List<User>>> = _users

    private val _registerResponse: MutableLiveData<Response<User>> = MutableLiveData()
    var registerResponse: LiveData<Response<User>> = _registerResponse

    private val _messages: MutableLiveData<Response<List<Message>>> = MutableLiveData()
    var messages: LiveData<Response<List<Message>>> = _messages

    private val _message: MutableLiveData<Response<Message>> = MutableLiveData()
    var message: LiveData<Response<Message>> = _message

    private val _conversation: MutableLiveData<Response<Conversation>> = MutableLiveData()
    var conversation: LiveData<Response<Conversation>> = _conversation

    private val _conversations: MutableLiveData<Response<List<Conversation>>> = MutableLiveData()
    var conversations: LiveData<Response<List<Conversation>>> = _conversations

    private val _image: MutableLiveData<Response<Image>> = MutableLiveData()
    var image: LiveData<Response<Image>> = _image

    private val _imageData: MutableLiveData<Response<ImageResponse>> = MutableLiveData()
    var imageData: LiveData<Response<ImageResponse>> = _imageData

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = networkRepository.userLogin(username, password)
        }
    }

    fun userRegister(user: User) {
        viewModelScope.launch {
            _registerResponse.value = networkRepository.userRegister(user)
        }
    }

    fun getAllUsers() {
        viewModelScope.launch {
            _users.value = networkRepository.getAllUsers()
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            _userById.value = networkRepository.getUserById(userId)
        }
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            _userByUsername.value = networkRepository.getUserByUsername(username)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _user.value = networkRepository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            _user.value = networkRepository.deleteUser(user)
        }
    }

    //Message
    fun getMessagesByConversationId(conversationId: Int) {
        viewModelScope.launch {
            _messages.value = networkRepository.getMessagesByConversationId(conversationId)
        }
    }

    fun postMessage(message: Message) {
        viewModelScope.launch {
            _message.value = networkRepository.postMessage(message)
        }
    }

    //Conversation
    fun getAllConversationsForUser(userId: Int) {
        viewModelScope.launch {
            _conversations.value = networkRepository.getAllConversationForUser(userId)
        }
    }

    fun postConversation(user1: Int, user2: Int) {
        viewModelScope.launch {
            _conversation.value = networkRepository.postConversation(user1, user2)
        }
    }

    //Image
    fun postImage(image: String, userId: Int) {
        viewModelScope.launch {
            _image.value = networkRepository.postImage(image, userId)
        }
    }

    fun getImageDataFromImageId(imageId: Int) {
        viewModelScope.launch {
            _imageData.value = networkRepository.getImageDataFromImageId(imageId)
        }
    }
}
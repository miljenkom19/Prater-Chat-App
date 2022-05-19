package org.prater.prater.fragments.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.prater.prater.adapter.ChatAdapter
import org.prater.prater.databinding.FragmentChatBinding
import org.prater.prater.model.Message
import org.prater.prater.viewmodel.SharedViewModel

class ChatFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args: ChatFragmentArgs by navArgs()
        val conversationId = args.conversation.id
        val userId = args.user.id!!
        binding = FragmentChatBinding.inflate(layoutInflater)

        viewModel.messages.observe(viewLifecycleOwner) {
            val messageList = it.body() ?: emptyList()
            val chatAdapter = ChatAdapter(messageList, userId)

            binding.recyclerView.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        viewModel.getMessagesByConversationId(conversationId!!)

        viewModel.message.observe(viewLifecycleOwner) {
            if(it.isSuccessful) {
                binding.messageEditText.setText("")
            } else {
                if(it.code() == 503)
                    Toast.makeText(context, "Server error", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show()
            }
        }

        binding.sendMessageButton.setOnClickListener {
            val content = binding.messageEditText.text.toString()
            if(content.isNotEmpty()) {
                val message = Message(null, content, userId, conversationId)
                viewModel.postMessage(message)
                viewModel.getMessagesByConversationId(conversationId)
            }
        }

        return binding.root
    }

}
package org.prater.prater.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.prater.prater.databinding.FragmentNewConversationBinding
import org.prater.prater.model.Conversation
import org.prater.prater.model.User
import org.prater.prater.viewmodel.SharedViewModel

class NewConversationFragment(private val user1: User) : DialogFragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentNewConversationBinding
    private lateinit var user2: User
    lateinit var conversation: Conversation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewConversationBinding.inflate(layoutInflater)

        val username = binding.usernameEditText.text.toString()
        var found = false
        viewModel.users.observe(viewLifecycleOwner) { response ->
            for(u in response.body()!!) {
                if(u.username == username)
                    found = true
            }
        }

        viewModel.userByUsername.observe(viewLifecycleOwner) { response ->
            if(response.isSuccessful)
                user2 = response.body()!!
        }

        viewModel.conversation.observe(viewLifecycleOwner) { response ->
            if(response.isSuccessful)
                conversation = response.body()!!
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.startNewConversationButton.setOnClickListener {

            viewModel.getAllUsers()

            if(found) {
                viewModel.getUserByUsername(username)
                val conversationEntity = Conversation(null, user1.id!!, user2.id!!)
                viewModel.postConversation(conversationEntity)
                dismiss()
                val action =
                    OverviewFragmentDirections.actionOverviewFragmentToChatFragment(conversation, user1)
                findNavController().navigate(action)

            }
        }

        return binding.root
    }

}
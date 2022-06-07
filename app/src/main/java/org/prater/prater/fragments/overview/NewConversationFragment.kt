package org.prater.prater.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import okhttp3.internal.wait
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
        var found = false
        var username = ""
        viewModel.users.observe(viewLifecycleOwner) { response ->
            for (u in response.body()!!) {
                if (u.username == username)
                    found = true
            }

            if (found) {
                binding.failTextView.text = ""
                viewModel.getUserByUsername(username)
            } else {
                binding.failTextView.text = "User does not exist"
            }
        }

        viewModel.userByUsername.observe(viewLifecycleOwner) { response ->
            if(response.isSuccessful) {
                user2 = response.body()!!
                viewModel.postConversation(user1.id ?: 0, user2.id ?: 0)
                conversation = Conversation(null, user1.id ?: 0, user2.id ?: 0)
                dismiss()
                val action =
                    OverviewFragmentDirections.actionOverviewFragmentToChatFragment(
                        conversation,
                        user1
                    )
                findNavController().navigate(action)
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.startNewConversationButton.setOnClickListener {
            username = binding.usernameEditText.text.toString()
            viewModel.getAllUsers()
        }

        return binding.root
    }

}
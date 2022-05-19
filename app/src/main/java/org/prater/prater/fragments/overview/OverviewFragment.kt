package org.prater.prater.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.prater.prater.adapter.OverviewAdapter
import org.prater.prater.databinding.FragmentOverviewBinding
import org.prater.prater.viewmodel.SharedViewModel

class OverviewFragment : Fragment() {
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val args by navArgs<OverviewFragmentArgs>()
        val user = args.user
        binding = FragmentOverviewBinding.inflate(layoutInflater)

        viewModel.conversations.observe(viewLifecycleOwner) {
             val overviewAdapter = OverviewAdapter(it.body() ?: emptyList(), user, viewModel, viewLifecycleOwner) { conversation ->
                val action =
                    OverviewFragmentDirections.actionOverviewFragmentToChatFragment(conversation, user)
                findNavController().navigate(action)
            }
            binding.recyclerView.apply {
                adapter = overviewAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        viewModel.getAllConversationsForUser(user.id ?: 0)


        binding.startNewConversationButton.setOnClickListener {
            val dialog = NewConversationFragment(user)
            dialog.show(parentFragmentManager, "Start a new conversation")
        }

        return binding.root
    }
}
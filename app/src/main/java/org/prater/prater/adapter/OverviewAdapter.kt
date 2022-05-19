package org.prater.prater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import org.prater.prater.databinding.OverviewItemBinding
import org.prater.prater.model.Conversation
import org.prater.prater.model.User
import org.prater.prater.viewmodel.SharedViewModel

class OverviewAdapter(
    private val conversationList: List<Conversation>,
    private val currentUser: User,
    private val viewModel: SharedViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val onSelect: (Conversation) -> Unit
) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    class ViewHolder(private val binding: OverviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation, currentUser: User, viewModel: SharedViewModel, lifecycleOwner: LifecycleOwner, onSelect: (Conversation) -> Unit) {

            viewModel.userById.observe(lifecycleOwner) {
                if(it.isSuccessful)
                    binding.usernameTextView.text = it.body()?.username
            }

            if(conversation.user1 == currentUser.id)
                viewModel.getUserById(conversation.user2)
            else
                viewModel.getUserById(conversation.user1)

            binding.root.setOnClickListener {
                onSelect(conversation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OverviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(conversationList[position], currentUser, viewModel, lifecycleOwner, onSelect)
    }

    override fun getItemCount() = conversationList.size
}
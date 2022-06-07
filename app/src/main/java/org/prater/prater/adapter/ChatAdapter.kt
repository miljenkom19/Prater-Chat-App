package org.prater.prater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.prater.prater.R
import org.prater.prater.databinding.MessageItemBinding
import org.prater.prater.model.Message

class ChatAdapter(
    private val messageList: List<Message>,
    private val userId: Int
) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message, userId: Int) {
            if (message.userId == userId)
                setConstraintsForUser()
            else
                setConstraintsForGuest()

            binding.messageTextView.text = message.content

            binding.profilePictureImageView.load("https://i.imgur.com/qCcds59.jpeg") {
                placeholder(R.drawable.ic_baseline_person_24)
            }
        }

        private fun setConstraintsForUser() {
            val constraintSet = ConstraintSet()
            val profilePictureImageViewId = binding.profilePictureImageView.id
            val messageTextViewId = binding.messageTextView.id
            constraintSet.clone(binding.constraintLayout)
            constraintSet.connect(
                profilePictureImageViewId,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )
            constraintSet.connect(
                messageTextViewId,
                ConstraintSet.START,
                profilePictureImageViewId,
                ConstraintSet.END,
                16
            )
            constraintSet.connect(
                profilePictureImageViewId,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                profilePictureImageViewId,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constraintSet.connect(
                messageTextViewId,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                messageTextViewId,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(binding.constraintLayout)
        }

        private fun setConstraintsForGuest() {
            val constraintSet = ConstraintSet()
            val profilePictureImageViewId = binding.profilePictureImageView.id
            val messageTextViewId = binding.messageTextView.id
            constraintSet.clone(binding.constraintLayout)
            constraintSet.clear(profilePictureImageViewId, ConstraintSet.START)
            constraintSet.clear(messageTextViewId, ConstraintSet.START)
            constraintSet.connect(
                messageTextViewId,
                ConstraintSet.END,
                profilePictureImageViewId,
                ConstraintSet.START,
                16
            )
            constraintSet.connect(
                profilePictureImageViewId,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END
            )
            constraintSet.connect(
                profilePictureImageViewId,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                profilePictureImageViewId,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constraintSet.connect(
                messageTextViewId,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                messageTextViewId,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM
            )
            constraintSet.applyTo(binding.constraintLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messageList[position], userId)
    }

    override fun getItemCount() = messageList.size
}
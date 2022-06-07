package org.prater.prater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import okhttp3.internal.notify
import okhttp3.internal.wait
import org.prater.prater.R
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
        fun bind(conversation: Conversation, username: String, currentUser: User, viewModel: SharedViewModel, lifecycleOwner: LifecycleOwner, onSelect: (Conversation) -> Unit) {

            /*viewModel.imageData.observe(lifecycleOwner) {
                if (it.isSuccessful) {
                    runBlocking {
                        launch {
                            val imageDataDecoded = Base64.getDecoder().decode(it.body()?.data)
                            val bitmap = BitmapFactory.decodeByteArray(
                                imageDataDecoded,
                                0,
                                imageDataDecoded.size
                            )
                            binding.profilePictureImageView.load(bitmap) {
                                placeholder(R.drawable.ic_baseline_person_24)
                            }
                        }
                    }
                }
            } */
            /*val profilePictureId = it.body()?.profilePicture ?: 0

                    if(profilePictureId != 0)
                        viewModel.getImageDataFromImageId(profilePictureId)
                    else
                        binding.profilePictureImageView.load(R.drawable.ic_baseline_person_24)*/

            binding.usernameTextView.text = username
            binding.profilePictureImageView.load(R.drawable.ic_baseline_person_24)

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

        val username = getUsername(position)

        holder.bind(
            conversationList[position],
            username,
            currentUser,
            viewModel,
            lifecycleOwner,
            onSelect
        )

    }

    override fun getItemCount() = conversationList.size

    private fun getUsername(position: Int): String {

        var username = ""

        viewModel.userById.observe(lifecycleOwner) {
            if(it.isSuccessful)
                username = it.body()?.username!!

        }

        if(conversationList[position].user1 == currentUser.id)
            viewModel.getUserById(conversationList[position].user2)
        else
            viewModel.getUserById(conversationList[position].user1)



        return username
    }
}
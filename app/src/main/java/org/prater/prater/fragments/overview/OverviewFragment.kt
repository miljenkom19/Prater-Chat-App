package org.prater.prater.fragments.overview

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import org.prater.prater.adapter.OverviewAdapter
import org.prater.prater.databinding.FragmentOverviewBinding
import org.prater.prater.viewmodel.SharedViewModel
import java.io.ByteArrayOutputStream
import java.util.Base64.getEncoder
import java.util.Collections.emptyList

class OverviewFragment : Fragment() {
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentOverviewBinding

    private suspend fun getBitmap(data: String): Bitmap {
        val loader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(data)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

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
            dialog.show(childFragmentManager, "newConversationFragment")
        }

        val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()) {

            lifecycleScope.launch {
                val bitmap = getBitmap(it.toString())
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
                val imageData = outputStream.toByteArray()
                val imageDataEncoded = getEncoder().encodeToString(imageData)

                //viewModel.postImage(imageDataEncoded, user.id ?: 0)
            }
        }

        binding.addImageButton.setOnClickListener {
            loadImage.launch("image/*")
        }

        return binding.root
    }
}
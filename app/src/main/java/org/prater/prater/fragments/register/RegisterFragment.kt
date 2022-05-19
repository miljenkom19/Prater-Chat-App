package org.prater.prater.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.prater.prater.R
import org.prater.prater.databinding.FragmentRegisterBinding
import org.prater.prater.model.User
import org.prater.prater.viewmodel.SharedViewModel

class RegisterFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        viewModel.registerResponse.observe(viewLifecycleOwner) {
            if(it.isSuccessful) {
                binding.failTextView.text = ""
                val action = RegisterFragmentDirections.actionRegisterFragmentToOverviewFragment(viewModel.registerResponse.value!!.body()!!)
                findNavController().navigate(action)
            } else {
                when {
                    it.code() == 400 -> binding.failTextView.text = getString(R.string.username_exists)
                    it.code() == 503 -> binding.failTextView.text = getString(R.string.server_error)
                    else -> binding.failTextView.text = getString(R.string.check_internet_connection)
                }
            }
        }

        binding.registerButton.setOnClickListener {
            if(isValid()) {
                username = binding.usernameEditText.text.toString().trim()
                password = binding.passwordEditText.text.toString().trim()

                viewModel.userRegister(User(null, username, password))
            }
        }

        return binding.root
    }

    private fun isValid() : Boolean {
        val username = binding.usernameEditText.text
        val password = binding.passwordEditText.text

        if(username.isEmpty() || password.isEmpty()) {
            binding.failTextView.text = getString(R.string.fill_out_fields)
            return false
        }

        return true
    }

}

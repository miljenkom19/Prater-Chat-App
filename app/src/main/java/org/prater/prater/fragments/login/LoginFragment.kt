package org.prater.prater.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.prater.prater.R
import org.prater.prater.databinding.FragmentLoginBinding
import org.prater.prater.viewmodel.SharedViewModel

class LoginFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                binding.failTextView.text = ""

                val action =
                    LoginFragmentDirections.actionLoginFragmentToOverviewFragment(it.body()!!)
                findNavController().navigate(action)
            } else {
                if (it.code() == 404)
                    binding.failTextView.text = getString(R.string.incorrect_username_or_password)
                else
                    binding.failTextView.text = getString(R.string.check_internet_connection)
            }
        }

        binding.loginButton.setOnClickListener {
            if (isValid()) {
                val username = binding.usernameEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString().trim()

                viewModel.userLogin(username, password)
            }
        }

        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun isValid(): Boolean {
        val username = binding.usernameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (username.length !in 4..25 || password.length !in 4..25) {
            binding.failTextView.text = getString(R.string.input_short_long)
            return false
        } else if (username.isEmpty() || password.isEmpty()) {
            binding.failTextView.text = getString(R.string.fill_out_fields)
            return false
        }
        return true
    }

}
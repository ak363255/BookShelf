package com.example.bookshelf.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookscoremodule.domain.LoginDataModel
import com.example.bookshelf.R
import com.example.bookshelf.databinding.LoginFragmentBinding
import com.example.bookshelf.domain.utils.LoginFieldValidation
import com.example.bookshelf.presentation.activity.BookShelfHomePageActivity
import com.example.bookshelf.presentation.viewmodel.LoginViewModel
import com.example.foodrecipes.domain.utils.Utility.showSnackBar
import com.example.network.ResultEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    var binding: LoginFragmentBinding? = null
    val loginViewModel: LoginViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        observeChanges()
        initUi()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.isUserLoggedIn()
    }

    private fun initUi() {
        setClickListeners()
        hideToolbar()
    }
    private fun hideToolbar(){
        (requireActivity() as BookShelfHomePageActivity).hideToolbar()
    }

    private fun observeChanges() {
        loginViewModel.validationRes.observe(viewLifecycleOwner) {
            when (it) {
                LoginFieldValidation.AllOk -> {
                    loginViewModel.loginUser(getLoginDetails())
                }

                is LoginFieldValidation.InvalidEmail -> {
                    showEmailError(it.msg)
                }

                is LoginFieldValidation.InvalidPassword -> {
                    showPasswordError(it.msg)
                }
            }
        }
        loginViewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultEvent.OnFailure -> {
                    //show Api error message

                    showMessage(it.msg)
                }

                ResultEvent.OnLoading -> {
                    //showLoader
                }

                is ResultEvent.OnSuccess -> {
                    goToBookListFragment()
                }
            }
        }

        loginViewModel.isLoggedIn.observe(viewLifecycleOwner){
            if(it){
                goToBookListFragment()
            }
        }
    }

    private fun showMessage(msg: String) {
        binding?.root?.let {
            requireContext().showSnackBar(it, msg, R.color.white)
        }
    }

    private fun goToBookListFragment() {
        if (requireActivity() is BookShelfHomePageActivity) {
            (requireActivity() as BookShelfHomePageActivity).goToBookListFragment(R.id.action_loginFragment_to_bookListFragment)
        }
    }

    private fun showPasswordError(msg: String) {
        binding?.passwordEditText?.error = msg
    }

    private fun showEmailError(msg: String) {
        binding?.emailIdEditText?.error = msg
    }

    private fun setClickListeners() {
        binding?.signUpTv?.setOnClickListener {
            if (requireActivity() is BookShelfHomePageActivity) {
                (requireActivity() as BookShelfHomePageActivity).goToSignUpFragment(R.id.action_loginFragment_to_signUpFragment)
            }
        }
        binding?.loginBtn?.setOnClickListener {
            loginViewModel.validateLoginDetails(getLoginDetails())
        }
    }

    private fun getLoginDetails(): LoginDataModel {
        return LoginDataModel(
            email = binding?.emailIdEditText?.text?.toString() ?: "",
            password = binding?.passwordEditText?.text?.toString() ?: ""
        )
    }
}
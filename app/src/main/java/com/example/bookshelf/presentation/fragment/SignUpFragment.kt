package com.example.bookshelf.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookscoremodule.domain.Country
import com.example.bookscoremodule.domain.SignUpDataModel
import com.example.bookshelf.R
import com.example.bookshelf.databinding.SignUpFragmentBinding
import com.example.bookshelf.domain.utils.SignUpFieldValidation
import com.example.bookshelf.presentation.activity.BookShelfHomePageActivity
import com.example.bookshelf.presentation.adapter.CountryAdapter
import com.example.bookshelf.presentation.viewmodel.SignUpViewModel
import com.example.network.ResultEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:Fragment(R.layout.sign_up_fragment) {
    private var binding:SignUpFragmentBinding? = null
    private val signUpViewModel :SignUpViewModel by viewModels()
    private var countries:List<Country> = listOf()
    private var selectdCountry:Country? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpViewModel.getCountries() // get country list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SignUpFragmentBinding.bind(view)
        observeChanges()
        initUi()
    }
    private fun hideToolbar(){
        (requireActivity() as BookShelfHomePageActivity).hideToolbar()
    }
    private fun observeChanges(){
        signUpViewModel.validationRes.observe(viewLifecycleOwner){
            when(it){
                SignUpFieldValidation.AllOk -> {
                    signUpViewModel.registerUser(getSignUpDataModel())
                }
                is SignUpFieldValidation.InvalidEmail -> {
                    showEmailError(it.msg)
                }
                is SignUpFieldValidation.InvalidName -> {
                    showNameError(it.msg)
                }
                is SignUpFieldValidation.InvalidPassword -> {
                    showPasswordError(it.msg)
                }
            }
        }
        signUpViewModel.regResult.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {
                    //show error msg
                }
                ResultEvent.OnLoading -> {
                    //show loader
                }
                is ResultEvent.OnSuccess -> {
                    goToBookListFragment()
                }
            }
        }
        signUpViewModel.countries.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {}
                ResultEvent.OnLoading -> {}
                is ResultEvent.OnSuccess -> {
                    inflateCountryData()
                }
            }
        }
    }

    /*
       inflate country list --> to show show drop down menu
     */
    private fun inflateCountryData() {
        if(signUpViewModel.countryList.size > 0 ){
           val adapter = CountryAdapter(requireContext(),signUpViewModel.countryList)
            binding?.countryAutoText?.setOnItemClickListener(object:AdapterView.OnItemClickListener{
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onItemClick(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {

                }
            })
            binding?.countryAutoText?.setAdapter(adapter)
        }
    }

    private fun goToBookListFragment() {
        if(requireActivity() is BookShelfHomePageActivity){
            (requireActivity() as BookShelfHomePageActivity).goToBookListFragment(R.id.action_signUpFragment_to_bookListFragment)
        }
    }

    private fun showPasswordError(msg: String) {
        binding?.passwordEditText?.error = msg
    }

    private fun showNameError(msg: String) {
        binding?.fullnameEditText?.error = msg
    }

    private fun showEmailError(msg: String) {
        binding?.emailidEditText?.error = msg
    }

    private fun initUi() {
        setClickListener()
        hideToolbar()
    }


    private fun setClickListener(){
        binding?.registerBtn?.setOnClickListener {
            val validationRes = signUpViewModel.validateSignUpDetails(getSignUpDataModel())
        }
        binding?.signInTv?.setOnClickListener {
            (requireActivity() as BookShelfHomePageActivity).goToSignInFragment(R.id.action_signUpFragment_to_loginFragment)
        }
    }
    private fun getSignUpDataModel():SignUpDataModel{
        return SignUpDataModel(
            fullName = binding?.fullnameEditText?.text?.toString()?:"",
            email = binding?.emailidEditText?.text?.toString()?:"",
            password = binding?.passwordEditText?.text?.toString()?:"",
            country = selectdCountry
        )
    }
}
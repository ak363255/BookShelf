package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookscoremodule.domain.Country
import com.example.bookscoremodule.domain.LoginDataModel
import com.example.bookscoremodule.domain.SignUpDataModel
import com.example.bookscoremodule.domain.User
import com.example.bookshelf.domain.usecase.EmailValidationUseCase
import com.example.bookshelf.domain.usecase.GetCountryListUseCase
import com.example.bookshelf.domain.usecase.LoginUseCase
import com.example.bookshelf.domain.usecase.NameValidationUseCase
import com.example.bookshelf.domain.usecase.PasswordValidationUseCase
import com.example.bookshelf.domain.usecase.RegisterUseCase
import com.example.bookshelf.domain.utils.LoginFieldValidation
import com.example.bookshelf.domain.utils.PasswordValidationUtility
import com.example.bookshelf.domain.utils.SignUpFieldValidation
import com.example.network.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val nameValidationUseCase: NameValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val getCountryListUseCase: GetCountryListUseCase
) :ViewModel() {

    private val _validationRes:MutableLiveData<SignUpFieldValidation<String>> = MutableLiveData()
    val validationRes: LiveData<SignUpFieldValidation<String>> get() = _validationRes

    fun validateSignUpDetails(signUpDataModel: SignUpDataModel) = viewModelScope.launch {
        if(nameValidationUseCase(signUpDataModel.fullName)){
            if(emailValidationUseCase(signUpDataModel.email)){
                if(passwordValidationUseCase(signUpDataModel.password)){
                    _validationRes.postValue(SignUpFieldValidation.AllOk)
                }else{
                     val msg = PasswordValidationUtility.invalidPasswordReason(signUpDataModel.password)
                    _validationRes.postValue(SignUpFieldValidation.InvalidPassword(msg))
                }
            }else{
                _validationRes.postValue(SignUpFieldValidation.InvalidEmail("Invalid Email"))
            }
        }else{
            _validationRes.postValue(SignUpFieldValidation.InvalidName("Invalid name"))
        }
    }

    private val _regResult:MutableLiveData<ResultEvent<User>> = MutableLiveData()
    val regResult:LiveData<ResultEvent<User>> get() = _regResult

    fun registerUser(signUpDataModel: SignUpDataModel) = viewModelScope.launch {
        registerUseCase(signUpDataModel).collectLatest {
            _regResult.postValue(it)
        }
    }

    private val _countries:MutableLiveData<ResultEvent<List<Country>>> = MutableLiveData()
    val countries:LiveData<ResultEvent<List<Country>>> get() = _countries
    fun getCountries() = viewModelScope.launch {
        getCountryListUseCase().collectLatest {
            if(it is ResultEvent.OnSuccess){
                countriesString(it.data)
            }
        }
    }
    var countryList:List<String> = mutableListOf()
    fun countriesString(countries:List<Country>)= viewModelScope.launch {
        val temp = mutableListOf<String>()
        countries.forEach {
            temp.add(it.country)
        }
        countryList = temp
        _countries.postValue(ResultEvent.OnSuccess(countries))
    }
}
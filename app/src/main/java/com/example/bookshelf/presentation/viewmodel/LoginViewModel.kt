package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookscoremodule.domain.LoginDataModel
import com.example.bookscoremodule.domain.User
import com.example.bookshelf.domain.usecase.EmailValidationUseCase
import com.example.bookshelf.domain.usecase.LoginUseCase
import com.example.bookshelf.domain.usecase.PasswordValidationUseCase
import com.example.bookshelf.domain.utils.BookDataStorePreference
import com.example.bookshelf.domain.utils.LoginFieldValidation
import com.example.bookshelf.domain.utils.SingleLiveEvent
import com.example.network.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) :ViewModel() {

    private val _validationRes:MutableLiveData<LoginFieldValidation<String>> = MutableLiveData()
    val validationRes: LiveData<LoginFieldValidation<String>> get() = _validationRes

    fun validateLoginDetails(loginDataModel: LoginDataModel) = viewModelScope.launch {
        if(emailValidationUseCase(loginDataModel.email)){
            if(passwordValidationUseCase(loginDataModel.password)){
                _validationRes.postValue(LoginFieldValidation.AllOk)
            }else{
                _validationRes.postValue(LoginFieldValidation.InvalidPassword("Invalid Password"))
            }
        }else{
            _validationRes.postValue(LoginFieldValidation.InvalidEmail("Invalid Email"))
        }
    }

    private val _loginResult:MutableLiveData<SingleLiveEvent<ResultEvent<User>>> = MutableLiveData()
    val loginResult:LiveData<SingleLiveEvent<ResultEvent<User>>> get() = _loginResult

    fun loginUser(loginDataModel: LoginDataModel) = viewModelScope.launch {
        loginUseCase(loginDataModel).collectLatest {
            _loginResult.postValue(SingleLiveEvent(it))
        }
    }

    private val _isLoggedIn:MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedIn:LiveData<Boolean> get() = _isLoggedIn
    fun isUserLoggedIn() = viewModelScope.launch {
        BookDataStorePreference.getLoginObject().collectLatest {
            if(it != null){
                _isLoggedIn.postValue(true)
            }else{
                _isLoggedIn.postValue(false)
            }
        }
    }
}
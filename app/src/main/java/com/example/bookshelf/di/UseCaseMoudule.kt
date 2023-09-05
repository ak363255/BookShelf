package com.example.bookshelf.di

import com.example.bookshelf.data.ImplEmailMatcher
import com.example.bookshelf.data.ImplGetBookRepo
import com.example.bookshelf.data.ImplGetCountryRepo
import com.example.bookshelf.data.ImplLoginRepo
import com.example.bookshelf.data.ImplNameMatcher
import com.example.bookshelf.data.ImplPasswordMatcher
import com.example.bookshelf.data.ImplSignRegisterUserRepo
import com.example.bookshelf.domain.usecase.EmailValidationUseCase
import com.example.bookshelf.domain.usecase.GetBooksUseCase
import com.example.bookshelf.domain.usecase.GetCountryListUseCase
import com.example.bookshelf.domain.usecase.LoginUseCase
import com.example.bookshelf.domain.usecase.NameValidationUseCase
import com.example.bookshelf.domain.usecase.PasswordValidationUseCase
import com.example.bookshelf.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseMoudule {

    @Provides
    @Singleton
    fun provideSignUpUseCase():RegisterUseCase{
        return RegisterUseCase(ImplSignRegisterUserRepo())
    }

    @Provides
    @Singleton
    fun provideLoginUseCase():LoginUseCase{
        return LoginUseCase(ImplLoginRepo())
    }

    @Provides
    @Singleton
    fun provideEmailValidationUseCae():EmailValidationUseCase{
        return EmailValidationUseCase(ImplEmailMatcher())
    }

    @Provides
    @Singleton
    fun provideNameMatcherUseCase():NameValidationUseCase{
        return NameValidationUseCase(ImplNameMatcher())
    }

    @Provides
    @Singleton
    fun providePasswordMatcherUseCase():PasswordValidationUseCase{
        return PasswordValidationUseCase(ImplPasswordMatcher())
    }

    @Provides
    @Singleton
    fun provideGetBookUseCase():GetBooksUseCase{
        return GetBooksUseCase(ImplGetBookRepo())
    }

    @Provides
    @Singleton
    fun provideCountryListUseCase():GetCountryListUseCase{
        return GetCountryListUseCase(ImplGetCountryRepo())
    }

}
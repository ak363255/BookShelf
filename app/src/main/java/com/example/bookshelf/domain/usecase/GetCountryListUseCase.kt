package com.example.bookshelf.domain.usecase

import com.example.bookshelf.domain.repository.GetCountryRepo

class GetCountryListUseCase(private val countryRepo: GetCountryRepo) {
    suspend operator fun invoke() = countryRepo.getCountryList()
}
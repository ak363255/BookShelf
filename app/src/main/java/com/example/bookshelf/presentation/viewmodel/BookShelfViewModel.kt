package com.example.bookshelf.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.domain.usecase.GetBooksUseCase
import com.example.bookshelf.domain.utils.BookDataStorePreference
import com.example.bookshelf.domain.utils.SingleLiveEvent
import com.example.bookshelf.presentation.fragment.BookListFragment
import com.example.dbmodule.BookDao
import com.example.dbmodule.BookDbHelper
import com.example.network.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase
):ViewModel() {

    private val _books:MutableLiveData<ResultEvent<List<BookModel>>> = MutableLiveData()
    val books: LiveData<ResultEvent<List<BookModel>>> get() = _books
    fun getBooks() = viewModelScope.launch {
        getBooksUseCase().collectLatest {
            _books.postValue(it)
        }
    }
    private val _addToFavorite:MutableLiveData<Boolean> = MutableLiveData()
    val addToFavorite:LiveData<Boolean> get() = _addToFavorite
    fun addToFavorite(bookModel: BookModel) = viewModelScope.launch {
        BookDbHelper.insertBook(bookModel)
        _addToFavorite.postValue(true)
    }
    private val _removeFavorite:MutableLiveData<Boolean> = MutableLiveData()
    val removeFavorite:LiveData<Boolean> get() = _removeFavorite

    fun removeFromFavorite(bookModel: BookModel) = viewModelScope.launch {
       if(bookModel.id != null){
           BookDbHelper.removeBookById(bookModel.id!!)
           _removeFavorite.postValue(true)
       }else{
           _removeFavorite.postValue(false)
       }
    }

    private var sortBy:BookListFragment.SORYBY = BookListFragment.SORYBY.TITLE
    private var asc = false
    private val _isFavBook:MutableLiveData<Boolean> = MutableLiveData()
    val isFavBook:LiveData<Boolean> get() = _isFavBook

    fun isFavoriteBook(id:String) = viewModelScope.launch {
        val book = BookDbHelper.getBookById(id)
        if(book != null){
            _isFavBook.postValue(true)
        }else{
            _isFavBook.postValue(false)
        }
    }

    private val _sortedList:MutableLiveData<SingleLiveEvent<List<BookModel>?>> = MutableLiveData()
     val sortedList:LiveData<SingleLiveEvent<List<BookModel>?>> get() = _sortedList

    fun sortBooks( bookList: MutableList<BookModel>?) = viewModelScope.launch {
        sortBooks(sortBy,asc,bookList)
    }
    fun sortBooks(
        sortBy: BookListFragment.SORYBY,
        asc: Boolean,
        bookList: MutableList<BookModel>?
    )  = viewModelScope.launch{

        var books :MutableList<BookModel>? = bookList?.toMutableList()
        var newList:MutableList<BookModel>? = mutableListOf()
        this@BookShelfViewModel.sortBy = sortBy
        this@BookShelfViewModel.asc = asc
        when(sortBy){
            BookListFragment.SORYBY.TITLE -> {
                if(asc){
                    books?.let {
                        it.sortBy {
                            it.title
                        }
                    }
                }else{
                    books?.let {
                        it.sortByDescending {
                            it.title
                        }
                    }
                }
            }
            BookListFragment.SORYBY.HITS -> {
                if(asc){
                    books?.let {
                        it.sortBy {
                            it.hits
                        }
                    }
                }else{
                    books?.let {
                        it.sortByDescending {
                            it.hits
                        }
                    }
                }
            }
            BookListFragment.SORYBY.FAV -> {
                if(asc){
                    books?.let {
                        it.sortBy {
                            it.isFav
                        }
                    }
                }else{
                    books?.let {
                        it.sortByDescending {
                            it.isFav
                        }
                    }
                }
            }
        }
        _sortedList.postValue(SingleLiveEvent(books))
    }


    fun getSortOrder():Pair<BookListFragment.SORYBY,Boolean>{
        return Pair(sortBy,asc)
    }

    fun setSortBy(sortBy: BookListFragment.SORYBY){
          this.sortBy = sortBy
    }
    fun setAsc(asc:Boolean){
        this.asc = asc
    }

    private val _onLogout:MutableLiveData<Boolean> = MutableLiveData()
    val onLogout:LiveData<Boolean> get() = _onLogout
    fun logout() = viewModelScope.launch {
        BookDataStorePreference.removeLoginObject()
        _onLogout.postValue(true)
    }
}
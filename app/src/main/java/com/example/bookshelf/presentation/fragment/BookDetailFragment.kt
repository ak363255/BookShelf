package com.example.bookshelf.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.R
import com.example.bookshelf.databinding.BookDetailFragmentLayoutBinding
import com.example.bookshelf.presentation.activity.BookShelfHomePageActivity
import com.example.bookshelf.presentation.viewmodel.BookShelfViewModel
import com.example.foodrecipes.domain.utils.Utility
import com.example.network.ImageLoaderHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment(R.layout.book_detail_fragment_layout) {
    var binding:BookDetailFragmentLayoutBinding? = null
    private var bookModel:BookModel? = null
    private var isFavBook = false
    private val bookShelfViewModel:BookShelfViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readArgument()
    }

    private fun readArgument() {
        arguments?.let {
            if(it.containsKey("data")){
                bookModel = it.getParcelable("data")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookDetailFragmentLayoutBinding.bind(view)
        initUi()
        observeChanges()
    }

    private fun observeChanges() {
        bookShelfViewModel.isFavBook.observe(viewLifecycleOwner){
            isFavBook = it
        }
        bookShelfViewModel.removeFavorite.observe(viewLifecycleOwner){
            if(it){
                bookModel?.isFav = false
                setFavIcon()
            }
        }
        bookShelfViewModel.addToFavorite.observe(viewLifecycleOwner){
            if(it){
                bookModel?.isFav = true
                setFavIcon()
            }
        }
    }

    private fun hideToolbar() {
        (requireActivity() as BookShelfHomePageActivity).hideToolbar()
    }

    private fun initUi() {
        hideToolbar()
        setTitle()
        setDescription()
        setImage()
        setFavIcon()

    }

    private fun setFavIcon() {
        isFavBook = bookModel?.isFav?:false
        var drawable = ContextCompat.getDrawable(requireContext(),R.drawable.fav_icon_empty)
        if(isFavBook){
            drawable = ContextCompat.getDrawable(requireContext(),R.drawable.fav_icon_filled)
            drawable?.setTint(ContextCompat.getColor(requireContext(),R.color.clr_d8232a))
        }
        binding?.favicon?.setImageDrawable(drawable)
        binding?.favicon?.setOnClickListener {
            if(isFavBook){
                bookModel?.id?.let {
                    bookShelfViewModel.removeFromFavorite(bookModel!!)
                }
            }else{
                bookModel?.id?.let {
                    bookShelfViewModel.addToFavorite(bookModel!!)
                }
            }
        }

    }


    private fun setTitle() {
        bookModel?.let {book ->
            book.alias?.let {
                binding?.bookTitle?.text = "Alias: ${it}"
            }
            binding?.likeView?.icon?.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.target))
            binding?.likeView?.iconDesc?.text = "Hits :${bookModel?.hits?:0}"
            binding?.likeView?.iconDesc?.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

        }
    }

    private fun setDescription() {
        bookModel?.let {book ->
            book.title?.let {
                binding?.description?.let {textView ->
                    Utility.setHtmlText(textView,it)
                }
            }
        }
    }

    private fun setImage() {
       bookModel?.let {book ->
            book.image?.let {url ->
                binding?.imageView?.let {imageView ->
                    ImageLoaderHelper.loadImage(imageView,url)
                }
            }
        }
    }


    companion object{
        val DATA = "book"
    }
}
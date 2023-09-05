package com.example.bookshelf.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.R
import com.example.bookshelf.databinding.BookListFragmentLayoutBinding
import com.example.bookshelf.presentation.activity.BookShelfHomePageActivity
import com.example.bookshelf.presentation.adapter.BookListAdapter
import com.example.bookshelf.presentation.viewmodel.BookShelfViewModel
import com.example.network.ResultEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class BookListFragment :Fragment(R.layout.book_list_fragment_layout) {

    private var binding:BookListFragmentLayoutBinding? = null
    private var bookListAdapter:BookListAdapter? = null
    private val bookShelfViewModel:BookShelfViewModel by viewModels()
    private var currentPostion = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookShelfViewModel.getBooks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookListFragmentLayoutBinding.bind(view)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressDispatcher)
        initUi()
        observeChanges()
    }

    private fun observeChanges() {
        bookShelfViewModel.books.observe(viewLifecycleOwner){
            when(it){
                is ResultEvent.OnFailure -> {}
                ResultEvent.OnLoading -> {}
                is ResultEvent.OnSuccess -> {
                    onSuccessOfBooksData(it.data)
                }
            }
        }
        bookShelfViewModel.addToFavorite.observe(viewLifecycleOwner){
            if(it){
                bookListAdapter?.differ?.currentList?.get(currentPostion)?.isFav = true
                bookListAdapter?.notifyItemChanged(currentPostion)
            }
        }
        bookShelfViewModel.removeFavorite.observe(viewLifecycleOwner){
            if(it){
                bookListAdapter?.differ?.currentList?.get(currentPostion)?.isFav = false
                bookListAdapter?.notifyItemChanged(currentPostion)
            }
        }
        bookShelfViewModel.sortedList.observe(viewLifecycleOwner){
            if(it != null && it.size > 0){
                bookListAdapter?.differ?.submitList(it)
                CoroutineScope(Dispatchers.IO + Job()).launch {
                    delay(200)
                    withContext(Dispatchers.Main){
                        binding?.booksRv?.scrollToPosition(0)
                    }
                }
            }
        }
    }

    private fun onSuccessOfBooksData(data: List<BookModel>) {
        if(data.size > 0){
                bookListAdapter?.differ?.submitList(data)
        }
    }

    private fun initUi(){
        setBookRv()
        showToolbar()
        setSortByUi()
    }
    private fun setSortByUi() {

       binding?.sortView?.sortByTitle?.contentTitle?.text = "Title"
       binding?.sortView?.sortByHits?.contentTitle?.text = "Hits"
       binding?.sortView?.sortByFav?.contentTitle?.text = "Fav"
        binding?.sortView?.sortByTitle?.parent?.tag = "0"
        binding?.sortView?.sortByFav?.parent?.tag = "0"
        binding?.sortView?.sortByHits?.parent?.tag = "0"

        binding?.sortView?.sortByTitle?.parent?.setOnClickListener {
            val tag = binding?.sortView?.sortByTitle?.parent?.tag
            setToDefault()
            val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.circle_bg_d8d8d8)

            var color = R.color.clr_d7d7d7
            drawable?.setTint(ContextCompat.getColor(requireContext(),color))
            if(tag == "0"){
                binding?.sortView?.sortByTitle?.parent?.tag = "1"
                 color = R.color.clr_32B1A5
            }else{
                binding?.sortView?.sortByTitle?.parent?.tag = "0"

            }
            drawable?.setTint(ContextCompat.getColor(requireContext(),color))
            binding?.sortView?.sortByTitle?.tickImage?.setImageDrawable(drawable)
        }
        binding?.sortView?.sortByHits?.parent?.setOnClickListener {
            val tag = binding?.sortView?.sortByHits?.parent?.tag
            setToDefault()
            val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.circle_bg_d8d8d8)

            var color = R.color.clr_d7d7d7
            drawable?.setTint(ContextCompat.getColor(requireContext(),color))
            if(tag == "0"){
                binding?.sortView?.sortByHits?.parent?.tag = "1"
                color = R.color.clr_32B1A5
            }else{
                binding?.sortView?.sortByHits?.parent?.tag = "0"
            }
            drawable?.setTint(ContextCompat.getColor(requireContext(),color))
            binding?.sortView?.sortByHits?.tickImage?.setImageDrawable(drawable)

        }
        binding?.sortView?.sortByFav?.parent?.setOnClickListener {
            val tag = binding?.sortView?.sortByFav?.parent?.tag
            setToDefault()
            val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.circle_bg_d8d8d8)
            var color = R.color.clr_d7d7d7
            drawable?.setTint(ContextCompat.getColor(requireContext(),color))
            if(tag == "0"){
                binding?.sortView?.sortByFav?.parent?.tag = "1"
                color = R.color.clr_32B1A5
            }else{
                binding?.sortView?.sortByFav?.parent?.tag = "0"

            }
            drawable?.setTint(ContextCompat.getColor(requireContext(),color))
            binding?.sortView?.sortByFav?.tickImage?.setImageDrawable(drawable)
        }
        binding?.sortView?.applyTv?.setOnClickListener {
             onApplyButtonClicked()
        }

    }

    private fun onApplyButtonClicked() {
        val sortByFav = binding?.sortView?.sortByFav?.parent?.tag == "1"
        if(sortByFav){
            bookShelfViewModel.sortBooks(SORYBY.FAV,binding?.sortView?.ascSwitch?.isChecked?:false,bookListAdapter?.differ?.currentList)
        }else if(binding?.sortView?.sortByHits?.parent?.tag=="1"){
            bookShelfViewModel.sortBooks(SORYBY.HITS,binding?.sortView?.ascSwitch?.isChecked?:false,bookListAdapter?.differ?.currentList)
        }else{
            bookShelfViewModel.sortBooks(SORYBY.TITLE,binding?.sortView?.ascSwitch?.isChecked?:false,bookListAdapter?.differ?.currentList)
        }
    }

    private fun setToDefault(){
        var color = R.color.clr_d7d7d7
        val drawable = ContextCompat.getDrawable(requireContext(),R.drawable.circle_bg_d8d8d8)
        drawable?.setTint(ContextCompat.getColor(requireContext(),color))
        binding?.sortView?.sortByTitle?.tickImage?.setImageDrawable(drawable)
        binding?.sortView?.sortByHits?.tickImage?.setImageDrawable(drawable)
        binding?.sortView?.sortByFav?.tickImage?.setImageDrawable(drawable)
        binding?.sortView?.sortByTitle?.parent?.tag = "0"
        binding?.sortView?.sortByHits?.parent?.tag = "0"
        binding?.sortView?.sortByFav?.parent?.tag = "0"
    }
    private fun setBookRv() {
        binding?.let {binding ->
            bookListAdapter = BookListAdapter(object :BookListAdapter.Callback{
                override fun onItemClikced(data: BookModel,pos:Int) {
                    currentPostion = pos
                    goToBookDetailPage(data)
                }

                override fun onFavIconClicked(data: BookModel,pos:Int) {
                    currentPostion = pos
                    markFavorite(data,pos)
                }

            })
            binding.booksRv.apply {
                adapter = bookListAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
            }
        }
    }

    private fun markFavorite(data: BookModel, pos: Int) {
              if(data.isFav){
                  bookShelfViewModel.removeFromFavorite(data)
              }else{
                   bookShelfViewModel.addToFavorite(data)
              }
    }

    private fun goToBookDetailPage(data: BookModel) {
         val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(data)
        (requireActivity() as BookShelfHomePageActivity).goToBookDetailFragment(data,action)
    }

    private fun showToolbar(){
        (requireActivity() as BookShelfHomePageActivity).showToolbar()
    }

    var backPressDispatcher = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isEnabled) {
                val backStackEntryCount = requireActivity().supportFragmentManager.fragments.size
                if (backStackEntryCount <= 1)
                    requireActivity().finish()
                else {
                        requireActivity().supportFragmentManager.popBackStackImmediate()
                }
            } else {
                requireActivity().onBackPressed()
            }
        }
    }

    enum class SORYBY{
        TITLE,HITS,FAV
    }
}
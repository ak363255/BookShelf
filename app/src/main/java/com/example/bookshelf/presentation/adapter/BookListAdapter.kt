package com.example.bookshelf.presentation.adapter

import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.R
import com.example.bookshelf.databinding.BookItemBinding
import com.example.bookshelf.domain.utils.CommonAdapter
import com.example.foodrecipes.domain.utils.Utility
import com.example.network.ImageLoaderHelper


class BookListAdapter(
    val itemClickCallBack:Callback
) : CommonAdapter<BookModel>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val binding  = viewHolder.viewbinding as BookItemBinding?
        binding?.let {
            val data = differ.currentList[position]
            setData(it,data,position,viewHolder)
        }
    }
    private fun setData(
        recipeItemBinding: BookItemBinding,
        data: BookModel,
        position: Int,
        viewHolder: ViewHolder
    ) {
        Utility.setHtmlText(recipeItemBinding.recipeDesc,data.title)
        recipeItemBinding.recipeTitle.text = data.title
        val drawable1 = ContextCompat.getDrawable(recipeItemBinding.root.context, R.drawable.target)
        recipeItemBinding.likeView.icon.setImageDrawable(drawable1)
        recipeItemBinding.likeView.iconDesc.text = data.hits?.toString()
        recipeItemBinding.likeView.iconDesc.setTextColor(ContextCompat.getColor(recipeItemBinding.root.context,R.color.clr_d8232a))
        if(!TextUtils.isEmpty(data.image)){
            ImageLoaderHelper.loadImage(recipeItemBinding.recipeImage,data.image)
        }
        if(data.isFav){
            val drawable = ContextCompat.getDrawable(recipeItemBinding.root.context,R.drawable.fav_icon_filled)
            drawable?.setTint(ContextCompat.getColor(recipeItemBinding.root.context,R.color.clr_d8232a))
            recipeItemBinding.favIcon.setImageDrawable(drawable)
        }else{
            val drawable = ContextCompat.getDrawable(recipeItemBinding.root.context,R.drawable.fav_icon_empty)
            drawable?.setTint(ContextCompat.getColor(recipeItemBinding.root.context,R.color.clr_303030))
            recipeItemBinding.favIcon.setImageDrawable(drawable)
        }
        recipeItemBinding.root.setOnClickListener {
            itemClickCallBack.onItemClikced(data,viewHolder.adapterPosition)
        }
        recipeItemBinding.favIcon.setOnClickListener {
            itemClickCallBack.onFavIconClicked(data,viewHolder.adapterPosition)
        }

        recipeItemBinding.executePendingBindings()
    }

    override fun getlayout(position: Int): Int {
        return R.layout.book_item
    }

    interface Callback{
        fun onItemClikced(data:BookModel,pos:Int)
        fun onFavIconClicked(data: BookModel,pos:Int)
    }
}

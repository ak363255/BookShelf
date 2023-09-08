package com.example.bookshelf.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bookscoremodule.domain.BookModel
import com.example.bookshelf.R
import com.example.bookshelf.databinding.BookShelfHomePageLayoutBinding
import com.example.bookshelf.presentation.viewmodel.BookShelfViewModel
import com.example.foodrecipes.domain.utils.Utility.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookShelfHomePageActivity : AppCompatActivity() {
    private var binding: BookShelfHomePageLayoutBinding? = null
    private var navController: NavController? = null
    private val bookShelfViewModel:BookShelfViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.book_shelf_home_page_layout)
        setSupportActionBar(null)
        setNavigationView()
        observeChanges()
    }

    private fun observeChanges(){
        bookShelfViewModel.onLogout.observe(this){
            if(it){
                reloadBookShelf()
                showLogoutMessage()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navController?.setGraph(R.navigation.book_nav_graph)
    }

    private fun reloadBookShelf() {
        val intent = Intent(this,BookShelfHomePageActivity::class.java)
        startActivity(intent)
    }

    private fun showLogoutMessage() {
        binding?.root?.let {
            this.showSnackBar(it,"Logout Success",R.color.clr_d8232a)
        }
    }

    private fun setNavigationView() {
        binding?.let { binding ->
            val navHostFragment =
                supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)
            navHostFragment?.let { navHostFragment ->
                navController = navHostFragment.findNavController()
                navController?.let { navController ->
                    setSupportActionBar(binding.toolBar)
                }
            }
        }
    }

    fun hideToolbar(){
        binding?.toolBar?.visibility = View.GONE
    }
    fun showToolbar(){
        binding?.toolBar?.visibility = View.VISIBLE
    }
    fun setToolbarTilte(title:String){
        binding?.toolBar?.title = title
    }

    fun goToSignUpFragment(action:Int){
        navController?.navigate(action)
    }

    fun goToBookListFragment(action:Int) {
        navController?.navigate(action)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp()?:false || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInfalter = menuInflater
        menuInfalter.inflate(R.menu.menu_layout,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.loguot){
            bookShelfViewModel.logout()
        }
        return true
    }

    fun goToBookDetailFragment(data: BookModel, action: NavDirections) {
        navController?.navigate(action)
    }

    fun goToSignInFragment(action: Int) {
        navController?.navigate(action)
    }

}
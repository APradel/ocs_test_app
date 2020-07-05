package com.example.ocstestapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.ocstestapp.R
import com.example.ocstestapp.ui.home.MainActivityFragment
import com.example.ocstestapp.ui.base.BaseActivity
import com.example.ocstestapp.ui.searchresult.SearchResultFragment
import com.example.ocstestapp.utils.showDebugMessage

private const val CURRENT_FRAGMENT_KEY = "currentFragment"
private const val SEARCH_VIEW_TEXT_KEY = "searchViewText"
private const val SEARCH_VIEW_FOCUS_KEY = "searchViewFocus"

class MainActivity : BaseActivity()
{
    //region VARIABLES
    override val activityLayoutID: Int = R.layout.activity_main

    private enum class CurrentFragment { HOME, SEARCH_RESULT }
    //This variable keep the current fragment displayed
    private var currentFragment: CurrentFragment = CurrentFragment.HOME
    //This variable is intended to keep the user search query when the activity is restored
    private var searchQueryRestored: String? = null
    //This variable is intended to keep if the search view was focused when the activity is restored
    private var searchFocusRestored: Boolean? = null
    private var searchView: SearchView? = null
//endregion

    override fun onSaveInstanceState(outState: Bundle) {
        //Save all data require when the UI while be recreated if it's killed and restarted
        outState.putSerializable(CURRENT_FRAGMENT_KEY, currentFragment)
        outState.putString(SEARCH_VIEW_TEXT_KEY, searchView?.query.toString())
        outState.putBoolean(SEARCH_VIEW_FOCUS_KEY, searchView?.hasFocus() ?: false)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        //Restore all data saved inside the onSaveInstanceState function
        currentFragment = savedInstanceState.getSerializable(CURRENT_FRAGMENT_KEY)
                as CurrentFragment
        searchQueryRestored = savedInstanceState.getString(SEARCH_VIEW_TEXT_KEY)
        searchFocusRestored = savedInstanceState.getBoolean(SEARCH_VIEW_FOCUS_KEY)
    }

    override fun handleIntent(intent: Intent) {
        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                onSearchQueryReceived(query)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setOnCloseListener {
                showDebugMessage(this@MainActivity, "Closing ")
                true
            }
        }
        val searchMenuItem: MenuItem = menu.findItem(R.id.search)
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                showDebugMessage(this@MainActivity, "Opening ")
                searchView?.apply {
                    onActionViewExpanded()
                    setQuery(searchQueryRestored, false)
                    if (false == searchFocusRestored)
                    {
                        clearFocus()
                    }
                }
                searchQueryRestored = null //The query restored is not useful anymore
                searchFocusRestored = null
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                goToHomePage()
                // This is required in order to update correctly the option menu after opening the
                // search view programmatically
                invalidateOptionsMenu()
                return true
            }
        })
        //The search view is expanded directly if a valid query was restored
        if(null != searchQueryRestored && 0 < searchQueryRestored?.length!!)
        {
            searchMenuItem.expandActionView()
        }
        return true
    }

    override fun launchMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, MainActivityFragment.newInstance())
            .commitNow()
        currentFragment = CurrentFragment.HOME
    }

    private fun launchResearchFragment(query: String?)
    {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, SearchResultFragment.newInstance(query))
            .commitNow()
        currentFragment = CurrentFragment.SEARCH_RESULT
    }

    private fun onSearchQueryReceived(query: String)
    {
        showDebugMessage(this, "The query is: $query")
        launchResearchFragment(query)
    }

    private fun onBackButtonClicked()
    {

    }

    private fun goToHomePage()
    {
        showDebugMessage(this@MainActivity, "Opening main menu ", true)
        launchMainFragment()
    }
}

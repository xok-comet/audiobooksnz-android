package com.audiobookz.nz.app.browse

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.audiobookz.nz.app.R
import com.audiobookz.nz.app.browse.categories.ui.CategoryFragment
import com.audiobookz.nz.app.browse.featured.ui.FeaturedFragment
import com.audiobookz.nz.app.di.Injectable
import kotlinx.android.synthetic.main.fragment_browse.view.*

class BrowseFragment : Fragment(), Injectable {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_browse, container, false)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FeaturedFragment(), "Featured")
        adapter.addFragment(CategoryFragment(), "Category")
        rootView.tab_view_pager.adapter = adapter
        rootView.tab_browse.setupWithViewPager(rootView.tab_view_pager)
        setHasOptionsMenu(true)

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_search, menu)
        val searchView: SearchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("TAG", "onQueryTextChange: "+newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("TAG", "onQueryTextChange: "+query)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(
            fragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
        private val fragments: ArrayList<Fragment> = ArrayList<Fragment>()
        private val titles: ArrayList<String> = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

    }

    companion object {
        fun newInstance(): BrowseFragment =
            BrowseFragment()
    }
}

package com.example.codechallenge.ui.show

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.codechallenge.DataAdapter
import com.example.codechallenge.R
import com.example.codechallenge.databinding.FragmentShowBinding

class ShowFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener
{
    private var keyword = ""
    private val showViewModel: ShowViewModel by lazy {
        ViewModelProvider(this).get(ShowViewModel::class.java)
    }
    private var _binding: FragmentShowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding = FragmentShowBinding.inflate(inflater)

// Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

// Giving the bindihng access to the OverViewodel
        binding.viewModel = showViewModel

        binding.showGrid.adapter = DataAdapter(DataAdapter.OnClickListener{
            showViewModel.displayDataDetails(it)
        })
        showViewModel.selectedData.observe(viewLifecycleOwner, Observer {
            if(it !=null){
                this.findNavController().navigate(
                    ShowFragmentDirections.actionShowDetail(it))
                showViewModel.displayDataDetailComplete()
            }
        })

        return binding.root
    }

    private var searchView: androidx.appcompat.widget.SearchView? = null
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home, menu)
        searchView = menu.findItem(R.id.action_search)?.let {
            it.actionView as? androidx.appcompat.widget.SearchView
        }?.apply {
            (activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager)?.also {
                setSearchableInfo(it.getSearchableInfo(activity?.componentName))
            }
            maxWidth = Int.MAX_VALUE

            setIconifiedByDefault(false)
            isIconified = false
            isSubmitButtonEnabled = false
            setOnQueryTextListener(this@ShowFragment)
            requestFocus()
            layoutParams = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return query?.let {
            if (it.length < 2) { showViewModel.reset() }
            keyword = it
            showViewModel.updateKeyword(keyword)
            searchView?.clearFocus()
            true
        } ?: false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return newText?.let {
            if (it.length < 2) { showViewModel.reset() }
            true
        } ?: true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
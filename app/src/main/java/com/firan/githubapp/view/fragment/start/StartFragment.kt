package com.firan.githubapp.view.fragment.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firan.githubapp.R
import com.firan.githubapp.usecase.common.*
import com.firan.githubapp.usecase.connection.ConnectionState
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * author: Artur Godlewski
 * as a bonus I implemented offline warning while no cellular or wifi connection is available, please check :)
 * also you can refresh list by swipe down
 */
class StartFragment : Fragment(), StartFragmentListAdapter.OnRepoInteractionListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: StartFragmentViewModel by viewModel()
    val fetcherListener: FetchingIdlingResource = FetchingIdlingResource()
    private var alreadyLoaded = false

    private val listAdapter by lazy {
        StartFragmentListAdapter(
            emptyList(),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    /**
     * reset search input when resumed
     */
    override fun onResume() {
        super.onResume()
        repoNameValue.setText("")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pullToRefresh.setOnRefreshListener(this)
        initItemsList()
        initObservers()

        /**
         * this is for one-time call to webservice while checking the details and going back to
         * list
         * the last 2 lines could be also placed into onResume to perform refresh each time we are
         * back in to main screen
         */
        if (savedInstanceState == null && !alreadyLoaded) {
            alreadyLoaded = true
            fetcherListener.beginFetching()
            viewModel.onRefresh()
        }
    }

    private fun initItemsList() {
        val linearLayoutManager = LinearLayoutManager(activity)
        searchItems.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = listAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    linearLayoutManager.orientation
                )
            )
        }
    }

    private fun initObservers() {
        viewModel.repositories.observe(viewLifecycleOwner, Observer { githubRepositories ->
            viewModel.filteredRepositories.postValue(githubRepositories)
        })

        viewModel.apiError.observe(this, Observer { throwable ->
            throwable?.let { error ->
                Toast.makeText(
                    requireContext(),
                    FormatError.perform(error, requireContext()),
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        viewModel.filteredRepositories.observe(viewLifecycleOwner, Observer { contacts ->
            listAdapter.setItems(contacts ?: listOf())
            val itemsVisible = listAdapter.itemCount > 0 || !repoNameValue.text.isNullOrEmpty()
            searchItems.isVisible = itemsVisible
            emptyView.isVisible = !itemsVisible
        })

        /**
         * for steering the progress dialog display
         */
        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer { isRefreshing ->
            pullToRefresh.isRefreshing = false
            if (isRefreshing) {
                showProgressDialog(requireContext())
            } else {
                hideProgressDialog()
                fetcherListener.doneFetching()
            }
        })

        /**
         * for offline hint steering
         */
        viewModel.connection.observe(viewLifecycleOwner, Observer { connectionState ->
            if (connectionState == ConnectionState.Online) {
                removeOfflineModeInfo()
            } else {
                showOfflineModeInfo()
            }
        })

        repoNameValue.addTextChangedListener(object : OnChangeTextWatcher() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.filter(s.toString())
            }
        })
    }

    /**
     * for close keyboard when you are filtering and then click on list item without closing keyboard
     * manually
     */
    override fun onStop() {
        super.onStop()
        if (view != null) {
            val imm =
                requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    override fun onClickRepo(repoId: Int) {
        findNavController().navigate(
            StartFragmentDirections.actionStartFragmentToDetailsFragment(
                repoId
            )
        )
    }

    /**
     * implementation for swipe to refresh interface
     */
    override fun onRefresh() = viewModel.onRefresh()

    private fun showOfflineModeInfo() {
        if (offline_row != null) {
            val animation =
                AnimationUtils.loadAnimation(offline_row.context, android.R.anim.slide_in_left)
            if (offline_row.visibility == View.GONE) {
                offline_row.visibility = View.VISIBLE
                offline_row.startAnimation(animation)
            }
        }
    }

    private fun removeOfflineModeInfo() {
        if (offline_row != null) {
            val animation =
                AnimationUtils.loadAnimation(offline_row.context, android.R.anim.slide_out_right)
            if (offline_row.visibility == View.VISIBLE) {
                onRefresh()
                offline_row.startAnimation(animation)
                offline_row.visibility = View.GONE
            }
        }
    }
}

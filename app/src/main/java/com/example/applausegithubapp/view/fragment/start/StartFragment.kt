package com.example.applausegithubapp.view.fragment.start

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
import com.example.applausegithubapp.R
import com.example.applausegithubapp.usecase.common.FormatError
import com.example.applausegithubapp.usecase.common.OnChangeTextWatcher
import com.example.applausegithubapp.usecase.common.hideProgressDialog
import com.example.applausegithubapp.usecase.common.showProgressDialog
import com.example.applausegithubapp.usecase.connection.ConnectionState
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartFragment : Fragment(), StartFragmentListAdapter.OnRepoInteractionListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: StartFragmentViewModel by viewModel()

    private val listAdapter by lazy {
        StartFragmentListAdapter(
            emptyList(),
            this
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.onRefresh()
        repoNameValue.setText("")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pullToRefresh.setOnRefreshListener(this)
        initItemsList()
        initObservers()
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

        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer { isRefreshing ->
            pullToRefresh.isRefreshing = false
            if (isRefreshing) {
                showProgressDialog(requireContext())
            } else {
                hideProgressDialog()
            }
        })

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

    override fun onRefresh() = viewModel.onRefresh()

    private fun showOfflineModeInfo() {
        if (offline_row != null) {
            val animation = AnimationUtils.loadAnimation(offline_row.context, android.R.anim.slide_in_left)
            if (offline_row.visibility == View.GONE) {
                offline_row.visibility = View.VISIBLE
                offline_row.startAnimation(animation)
            }
        }
    }

    private fun removeOfflineModeInfo() {
        if (offline_row != null) {
            val animation = AnimationUtils.loadAnimation(offline_row.context, android.R.anim.slide_out_right)
            if (offline_row.visibility == View.VISIBLE) {
                onRefresh()
                offline_row.startAnimation(animation)
                offline_row.visibility = View.GONE
            }
        }
    }
}

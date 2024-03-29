package com.firan.githubapp.view.fragment.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.firan.githubapp.R
import com.firan.githubapp.data.entity.GithubItem
import com.firan.githubapp.view.fragment.interfaces.ChildFragment
import com.firan.githubapp.view.fragment.interfaces.FlatActionBar
import kotlinx.android.synthetic.main.details_item.view.*
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * author: Artur Godlewski
 */
class DetailsFragment : Fragment(), ChildFragment, FlatActionBar {

    private val viewModel: DetailsFragmentViewModel by viewModel {
        val args = DetailsFragmentArgs.fromBundle(arguments!!)
        parametersOf(args.repoId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        viewModel.repository.observe(viewLifecycleOwner, Observer { githubRepository ->
            renderRepositoryDetails(githubRepository)
        })
    }

    private fun renderRepositoryDetails(githubRepository: GithubItem) {
        name.text = githubRepository.name

        description.txt_title.text = getString(R.string.description)
        description.txt_value.text = githubRepository.description

        identifier.txt_title.text = getString(R.string.identifier)
        identifier.txt_value.text = githubRepository.identifier

        url.txt_title.text = getString(R.string.url)
        url.txt_value.text = githubRepository.url

        created.txt_title.text = getString(R.string.created)
        created.txt_value.text = githubRepository.createdAt.toString()

        updated.txt_title.text = getString(R.string.updated)
        updated.txt_value.text = githubRepository.updatedAt.toString()

        isprivate.txt_title.text = getString(R.string.isprivate)
        isprivate.txt_value.text = githubRepository.private.toString()

        hasissues.txt_title.text = getString(R.string.hasissues)
        hasissues.txt_value.text = githubRepository.hasIssues.toString()

        language.txt_title.text = getString(R.string.language)
        language.txt_value.text = githubRepository.language
    }
}

package com.example.applausegithubapp.view.fragment.start

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applausegithubapp.R
import com.example.applausegithubapp.data.to.NameTuple
import com.example.applausegithubapp.usecase.common.setSafeOnClickListener
import kotlinx.android.synthetic.main.listitem_search.view.*

class StartFragmentListAdapter(
    private var dataset: List<NameTuple>,
    private val listener: OnRepoInteractionListener
) : RecyclerView.Adapter<StartFragmentListAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = dataset[position]
        holder.view.txt_name.text = repo.name
        holder.view.setSafeOnClickListener {
            listener.onClickRepo(dataset[position].rid)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun setItems(items: List<NameTuple>) {
        dataset = items
        notifyDataSetChanged()
    }

    interface OnRepoInteractionListener {
        fun onClickRepo(repoId: Int)
    }
}

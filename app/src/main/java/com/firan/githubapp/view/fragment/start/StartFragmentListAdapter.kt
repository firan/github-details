package com.firan.githubapp.view.fragment.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firan.githubapp.R
import com.firan.githubapp.data.to.NameTuple
import com.firan.githubapp.usecase.common.setSafeOnClickListener
import kotlinx.android.synthetic.main.listitem_search.view.*

/**
 * author: Artur Godlewski
 */
class StartFragmentListAdapter(
    private var data: List<NameTuple>,
    private val listener: OnRepoInteractionListener
) : RecyclerView.Adapter<StartFragmentListAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.txt_name.text = data[position].name
        /**
         * check safeOnClickListener - it is very useful against quick clicking manual testers
         */
        holder.view.setSafeOnClickListener {
            listener.onClickRepo(data[position].rid)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setItems(items: List<NameTuple>) {
        data = items
        notifyDataSetChanged()
    }

    interface OnRepoInteractionListener {
        fun onClickRepo(repoId: Int)
    }
}

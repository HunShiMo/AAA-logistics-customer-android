package xyz.rayisbest.userlogisticssystem.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.logic.bean.Branch

class BranchAdapter(private val branchList: List<Branch>): RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    inner class BranchViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val branchNameTextView = item.findViewById<TextView>(R.id.branchNameTextView)!!
        val branchAddressTextView = item.findViewById<TextView>(R.id.branchAddressTextView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.branch_item, parent, false)
        val viewHolder = BranchViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        val branch = branchList[position]
        holder.branchNameTextView.text = branch.branchName
        holder.branchAddressTextView.text = branch.address
    }


}
package xyz.rayisbest.userlogisticssystem.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.logic.bean.Address

class AddressAdapter(private val addressList: List<Address>): RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val addressUserName = item.findViewById<TextView>(R.id.addressUserName)!!
        val addressPhone = item.findViewById<TextView>(R.id.addressPhone)!!
        val addressDetail = item.findViewById<TextView>(R.id.addressDetail)!!
        val addressDefault = item.findViewById<TextView>(R.id.addressDefault)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_item, parent, false)
        val viewHolder = AddressViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val branch = addressList[position]
        holder.addressUserName.text = branch.name
        holder.addressPhone.text = branch.phoneNum
        holder.addressDetail.text = branch.province + branch.city + branch.district + branch.street + branch.addressDetail
        holder.addressDefault.visibility = View.INVISIBLE


    }


}
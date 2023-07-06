package xyz.rayisbest.userlogisticssystem.logic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.logic.bean.Order
import xyz.rayisbest.userlogisticssystem.logic.bean.PackageInfo

class PackageInfoAdapter(private val orderList: List<PackageInfo>):RecyclerView.Adapter<PackageInfoAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val waybillIdLabel = item.findViewById<TextView>(R.id.waybillIdLabel)!!
        val waybillIdTextView = item.findViewById<TextView>(R.id.waybillIdTextview)!!
        val senderCityNameTextView = item.findViewById<TextView>(R.id.senderCityNameTextview)!!
        val senderNameTextView = item.findViewById<TextView>(R.id.senderNameTextView)!!
        val receiverCityNameTextView = item.findViewById<TextView>(R.id.receiverCityNameTextview)!!
        val receiverNameTextView = item.findViewById<TextView>(R.id.receiverNameTextview)!!
        val nowStatusLabel = item.findViewById<TextView>(R.id.nowStatusLabel)!!
        val nowStatusTextView = item.findViewById<TextView>(R.id.nowStatusTextview)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        val viewHolder = OrderViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val packageInfo = orderList[position]
        holder.waybillIdTextView.text = packageInfo.waybill.waybillId.toString()
        holder.senderCityNameTextView.text = packageInfo.senderAddress.city
        holder.senderNameTextView.text = packageInfo.senderAddress.name
        holder.receiverCityNameTextView.text = packageInfo.receiverAddress.city
        holder.receiverNameTextView.text = packageInfo.receiverAddress.name
        holder.nowStatusTextView.text = packageInfo.waybill.waybillStatus
    }

}
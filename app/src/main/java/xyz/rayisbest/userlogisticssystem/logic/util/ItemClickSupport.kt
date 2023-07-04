package xyz.rayisbest.userlogisticssystem.logic.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R

class ItemClickSupport private constructor(private val mRecyclerView: RecyclerView) {
    private var mOnItemClickListener: OnItemClickListener? = null
    private val childListenerMap = hashMapOf<Int, OnChildClickListener>()
    private var mOnItemLongClickListener: OnItemLongClickListener? = null

    private val mAttachListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            //item 点击
            if (mOnItemClickListener != null) {
                view.setOnClickListener { v ->
                    if (mOnItemClickListener != null) {
                        val holder = mRecyclerView.findContainingViewHolder(v)
                        if (holder != null && holder.bindingAdapterPosition != -1) {
                            mOnItemClickListener?.onItemClicked(mRecyclerView, holder.bindingAdapterPosition, v)
                        }
                    }
                }
            }

            //子 View 点击
            if (childListenerMap.isNotEmpty()) {
                for (key in childListenerMap.keys) {
                    view.findViewById<View>(key)?.setOnClickListener { v ->
                        val holder = mRecyclerView.findContainingViewHolder(v)
                        if (holder != null && holder.bindingAdapterPosition != -1) {
                            childListenerMap[key]!!.onChildClicked(mRecyclerView, holder.bindingAdapterPosition, v)
                        }
                    }
                }
            }

            //Item 长按点击
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(View.OnLongClickListener { v ->
                    if (mOnItemLongClickListener != null) {
                        val holder = mRecyclerView.getChildViewHolder(v)
                        return@OnLongClickListener mOnItemLongClickListener!!.onItemLongClicked(mRecyclerView, holder.adapterPosition, v)
                    }
                    false
                })
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) {

        }
    }

    init {
        mRecyclerView.setTag(R.id.item_click_support, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }


    fun addOnChildClickListener(resId: Int, listener: OnChildClickListener): ItemClickSupport {
        childListenerMap[resId] = listener
        return this
    }

    fun addOnItemClickListener(listener: OnItemClickListener): ItemClickSupport {
        mOnItemClickListener = listener
        return this
    }

    fun addOnItemLongClickListener(listener: OnItemLongClickListener): ItemClickSupport {
        mOnItemLongClickListener = listener
        return this
    }

    private fun detach(view: RecyclerView) {
        view.removeOnChildAttachStateChangeListener(mAttachListener)
        view.setTag(R.id.item_click_support, null)
    }

    // 子 View点击接口
    interface OnChildClickListener {
        fun onChildClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    // 点击接口
    interface OnItemClickListener {
        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    // 长按接口
    interface OnItemLongClickListener {
        fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean
    }

    companion object {

        fun addTo(view: RecyclerView): ItemClickSupport {
            var support: ItemClickSupport? = view.getTag(R.id.item_click_support) as ItemClickSupport?
            if (support == null) {
                support = ItemClickSupport(view)
            }
            return support
        }

        fun removeFrom(view: RecyclerView): ItemClickSupport? {
            val support = view.getTag(R.id.item_click_support) as ItemClickSupport
            support.detach(view)
            return support
        }
    }
}
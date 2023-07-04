package xyz.rayisbest.userlogisticssystem.ui.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import xyz.rayisbest.userlogisticssystem.databinding.FragmentPickupOrderBinding
import xyz.rayisbest.userlogisticssystem.logic.util.BranchSelectContract
import xyz.rayisbest.userlogisticssystem.logic.util.showToast

class PickupOrderFragment : Fragment() {
    private lateinit var binding: FragmentPickupOrderBinding
    private lateinit var branchChoose: LinearLayout
    private lateinit var branchAddress: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickupOrderBinding.inflate(inflater, container, false)
        val root = binding.root
        branchChoose = binding.branchChoose
        branchAddress = binding.branchAddress

        init()

        return root
    }

    val launcher = registerForActivityResult(BranchSelectContract()) {
        Log.d(TAG, "收到的branch => ${it.toString()}")
        it.branchName.showToast()
        branchAddress.text = it.address
    }

    private fun init() {
        branchChoose.setOnClickListener {
            // "网点选择LinearLayout被点击".showToast()
            launcher.launch("Ada")
        }
    }

    companion object {
        const val TAG = "PickupOrderFragment"
    }
}
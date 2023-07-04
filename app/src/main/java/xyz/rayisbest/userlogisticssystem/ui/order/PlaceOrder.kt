package xyz.rayisbest.userlogisticssystem.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import xyz.rayisbest.userlogisticssystem.databinding.ActivityPalceOrderBinding
import xyz.rayisbest.userlogisticssystem.logic.util.TabLayoutMediator


class PlaceOrder : AppCompatActivity() {
    private lateinit var binding: ActivityPalceOrderBinding

    private lateinit var senderTextView: TextView
    private lateinit var recipientTextView: TextView
    private lateinit var placeOrderButton: Button
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPalceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化控件
        senderTextView = binding.senderTextView
        recipientTextView = binding.recipientTextView
        placeOrderButton = binding.placeOrderButton
        viewPager2 = binding.vp2
        tabLayout = binding.tabLayout

        init()
    }

    private fun init() {
        placeOrderButton.setOnClickListener {
            val dataIntent = Intent()
            dataIntent.putExtra("goodsDetails", "爱来自PlaceOrder")
            setResult(Activity.RESULT_OK, dataIntent)
            finish()
        }

        viewPager2.setAdapter(object: FragmentStateAdapter(this@PlaceOrder) {
            override fun getItemCount(): Int {
                return 2
            }

            override fun createFragment(position: Int): Fragment {
               return when (position) {
                    0 -> {
                        SelfSendOrderFragment()
                    }
                    else -> {
                        PickupOrderFragment()
                    }
                }
            }
        })

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "上门取件"
                }
                else -> {
                    tab.text = "网点自寄"
                }
            }
        }.attach()

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dataIntent = Intent()
                dataIntent.putExtra("goodsDetails", "爱来自PlaceOrder")
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            }

        })
    }

    companion object {
        const val TAG = "PlaceOrder"
    }
}
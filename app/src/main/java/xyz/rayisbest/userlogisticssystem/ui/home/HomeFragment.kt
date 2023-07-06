package xyz.rayisbest.userlogisticssystem.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.LogUtils
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.FragmentHomeBinding
import xyz.rayisbest.userlogisticssystem.logic.adapter.ImageAdapter
import xyz.rayisbest.userlogisticssystem.logic.bean.ImageData
import xyz.rayisbest.userlogisticssystem.logic.util.PlaceOrderContract
import xyz.rayisbest.userlogisticssystem.logic.viewmodel.HomeViewModel
import xyz.rayisbest.userlogisticssystem.ui.address.MyAddressActivity
import xyz.rayisbest.userlogisticssystem.ui.order.PlaceOrderActivity
import xyz.rayisbest.userlogisticssystem.ui.order.SearchOrderActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var banner: Banner<ImageData, ImageAdapter>
    private lateinit var placeOrderButton: Button
    private lateinit var myAddressButton: Button
    private lateinit var searchOrderButton: Button
    private val bannerImageList = ArrayList<ImageData>().apply {
        add(ImageData(R.drawable.ad1, "脚蹬牌轮椅", 1))
        add(ImageData(R.drawable.ad2, "KFC疯狂星期四", 1))
        add(ImageData(R.drawable.ad3, "广告位招租", 1))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        placeOrderButton = binding.placeOrderButton
        myAddressButton = binding.myAddressButton
        searchOrderButton = binding.searchOrderButton

        banner = binding.banner as Banner<ImageData, ImageAdapter>

        // 自定义的图片适配器，也可以使用默认的BannerImageAdapter
        val adapter = ImageAdapter(bannerImageList)

        banner.setAdapter(adapter)
            .addBannerLifecycleObserver(this) // 添加生命周期观察者
            .setIndicator(CircleIndicator(activity)) // 设置指示器
            .setOnBannerListener { data: Any, position: Int ->
                Snackbar.make(banner, (data as ImageData).title, Snackbar.LENGTH_SHORT).show()
                LogUtils.d("position：$position")
            }

        initListener()

        return root
    }



    private val placeOrderLauncher = registerForActivityResult(PlaceOrderContract()) {
        Log.d(TAG, "收到的order => ${it.toString()}")
    }

    companion object {
        const val TAG = "HomeFragment"
    }

    private fun initListener() {
        placeOrderButton.setOnClickListener {
            val intent = Intent(this.requireContext(), PlaceOrderActivity::class.java)
            startActivity(intent)
            // placeOrderLauncher.launch("Ada")
        }

        myAddressButton.setOnClickListener {
            val intent = Intent(this.requireContext(), MyAddressActivity::class.java)
            startActivity(intent)
        }

        searchOrderButton.setOnClickListener {
            val intent = Intent(this.requireContext(), SearchOrderActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
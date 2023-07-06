package xyz.rayisbest.userlogisticssystem.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.FragmentPickupOrderBinding
import xyz.rayisbest.userlogisticssystem.logic.util.StringUtils

class PickupOrderFragment : Fragment() {

    private var _binding: FragmentPickupOrderBinding? = null
    val binding get() = _binding!!

    // 日期时间选择器
    private lateinit var dateTimePicker: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickupOrderBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dateTimePicker = binding.dateTimePicker

        init()

        return root
    }

    private fun init() {
        dateTimePicker.setOnClickListener {
            CardDatePickerDialog.builder(this.requireContext())
                .setTitle("选择预约时间")
                .setWrapSelectorWheel(false)
                .setThemeColor(resources.getColor(R.color.my_colorPrimary))
                .showBackNow(false)
                .setTouchHideable(true)
                .setLabelText("年","月","日","时","分")
                .setOnChoose("选择") {
                    dateTimePicker.text = "${StringUtils.conversionTime(it, "yyyy-MM-dd HH:mm")} ${StringUtils.getWeek(it)}"
                }
                .setOnCancel("关闭") {}
                .build().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "PickupOrderFragment"
    }
}
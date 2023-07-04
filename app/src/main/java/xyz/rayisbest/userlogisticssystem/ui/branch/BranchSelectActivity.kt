package xyz.rayisbest.userlogisticssystem.ui.branch

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.ActivityBranchSelectBinding
import xyz.rayisbest.userlogisticssystem.logic.adapter.BranchAdapter
import xyz.rayisbest.userlogisticssystem.logic.bean.Branch
import xyz.rayisbest.userlogisticssystem.logic.util.ItemClickSupport
import xyz.rayisbest.userlogisticssystem.logic.util.showToast

class BranchSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBranchSelectBinding
    private val branchList = ArrayList<Branch>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var branchAdapter: BranchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBranchSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView

        initBranchList()

        val layoutManager =LinearLayoutManager(this@BranchSelectActivity)
        recyclerView.layoutManager = layoutManager
        branchAdapter = BranchAdapter(branchList)
        recyclerView.adapter = branchAdapter

        val itemClickSupport = ItemClickSupport.addTo(recyclerView)
        itemClickSupport.addOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                "你点击了：position = $position ${branchList[position].branchName}".showToast()
                val dataIntent = Intent()
                dataIntent.putExtra("address", "爱来自中国：${branchList[position].address}")
                dataIntent.putExtra("branchName", "爱来自中国：${branchList[position].branchName}")
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            }
        })

        // 添加返回动作监听事件
        onBackPressedDispatcher.addCallback(this, object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dataIntent = Intent()
                dataIntent.putExtra("address", "福建省福州市连江县人民路179号")
                dataIntent.putExtra("branchName", "爱来自中国：人民路179号")
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            }
        })
    }

    private fun initBranchList() {
        branchList.apply {
            add(Branch(address = "陕西省延安市黄龙县南滩路645号", branchName = "南滩路645号"))
            add(Branch(address = "安徽省阜阳市蒙城县健康路32号", branchName = "健康路32号"))
            add(Branch(address = "山西省晋城市右玉县永庆路370号", branchName = "永庆路370号"))
            add(Branch(address = "山东省德州市临邑县团结路243号", branchName = "团结路243号"))
            add(Branch(address = "河南省濮阳市南乐县银山路659号", branchName = "银山路659号"))
            add(Branch(address = "安徽省阜阳市太和县车站路835号", branchName = "车站路835号"))
            add(Branch(address = "福建省福州市连江县人民路179号", branchName = "人民路179号"))
            add(Branch(address = "福建省泉州市金门县起凤路120号", branchName = "起凤路120号"))
            add(Branch(address = "陕西省宝鸡市扶风县王府路705号", branchName = "王府路705号"))
            add(Branch(address = "湖南省永州市江永县百合路234号", branchName = "百合路234号"))
            add(Branch(address = "山西省晋城市应县长盛路735号", branchName = "长盛路735号"))
        }

    }
}
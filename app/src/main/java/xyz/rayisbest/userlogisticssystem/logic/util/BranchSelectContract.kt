package xyz.rayisbest.userlogisticssystem.logic.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import xyz.rayisbest.userlogisticssystem.logic.bean.Branch
import xyz.rayisbest.userlogisticssystem.ui.branch.BranchSelectActivity

class BranchSelectContract: ActivityResultContract<String, Branch>() {

    companion object {
        const val TAG  = "BranchSelectContract"
    }

    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, BranchSelectActivity::class.java)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Branch {
        val branch = Branch()
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "返回值ok, intent => ${intent.toString()}")
            if (intent != null) {
                branch.branchName = intent.getStringExtra("branchName")!!
                branch.address = intent.getStringExtra("address")!!
            }
        } else {
            Log.d(TAG, "返回值不ok, intent => ${intent.toString()}")
        }
        return branch
    }

}
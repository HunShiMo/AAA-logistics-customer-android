package xyz.rayisbest.userlogisticssystem.logic.util

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText

class NonEditableEditText(context: Context, attrs: AttributeSet): AppCompatEditText(context, attrs) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }
}
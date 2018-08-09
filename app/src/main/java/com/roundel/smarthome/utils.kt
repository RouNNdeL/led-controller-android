package com.roundel.smarthome

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.IdRes
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

fun <T : View> Activity.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { findViewById<T>(idRes) }
}

fun <T : View> View.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafeLazy { findViewById<T>(idRes) }
}

private fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

fun hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun <E> List<E>.random(default: E): E = if (size > 0) get(Random().nextInt(size)) else default

fun Canvas.drawTextCentred(text: String, cx: Float, cy: Float, paint: Paint) {
    val textBounds = Rect()
    paint.getTextBounds(text, 0, text.length, textBounds)
    drawText(text, cx - textBounds.exactCenterX(), cy - textBounds.exactCenterY(), paint)
}
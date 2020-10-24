package com.example.gitaplication.firstScreen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.core.statefulview.SceneView
import com.example.gitaplication.R
import com.example.gitaplication.util.hideKeyboard
import com.multiplatform.util.Diff
import kotlinx.android.synthetic.main.view_main.view.*


class MainView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SceneView<MainState>(context, attrs, defStyleAttr) {


    override fun createView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.view_main, parent, false)

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        Scan.setOnClickListener {
            dispatchAction(MainAction.Scan)
        }

        iAmSick.setOnClickListener {
            dispatchAction(MainAction.SelfReport)
        }
    }

    override fun render(state: MainState, diff: Diff<MainState>) {

        if (diff.by { it.status }) {
            status.text = state.status
        }

        if (diff.by { it.isLoading }) {
            progressIndicator.isVisible = state.isLoading
        }
    }
}

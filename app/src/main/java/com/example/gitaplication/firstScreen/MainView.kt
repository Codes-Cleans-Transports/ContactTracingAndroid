package com.example.gitaplication.firstScreen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.core.statefulview.SceneView
import com.example.gitaplication.R
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

        iAmSick.setOnClickListener {
            dispatchAction(MainAction.ShowDialog(context))
        }
    }

    override fun render(state: MainState, diff: Diff<MainState>) {

        if (diff.by { it.status }) {
            status.text = state.status.status
        }

        if (diff.by { it.status }) {
            val risk = (100.0-state.status.safety.toDouble() * 100) / 100.0
            safety.text = "Your personal risk level is: $risk"
        }

        if (diff.by { it.status }) {
            if (state.status.status == "Positive") {
                iAmSick.visibility = View.GONE
                message.visibility = View.VISIBLE
            }
        }

//        if (diff.by { it.isLoading }) {
//            progressIndicator.isVisible = state.isLoading
//        }
    }
}

package com.example.gitaplication.login

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import com.bluelinelabs.conductor.RouterTransaction
import com.core.statefulview.SceneView
import com.example.gitaplication.R
import com.example.gitaplication.userDetails.UserDetailsController
import com.multiplatform.util.Diff
import kotlinx.android.synthetic.main.view_login.view.*


class LoginView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SceneView<LoginState>(context, attrs, defStyleAttr) {


    override fun createView(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(R.layout.view_login, parent, false)

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        loginButton.setOnClickListener {
            dispatchAction(LoginAction.Login(usernameEditText.text.toString()))
        }

        usernameEditText.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                this.hideKeyboard()
            }
        }

    }

    override fun render(state: LoginState, diff: Diff<LoginState>) {

        if (diff.by { it.isLoggingIn }) {

            progressIndicator.visibility = if (state.isLoggingIn) View.VISIBLE else View.GONE

            loginButton.isEnabled = !state.isLoggingIn
        }
    }
}

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

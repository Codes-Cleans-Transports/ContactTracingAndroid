package com.example.gitaplication.firstScreen

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import com.example.gitaplication.firstScreen.bluetooth.Device
import com.example.gitaplication.firstScreen.useCases.*
import com.multiplatform.play.Action
import com.multiplatform.play.Actor
import com.multiplatform.play.React
import com.multiplatform.play.StateTransformer
import com.multiplatform.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/* State */
data class MainState(
    val status: Status = Status(status = "Negative", safety = "1.0"),
    val mac: String = "",
    val contacts: ArrayList<String> = ArrayList(),
    val isLoading: Boolean = false
)
/* Actions */

sealed class MainAction : Action {

    object Scan : MainAction()

    class GoToUrl(val context: Context) : MainAction()

    class ShowDialog(val context: Context) : MainAction() {
        sealed class Reaction : com.multiplatform.play.Reaction {

            object SelfReport : Reaction()
        }
    }

    object SendData : MainAction() {
        sealed class Reaction : com.multiplatform.play.Reaction {

            object Success : Reaction()
        }
    }

    object LoadStatus : MainAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val status: Status) : Reaction()

            class Error(val error: Throwable) : Reaction()
        }
    }

    object TurnBluetoothOn : MainAction()

    class AddDevice(val device: Device) : MainAction()

    object GetMyOwnMacAddress : MainAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val mac: String) : Reaction()
        }
    }
}

/* State transformer */

object MainStateTransformer : StateTransformer<MainState> {

    override fun invoke(state: MainState, action: Action): MainState = when (action) {

        is MainAction.AddDevice -> state.copy(
            contacts = state.contacts.apply { add(action.device.deviceMAC!!) }
        )

        is MainAction.SendData.Reaction.Success -> state.copy(
            contacts = ArrayList()
        )

        is MainAction.LoadStatus.Reaction.Success -> state.copy(
            status = action.status
        )

        is MainAction.ShowDialog.Reaction.SelfReport -> state.copy(
            status = Status(status = "positive", safety = "0.0")
        )

        is MainAction.GetMyOwnMacAddress.Reaction.Success -> state.copy(
            mac = action.mac
        )

        else -> state
    }
}

/* Actor */

class MainActor(
    private val scope: CoroutineScope,

    private val sendDataUseCase: SendDataUseCase,

    private val loadStatusUseCase: LoadStatusUseCase,

    private val selfReportUseCase: SelfReportUseCase,

    private val turnBluetoothOn: TurnBluetoothOnUseCase,

    private val scanUseCase: ScanUseCase,

    private val getMyOwnMacAddressUseCase: GetMyOwnMacAddressUseCase
) : Actor<MainState> {

    override fun invoke(action: Action, state: MainState, react: React) {

        when (action) {

            is MainAction.LoadStatus -> {
                scope.launch(Dispatchers.Main) {
                    when (val result = loadStatusUseCase(state.mac)) {
                        is Result.Success -> react(MainAction.LoadStatus.Reaction.Success(result.data))

                        is Result.Error -> react(MainAction.LoadStatus.Reaction.Error(result.error))
                    }
                }
            }

            is MainAction.ShowDialog.Reaction.SelfReport -> {
                scope.launch(Dispatchers.Main) {
                    selfReportUseCase(state.mac)
                }
            }

            is MainAction.GoToUrl->{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://10.108.6.171:8000/${state.mac}"))
                startActivity(action.context,browserIntent,null)
            }

            is MainAction.SendData -> {
                scope.launch(Dispatchers.Main) {
                    sendDataUseCase(state.mac, state.contacts.toTypedArray())
                    react(MainAction.SendData.Reaction.Success)
                }
            }

            is MainAction.TurnBluetoothOn -> {
                scope.launch(Dispatchers.Main) {
                    turnBluetoothOn()
                }
            }

            is MainAction.Scan -> {
                scope.launch(Dispatchers.Main) {
                    scanUseCase()
                }
            }

            is MainAction.GetMyOwnMacAddress -> {
                val address = getMyOwnMacAddressUseCase.invoke()
                if (address != "") react(MainAction.GetMyOwnMacAddress.Reaction.Success(address))
            }

            is MainAction.ShowDialog -> {
                val handler = Handler()

                val dialog = AlertDialog.Builder(action.context)
                    .setTitle("I have tested positive")
                    .setCancelable(false)
                    .setMessage(action.context.getString(com.example.gitaplication.R.string.dialog_message))
                    .setPositiveButton("5", null)
                    .setNegativeButton("I am not quite sure", null)
                    .setIcon(R.drawable.ic_dialog_alert)
                    .create()

                dialog.show()

                val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                //button.visibility = View.INVISIBLE
                handler.postDelayed({ button.text = "4" }, 1000)
                handler.postDelayed({ button.text = "3" }, 2000)
                handler.postDelayed({ button.text = "2" }, 3000)
                handler.postDelayed({ button.text = "1" }, 4000)
                handler.postDelayed({
                    button.text = "I am sure"
                    button.setOnClickListener {
                        react(MainAction.ShowDialog.Reaction.SelfReport)
                        dialog.dismiss()
                    }
                }, 5000)
            }
        }
    }
}
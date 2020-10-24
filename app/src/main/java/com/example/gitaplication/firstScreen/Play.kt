package com.example.gitaplication.firstScreen

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
    val status: String = "Negative",
    val mac: String = "",
    val contacts: ArrayList<String> = ArrayList(),
    val isLoading: Boolean=false
)
/* Actions */

sealed class MainAction : Action {

    object Scan : MainAction()

    object SendData : MainAction(){
        sealed class Reaction : com.multiplatform.play.Reaction {

            object Success : Reaction()
        }
    }

    object LoadStatus : MainAction() {

        sealed class Reaction : com.multiplatform.play.Reaction {

            class Success(val status: String) : Reaction()

            class Error(val error: Throwable) : Reaction()
        }
    }

    object SelfReport : MainAction()

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

            is MainAction.SelfReport -> {
                scope.launch(Dispatchers.Main) {
                    selfReportUseCase(state.mac)
                }
            }

            is MainAction.SendData -> {
                scope.launch(Dispatchers.Main) {
                    sendDataUseCase(state.mac, state.contacts.toTypedArray())
                }
                react(MainAction.SendData.Reaction.Success)
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
        }
    }
}
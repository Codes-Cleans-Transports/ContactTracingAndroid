package com.example.gitaplication.firstScreen

import android.bluetooth.BluetoothAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.example.gitaplication.R
import com.example.gitaplication.firstScreen.bluetooth.BluetoothBroadcastReceiver
import com.example.gitaplication.firstScreen.bluetooth.BluetoothManager
import com.example.gitaplication.firstScreen.di.firstScreenModule
import com.example.gitaplication.firstScreen.useCases.*
import com.multiplatform.play.Action
import com.multiplatform.play.Scene
import kotlinx.coroutines.GlobalScope
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance


class MainController : Controller(), DIAware {

    override val di: DI = DI.lazy {
        extend((applicationContext as DIAware).di)

        import(firstScreenModule)
    }

    private lateinit var mainView: MainView

    private lateinit var scene: Scene<MainState>

    private val mReceiver = BluetoothBroadcastReceiver()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {

        val view = inflater.inflate(R.layout.controller_login, container, false)

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val bluetoothManager = BluetoothManager()

        val sendDataUseCase: SendDataUseCase by instance<SendDataUseCase>()

        val loadStatusUseCase: LoadStatusUseCase by instance<LoadStatusUseCase>()

        val selfReportUseCase: SelfReportUseCase by instance<SelfReportUseCase>()

        val turnBluetoothOnUseCase = TurnBluetoothOnUseCase(bluetoothManager, activity, bluetoothAdapter)

        val scanUseCase = ScanUseCase(bluetoothManager, activity, bluetoothAdapter, mReceiver)

        mainView = view.findViewById(R.id.loginView)

        scene = Scene(
            initialState = MainState(),

            stateTransformer = MainStateTransformer,

            actor = MainActor(
                scope = GlobalScope,
                loadStatusUseCase = loadStatusUseCase,
                sendDataUseCase = sendDataUseCase,
                selfReportUseCase = selfReportUseCase,
                turnBluetoothOn = turnBluetoothOnUseCase,
                scanUseCase = scanUseCase
            ),

            spectators = listOf(
                ::navigationSpectator,
                ::errorHandlingSpectator
            )
        )

        scene.dispatch(MainAction.LoadStatus)

        mainView.init(scene)

        scene.dispatch(MainAction.TurnBluetoothOn)

        return view
    }

    private fun navigationSpectator(action: Action, state: MainState): Boolean {

        return false
    }

    private fun errorHandlingSpectator(action: Action, state: MainState): Boolean {

        return false
    }
}

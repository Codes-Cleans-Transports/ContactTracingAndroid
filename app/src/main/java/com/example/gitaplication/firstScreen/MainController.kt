package com.example.gitaplication.firstScreen

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import com.example.gitaplication.R
import com.example.gitaplication.firstScreen.bluetooth.BluetoothBroadcastReceiver
import com.example.gitaplication.firstScreen.bluetooth.BluetoothManager
import com.example.gitaplication.firstScreen.bluetooth.DeviceEventHandler
import com.example.gitaplication.firstScreen.di.firstScreenModule
import com.example.gitaplication.firstScreen.useCases.*
import com.multiplatform.play.Action
import com.multiplatform.play.Scene
import kotlinx.coroutines.GlobalScope
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import java.util.*


class MainController : Controller(), DIAware {

    override val di: DI = DI.lazy {
        extend((applicationContext as DIAware).di)

        import(firstScreenModule)
    }

    private val mReceiver = BluetoothBroadcastReceiver()

    private lateinit var mainView: MainView

    private lateinit var scene: Scene<MainState>

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeviceFindEvent(deviceEventHandler: DeviceEventHandler) {
        if (deviceEventHandler.device.deviceMAC != null) scene.dispatch(MainAction.AddDevice(deviceEventHandler.device))
        //Toast.makeText(activity, "We found ${deviceEventHandler.device.name}!!!!", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        EventBus.getDefault().register(this)
    }

    override fun onActivityStopped(activity: Activity) {
        EventBus.getDefault().unregister(this)
        super.onActivityStopped(activity)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {

        val view = inflater.inflate(R.layout.controller_login, container, false)

        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val bluetoothManager = BluetoothManager()

        val sendDataUseCase: SendDataUseCase by instance<SendDataUseCase>()

        val loadStatusUseCase: LoadStatusUseCase by instance<LoadStatusUseCase>()

        val selfReportUseCase: SelfReportUseCase by instance<SelfReportUseCase>()

        val turnBluetoothOnUseCase = TurnBluetoothOnUseCase(bluetoothManager, activity, bluetoothAdapter)

        val scanUseCase = ScanUseCase(bluetoothManager, activity, bluetoothAdapter, mReceiver)

        val getMyOwnMacUseCase = GetMyOwnMacAddressUseCase()

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
                scanUseCase = scanUseCase,
                getMyOwnMacAddressUseCase = getMyOwnMacUseCase
            ),

            spectators = listOf(
                ::navigationSpectator,
                ::errorHandlingSpectator
            )
        )

        mainView.init(scene)

        scene.dispatch(MainAction.GetMyOwnMacAddress)

        scene.dispatch(MainAction.LoadStatus)

        scene.dispatch(MainAction.TurnBluetoothOn)

        scene.dispatch(MainAction.Scan)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                scene.dispatch(MainAction.Scan)
                scene.dispatch(MainAction.SendData)
            }
        }, 0, 15000) //put here time 1000 milliseconds=1 second

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                scene.dispatch(MainAction.LoadStatus)
            }
        }, 0, 5000)

        return view
    }

    private fun navigationSpectator(action: Action, state: MainState): Boolean {

        return false
    }

    private fun errorHandlingSpectator(action: Action, state: MainState): Boolean {

        when (action) {
            is MainAction.LoadStatus.Reaction.Error -> {
                Toast.makeText(applicationContext, "Could not load your status", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }
}

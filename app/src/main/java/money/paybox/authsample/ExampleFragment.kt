package money.paybox.authsample

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import money.paybox.authentication_sdk.EventListener
import money.paybox.authentication_sdk.PayBoxAuth
import money.paybox.authentication_sdk.api.Language
import money.paybox.authentication_sdk.model.AuthResult
import money.paybox.authentication_sdk.ui.AuthView

/**
 * A simple [Fragment] subclass.
 * Use the [ExampleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExampleFragment : Fragment(), EventListener {
    private lateinit var authSDK: PayBoxAuth

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_example, container, false)

        val authView = view.findViewById<AuthView>(R.id.authView)
        progressBar = view.findViewById(R.id.progressBar)

        authSDK = PayBoxAuth.Builder(this)
            .setAuthView(authView)
            .setSlug("Ps80iM2lcH")
            .setSecretKey("Sx8eNseBYabaKMymsWQULLWnR46iC9pT")
            .setToken("6QwxaPQxEU6FkqHbmqn6dPYjpRnyED8m")
            .setLanguage(Language.ru)
            .build()

        val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                if(it.key == Manifest.permission.CAMERA) {
                    if (it.value == true) {
                        startAuthentication()
                    } else {
                        Toast.makeText(context, "Camera permission not granted", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        requestMultiplePermissions.launch(arrayOf(Manifest.permission.CAMERA))

        authSDK.checkLast()
        return view
    }

    private fun startAuthentication() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            authSDK.auth("77771112233")
        } else {
            //Devices with unsupported android APIs
            Toast.makeText(activity, "Your device is unsupported", Toast.LENGTH_LONG).show()
        }
    }

    override fun onLoadStarted() {
        //TODO: Show progress bar
        progressBar.visibility = View.VISIBLE
    }

    override fun onLoadFinished() {
        //TODO: Hide progress bar
        progressBar.visibility = View.GONE
    }
    override fun onError(message: String) {
        //TODO: Resolve errors occurred during SDK's work
        Log.e("Authentication SDK", message)
    }

    override fun onAuth(result: AuthResult) {
        //TODO: Your implementation using auth result
        when(result.status) {
            AuthResult.Status.NEW -> {
                //Требуется подтверждение идентификации с помощью OTP (Промежуточный)
                Toast.makeText(context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.APPROVED -> {
                //Необходимо отправить лучшее изображение идентифицируемого (Промежуточный)
                Toast.makeText(context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.PROCESS -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.VERIFIED -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.IDENTIFIED -> {
                //Успешный результат идентификации (Финальный)
                Toast.makeText(context, "Success! ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.ERROR -> {
                //Неудачный результат идентификации (Финальный)
                Toast.makeText(context,
                    "Error: " + result.error?.code + " - " + result.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCheck(result: AuthResult) {
        //TODO: Your implementation using auth result
        when (result.status) {
            AuthResult.Status.NEW -> {
                //Требуется подтверждение идентификации с помощью OTP (Промежуточный)
                Toast.makeText(
                    context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG
                ).show()
            }
            AuthResult.Status.APPROVED -> {
                //Необходимо отправить лучшее изображение идентифицируемого (Промежуточный)
                Toast.makeText(
                    context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG
                ).show()
            }
            AuthResult.Status.PROCESS -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(
                    context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG
                ).show()
            }
            AuthResult.Status.VERIFIED -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(
                    context,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG
                ).show()
            }
            AuthResult.Status.IDENTIFIED -> {
                //Успешный результат идентификации (Финальный)
                Toast.makeText(context, "Success! ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.ERROR -> {
                //Неудачный результат идентификации (Финальный)
                Toast.makeText(
                    context,
                    "Error: " + result.error?.code + " - " + result.error?.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BlankFragment.
         */
        @JvmStatic
        fun newInstance() = ExampleFragment()
    }
}
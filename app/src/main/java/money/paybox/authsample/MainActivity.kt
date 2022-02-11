package money.paybox.authsample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import  money.paybox.authentication_sdk.model.AuthResult
import money.paybox.authentication_sdk.EventListener
import money.paybox.authentication_sdk.PayBoxAuth
import money.paybox.authentication_sdk.api.Language
import money.paybox.authentication_sdk.ui.AuthView

class MainActivity : AppCompatActivity(), EventListener {

    private lateinit var authSDK: PayBoxAuth

    private lateinit var progressBar: ProgressBar
    private val CAMERA_REQUEST_CODE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authView = findViewById<AuthView>(R.id.authView)
        progressBar = findViewById(R.id.progressBar)

        authSDK = PayBoxAuth.Builder(this)
            .setAuthView(authView)
            .setSlug("Ps80iM2lcH")
            .setSecretKey("Sx8eNseBYabaKMymsWQULLWnR46iC9pT")
            .setToken("6QwxaPQxEU6FkqHbmqn6dPYjpRnyED8m")
            .setLanguage(Language.ru)
            .build()

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat
                .requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        } else {
            startAuthentication()
        }

        //Checks last authentication's status
        authSDK.checkLast()

        //Checks authentication status by ID
        //authSDK.checkStatusById(123)

        //Checks authentication status by phone number
        //authSDK.checkStatusByPhone("77771112233")

        //startActivity(Intent(this, FragmentExampleActivity::class.java))
        //finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startAuthentication()
                }
                return
            }
        }
    }

    private fun startAuthentication() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            authSDK.auth("77771112233")
        } else {
            //Devices with unsupported android APIs
            Toast.makeText(this, "Your device is unsupported", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.APPROVED -> {
                //Необходимо отправить лучшее изображение идентифицируемого (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.PROCESS -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.VERIFIED -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.IDENTIFIED -> {
                //Успешный результат идентификации (Финальный)
                Toast.makeText(this, "Success! ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.ERROR -> {
                //Неудачный результат идентификации (Финальный)
                Toast.makeText(this,
                    "Error: " + result.error?.code + " - " + result.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCheck(result: AuthResult) {
        //TODO: Your implementation using auth result
        when(result.status) {
            AuthResult.Status.NEW -> {
                //Требуется подтверждение идентификации с помощью OTP (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.APPROVED -> {
                //Необходимо отправить лучшее изображение идентифицируемого (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.PROCESS -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.VERIFIED -> {
                //Запрашивать статус до финального статуса (Промежуточный)
                Toast.makeText(this,
                    "Status: " + result.status.name + " ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.IDENTIFIED -> {
                //Успешный результат идентификации (Финальный)
                Toast.makeText(this, "Success! ID: " + result.id, Toast.LENGTH_LONG).show()
            }
            AuthResult.Status.ERROR -> {
                //Неудачный результат идентификации (Финальный)
                Toast.makeText(this,
                    "Error: " + result.error?.code + " - " + result.error?.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
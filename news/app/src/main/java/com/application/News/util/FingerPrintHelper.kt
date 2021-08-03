package com.application.News.util


import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

class FingerPrintHelper {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricManager: BiometricManager
    private var type = 0


    fun scanData(fragmentActivity: FragmentActivity, action: FingerPrintAction?) {

        biometricManager = BiometricManager.from(fragmentActivity)

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(false)
            .setSubtitle("Verify your security to continue")
            .build()

        executor = ContextCompat.getMainExecutor(fragmentActivity)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Log.e("MY_APP_TAG", "The user hasn't associated " +
                        "any biometric credentials with their account.")
        }




        biometricPrompt = BiometricPrompt(fragmentActivity, executor, object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)

                    if (errorCode ==  BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED){
                        fragmentActivity.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))
                        action!!.onFailed(errString.toString())
                    }
                    if (errorCode ==  BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE){
                        action!!.onSuccess(errString.toString())
                    }
                    if (errorCode == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE){
                        action!!.onSuccess(errString.toString())
                    }

                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    action!!.onSuccess("Authentication succeeded!")


                }

                override fun onAuthenticationFailed() {
                    action!!.onFailed("Authentication failed")
                }


            })

        biometricPrompt.authenticate(promptInfo)


    }


    interface FingerPrintAction {
        fun onSuccess(result: String?)
        fun onFailed(result: String?)
    }


}
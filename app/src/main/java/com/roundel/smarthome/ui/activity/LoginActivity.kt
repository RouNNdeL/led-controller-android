package com.roundel.smarthome.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.constraint.Group
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.roundel.smarthome.R
import com.roundel.smarthome.api.OAuthException
import com.roundel.smarthome.api.OAuthManager
import com.roundel.smarthome.bind
import com.roundel.smarthome.hideKeyboard
import java.io.IOException
import java.net.ConnectException

class LoginActivity : AppCompatActivity() {

    private val usernameTextInput: TextInputEditText by bind(R.id.login_textinput_username)
    private val passwordTextInput: TextInputEditText by bind(R.id.login_textinput_password)
    private val passwordLoginButton: Button by bind(R.id.login_login_button)

    private val progressGroup: Group by bind(R.id.login_progress_group)
    private val formGroup: Group by bind(R.id.login_form_group)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        passwordLoginButton.setOnClickListener { initiatePasswordLogin() }
        passwordTextInput.setOnEditorActionListener { _, _, _ ->
            initiatePasswordLogin()
            true
        }
    }

    override fun onResume() {
        super.onResume()
        showForm(true)
    }

    private fun initiatePasswordLogin() {
        val username = usernameTextInput.text.toString()
        val password = passwordTextInput.text.toString()

        hideKeyboard(this)
        showForm(false)
        Thread(Runnable {
            try {
                val manager = OAuthManager(username, password)
                //TODO: We got the tokens, redirect to main activity
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } catch (e: OAuthException) {
                runOnUiThread {
                    showForm(true)
                    Toast.makeText(this@LoginActivity, "Invalid username or password!", Toast.LENGTH_SHORT).show()
                }
            } catch (e1: IOException) {
                runOnUiThread {
                    showForm(true)
                    Toast.makeText(this@LoginActivity, "Connection error", Toast.LENGTH_SHORT).show()
                }
            }
        }).start()
    }

    private fun showForm(show: Boolean) {
        formGroup.visibility = if (show) View.VISIBLE else View.GONE
        progressGroup.visibility = if (show) View.GONE else View.VISIBLE
    }
}

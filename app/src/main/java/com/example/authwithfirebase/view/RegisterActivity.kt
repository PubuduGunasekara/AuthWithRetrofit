package com.example.authwithfirebase.view

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import com.example.authwithfirebase.R
import com.example.authwithfirebase.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener {

    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(registerBinding.root)
        registerBinding.regName.onFocusChangeListener = this
        registerBinding.regEmail.onFocusChangeListener = this
        registerBinding.regPassword.onFocusChangeListener = this
        registerBinding.regConPassword.onFocusChangeListener = this

    }

    //Validation

    private fun validateName(): Boolean {
        var errorMessage: String? = null
        val value: String = registerBinding.regName.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Name is required"
        }

        if (errorMessage != null) {
            registerBinding.regNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val value: String = registerBinding.regEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
        }

        if (errorMessage != null) {
            registerBinding.regEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePassword(): Boolean {
        var errorMessage: String? = null
        val value: String = registerBinding.regPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be six characters long"
        }
        if (errorMessage != null) {
            registerBinding.regPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateConPassword(): Boolean {
        var errorMessage: String? = null
        val value: String = registerBinding.regConPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"
        } else if (value.length < 6) {
            errorMessage = "Confirm Password must be six characters long"
        }
        if (errorMessage != null) {
            registerBinding.regConPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConPassword(): Boolean {
        var errorMessage: String? = null
        val regPassword: String = registerBinding.regPassword.text.toString()
        val regConPassword: String = registerBinding.regConPassword.text.toString()

        if (regPassword != regConPassword) {
            errorMessage = "Confirm Password doesn't match with the password"
        }

        if (errorMessage != null) {
            registerBinding.regConPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }


    override fun onClick(view: View?) {
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.reg_name -> {
                    if (hasFocus) {
                        if (registerBinding.regNameTil.isErrorEnabled) {
                            registerBinding.regNameTil.isErrorEnabled = false
                        }
                    } else {
                        validateName()
                    }
                }

                R.id.reg_email -> {
                    if (hasFocus) {
                        if (registerBinding.regEmailTil.isErrorEnabled) {
                            registerBinding.regEmailTil.isErrorEnabled = false
                        }
                    } else {
                       if(validateEmail()) {
                           //do validation for it's uniqueness
                       }
                    }
                }

                R.id.reg_password -> {
                    if (hasFocus) {
                        if (registerBinding.regPasswordTil.isErrorEnabled) {
                            registerBinding.regPasswordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && registerBinding.regConPassword.text!!.isNotEmpty() && validateConPassword() && validatePasswordAndConPassword()) {
                            if (registerBinding.regConPasswordTil.isErrorEnabled) {
                                registerBinding.regConPasswordTil.isErrorEnabled = false
                            }
                            registerBinding.regConPasswordTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }

                R.id.reg_con_password -> {
                    if (hasFocus) {
                        if (registerBinding.regConPasswordTil.isErrorEnabled) {
                            registerBinding.regConPasswordTil.isErrorEnabled = false
                        }
                    } else {
                        if (validatePassword() && validateConPassword() && validatePasswordAndConPassword()) {
                            if (registerBinding.regPasswordTil.isErrorEnabled) {
                                registerBinding.regPasswordTil.isErrorEnabled = false
                            }
                            registerBinding.regConPasswordTil.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false
    }
}
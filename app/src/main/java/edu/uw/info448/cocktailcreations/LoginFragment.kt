package edu.uw.info448.cocktailcreations

// Sarah West wrote this file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val signupLink = view.findViewById<TextView>(R.id.signup_caption_link)
        signupLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.login_nav_fragment, SignupFragment())
                .commit()
        }
        val loginButton = view.findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val email = view.findViewById<TextView>(R.id.email_box).text.toString()
            val password = view.findViewById<TextView>(R.id.password_box).text.toString()
            (activity as LoginActivity).login(email, password)
        }
        return view
    }

}
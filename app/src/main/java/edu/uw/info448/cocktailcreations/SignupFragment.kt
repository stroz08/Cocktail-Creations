package edu.uw.info448.cocktailcreations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class SignupFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val loginLink = view.findViewById<TextView>(R.id.login_caption_link)
        loginLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.login_nav_fragment, LoginFragment())
                .commit()
        }
        val signUpButton = view.findViewById<Button>(R.id.signup_button)
        signUpButton.setOnClickListener {
            val name = view.findViewById<TextView>(R.id.name_box).text.toString()
            val email = view.findViewById<TextView>(R.id.email_box).text.toString()
            val password = view.findViewById<TextView>(R.id.password_box).text.toString()
            val confirmPassword = view.findViewById<TextView>(R.id.confirm_password_box)
                .text.toString()
            if (password != confirmPassword) {
                Toast.makeText(activity, "Sign up failed: Passwords do not match",
                    Toast.LENGTH_SHORT).show()
            } else {
                (activity as LoginActivity).createAccount(name, email, password)
            }
        }
        return view
    }
}
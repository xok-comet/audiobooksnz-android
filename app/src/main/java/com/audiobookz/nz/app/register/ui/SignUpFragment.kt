package com.audiobookz.nz.app.register.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.audiobookz.nz.app.MainActivity
import com.audiobookz.nz.app.R
import com.audiobookz.nz.app.SplashScreenActivity
import com.audiobookz.nz.app.di.Injectable
import com.audiobookz.nz.app.di.injectViewModel
import com.audiobookz.nz.app.data.Result
import com.audiobookz.nz.app.login.ui.LoginEmailActivity
import javax.inject.Inject

class SignUpFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = injectViewModel(viewModelFactory)

        val edittxtFistname = view.findViewById<EditText>(R.id.editFirstName)
        val edittxtLastname = view.findViewById<EditText>(R.id.editLastName)
        val edittxtEmail = view.findViewById<EditText>(R.id.editEmail)
        val edittxtPassword = view.findViewById<EditText>(R.id.editPasswordSignUp)
        val edittxtPasswordConfirm = view.findViewById<EditText>(R.id.editPasswordConfirm)
        val btnSignup = view.findViewById<Button>(R.id.btnSignUp)
        val chkbox = view.findViewById<CheckBox>(R.id.checkboxTerm)

        btnSignup.setOnClickListener { view ->

            if (!chkbox.isChecked) {
                Toast.makeText(activity, "please tick checkbok", Toast.LENGTH_SHORT).show()
            } else if (edittxtFistname.text.isEmpty()) {
                Toast.makeText(activity, "fisrtname is blank", Toast.LENGTH_SHORT).show()
            } else if (edittxtLastname.text.isEmpty()) {
                Toast.makeText(activity, "lastname is blank", Toast.LENGTH_SHORT).show()
            } else if (edittxtEmail.text.isEmpty()) {
                Toast.makeText(activity, "email is blank", Toast.LENGTH_SHORT).show()
            } else if (edittxtPassword.text.isEmpty()) {
                Toast.makeText(activity, "password is blank", Toast.LENGTH_SHORT).show()
            } else if (edittxtPasswordConfirm.text.isEmpty()) {
                Toast.makeText(activity, "confirm password is blank", Toast.LENGTH_SHORT).show()
            } else {

                viewModel.email = edittxtEmail.text.toString()
                viewModel.firstName = edittxtFistname.text.toString()
                viewModel.lastName = edittxtLastname.text.toString()
                viewModel.terms = "1"
                viewModel.password = edittxtPassword.text.toString()
                viewModel.cPassword = edittxtPasswordConfirm.text.toString()

                subscribeUi()

//                MaterialAlertDialogBuilder(getActivity())
//                    .setTitle(resources.getString(R.string.AlertTitle))
//                    .setMessage(resources.getString(R.string.AlertDescription))
//                    .setPositiveButton(resources.getString(R.string.AlertOk)) { dialog, which ->
//                        // Respond to positive button press
//                    }
//                    .show()
            }

        }

    }

    private fun subscribeUi() {
        viewModel.emailSignUp.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    if (result.data == null) {
                        Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "good", Toast.LENGTH_SHORT).show();
                        val intent = Intent(
                            activity,
                            SplashScreenActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }

                }
                Result.Status.LOADING -> Log.d("TAG", "loading")
                Result.Status.ERROR -> {
                    Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show();
                }
            }
        })
    }

}

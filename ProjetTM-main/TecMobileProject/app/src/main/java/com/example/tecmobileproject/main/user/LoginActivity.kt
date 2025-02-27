    package com.example.tecmobileproject.main.user


    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.ViewModelProvider
    import com.example.tecmobileproject.main.movie.MovieListActivity
    import com.example.tecmobileproject.databinding.ActivityLoginBinding
    import com.example.tecmobileproject.dtos.DtoLogin
    import com.google.android.gms.auth.api.signin.GoogleSignIn
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions
    import com.google.android.gms.common.api.ApiException
    import com.google.android.gms.tasks.Task


    class LoginActivity : AppCompatActivity() {
        lateinit var binding: ActivityLoginBinding
        private lateinit var viewModel: UserViewModel

        private val GOOGLE_SIGN_IN_REQUEST_CODE = 9001

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)


            viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            setUpListeners()
        }
        private fun setUpListeners() {
            viewModel.mutableLiveDataLoginUser.observe(this){   user ->
                if (user == null) {
                    Toast.makeText(this, "Incorrect email address or password", Toast.LENGTH_SHORT).show()
                } else {
                    Intent(this, MovieListActivity::class.java).apply {
                        startActivity(this)
                    }
                }

            }


            binding.btnLoginActivityLogin.setOnClickListener {
                if (binding.etLoginActivityEmail.text.isNullOrBlank() || binding.etLoginActivityPassword.text.isNullOrBlank()) {
                    Toast.makeText(this, "Please fill in the fields", Toast.LENGTH_SHORT).show()
                }else{
                    val loginData = DtoLogin(
                        binding.etLoginActivityEmail.text.toString(),
                        binding.etLoginActivityPassword.text.toString()
                    )
                    viewModel.launchGetUser(loginData)

                    val preferences = getSharedPreferences("app", MODE_PRIVATE)
                    val token = preferences.getString("jwtToken", "???")

                    viewModel.mutableLiveDataLoginUser.observeForever { userData ->
                        userData?.let {
                            val editor = preferences.edit()
                            editor.putString("jwtToken", userData)
                            editor.apply()

                        } ?: run {
                        }
                    }
                    viewModel.getDataToken(token.toString())

                }

            }

            binding.btnLoginWithGoogle.setOnClickListener {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(this, gso)

                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
            }


        }
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }

        private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
            try {
                val account = completedTask.getResult(ApiException::class.java)
                val email = account?.email

                if (email != null) {

                        val loginData = DtoLogin(
                            email,
                            ""
                        )
                        viewModel.launchGetUserGoogle(loginData)

                        val preferences = getSharedPreferences("app", MODE_PRIVATE)
                        val token = preferences.getString("jwtToken", "???")

                        viewModel.mutableLiveDataLoginUser.observeForever { userData ->
                            userData?.let {
                                val editor = preferences.edit()
                                editor.putString("jwtToken", userData)
                                editor.apply()

                            } ?: run {
                            }
                        }
                        viewModel.getDataToken(token.toString())



                    Toast.makeText(this, "Logged in with $email", Toast.LENGTH_SHORT).show()
                }

                val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
                googleSignInClient.signOut()

            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
            }
        }

    }
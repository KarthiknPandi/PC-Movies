package com.example.pcmovies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pcmovies.data.repository.NewUserRepository
import com.example.pcmovies.databinding.ActivityMainBinding
import com.example.pcmovies.worker.ServiceLocator
import com.example.pcmovies.worker.UserSyncWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var newUserRepository: NewUserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ServiceLocator.init(newUserRepository)
//        scheduleUserSyncWork(applicationContext)
    }

//    fun scheduleUserSyncWork(context: Context) {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//
//        val request = OneTimeWorkRequestBuilder<UserSyncWorker>()
//            .setConstraints(constraints)
//            .build()
//        binding.progressBar.visibility = View.VISIBLE
//
//        WorkManager.getInstance(context).enqueue(request)
//        WorkManager.getInstance(context)
//            .getWorkInfoByIdLiveData(request.id)
//            .observe(this) { info ->
//                binding.progressBar.visibility = View.GONE
//                if (info != null && info.state.isFinished) {
//                    Toast.makeText(this, "Sync complete", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

//    override fun onResume() {
//        super.onResume()
//        scheduleUserSyncWork(applicationContext)
//    }
}

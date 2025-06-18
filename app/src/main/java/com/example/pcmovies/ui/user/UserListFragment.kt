package com.example.pcmovies.ui.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pcmovies.R
import com.example.pcmovies.databinding.FragmentUserListBinding
import com.example.pcmovies.worker.UserSyncWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserListViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private val navController by lazy { findNavController() }
    private var userJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        collectUsers()
        setupFab()

        binding.swipeRefreshLayout.setOnRefreshListener {
//            if (NetworkUtils.isOnline(requireContext()))
//                viewModel.refreshUsers()
//            else
//                binding.swipeRefreshLayout.isRefreshing = false
//                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()

            viewModel.refreshUsers()
            userAdapter.refresh()

        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter {
            if(NetworkUtils.isOnline(requireContext())){
                navController.navigate(R.id.action_userListFragment_to_movieListFragment)
            }else{
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
            }
            
        }

        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        UserAdapter { selectedUser ->
            findNavController().navigate(R.id.action_userListFragment_to_movieListFragment)
        }
    }

    private fun collectUsers() {
        userJob?.cancel()
        userJob = lifecycleScope.launch {
            viewModel.users.collectLatest {
                userAdapter.submitData(it)
                binding.recyclerView.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            userAdapter.loadStateFlow.collect { loadStates ->
                binding.recyclerView.scrollToPosition(0)
                binding.swipeRefreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    private fun setupFab() {
        binding.fabAddUser.setOnClickListener {
            navController.navigate(R.id.action_userListFragment_to_addUserFragment)
        }
    }

    private fun scheduleUserSyncWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<UserSyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueue(request)

        WorkManager.getInstance(context)
            .getWorkInfoByIdLiveData(request.id)
            .observe(viewLifecycleOwner) { info ->
                if (info != null && info.state.isFinished) {
//                    Toast.makeText(requireContext(), "Offline user syncing", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onResume() {
        super.onResume()
        scheduleUserSyncWork(requireContext())
        viewModel.refreshUsers()
        collectUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

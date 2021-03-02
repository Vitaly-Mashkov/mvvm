package com.mashkov.mvvm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mashkov.mvvm.databinding.FragmentUsersBinding
import com.mashkov.mvvm.network.apis.AppApi

@Suppress("UNCHECKED_CAST")
class UsersFVMFactory(
    private val api: AppApi,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserFVM(api) as T
    }
}

class UsersF : Fragment() {
    private val vm: UserFVM by activityViewModels { UsersFVMFactory(AppApi) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUsersBinding.inflate(inflater, container, false)
        vm.getUsers()
        vm.apply {
            users.observe(viewLifecycleOwner) {

                binding.text.text = it
                    .first().htmlURL

            }
        }
        return binding.root
    }
}
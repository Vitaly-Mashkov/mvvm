package com.mashkov.mvvm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.mashkov.mvvm.databinding.FragmentUserInfoBinding
import com.mashkov.mvvm.network.apis.GlobalApi

@Suppress("UNCHECKED_CAST")
class UserInfoFVMFactory(
    private val api: GlobalApi,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserInfoFVM(api) as T
    }
}

class UserInfoF: Fragment() {
    private val vm: UserInfoFVM by activityViewModels { UserInfoFVMFactory(GlobalApi) }
    private val args: UserInfoFArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        vm.getUser(args.login)
        vm.apply {
            user.observe(viewLifecycleOwner) {
                binding.user = it
            }
        }
        return binding.root
    }
}
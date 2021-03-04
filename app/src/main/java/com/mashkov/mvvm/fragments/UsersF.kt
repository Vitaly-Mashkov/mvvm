package com.mashkov.mvvm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mashkov.mvvm.adapters.UserAdapter
import com.mashkov.mvvm.databinding.FragmentUsersBinding
import com.mashkov.mvvm.fragments.dialogs.DiiaSystemDFVM
import com.mashkov.mvvm.models.SystemDialog
import com.mashkov.mvvm.network.apis.GlobalApi
import com.mashkov.mvvm.util.navigate

@Suppress("UNCHECKED_CAST")
class UsersFVMFactory(
    private val api: GlobalApi,
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserFVM(api) as T
    }
}

class UsersF : Fragment() {
    private val vm: UserFVM by activityViewModels { UsersFVMFactory(GlobalApi) }
    private val dialogVm: DiiaSystemDFVM by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUsersBinding.inflate(inflater, container, false)
        vm.getUsers()
        vm.apply {
            users.observe(viewLifecycleOwner) {
                binding.rvUsers.adapter = UserAdapter(it) { login ->
                    login?.let {
                        navigate(
                            UsersFDirections.actionUsersFrargmentToUserInfoF(login),
                            findNavController()
                        )
                    }
                }
            }
            error.observe(viewLifecycleOwner) {
                navigate(
                    UsersFDirections.actionGlobalToSystemDialog(
                        SystemDialog(
                            "Something going wrong!",
                            "Error occured",
                            "Retry",
                            "Close"
                        )
                    ), findNavController()
                )
            }
        }
        dialogVm.action.observe(viewLifecycleOwner) {
            when (it.getContentIfNotHandled()) {
                DiiaSystemDFVM.Action.POSITIVE ->
                    vm.getUsers()
                DiiaSystemDFVM.Action.NEGATIVE ->
                    activity?.finish()
            }
        }
        return binding.root
    }
}
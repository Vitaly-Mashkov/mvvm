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
import androidx.navigation.fragment.navArgs
import com.mashkov.mvvm.databinding.FragmentUserInfoBinding
import com.mashkov.mvvm.fragments.dialogs.DiiaSystemDFVM
import com.mashkov.mvvm.models.SystemDialog
import com.mashkov.mvvm.network.apis.GlobalApi
import com.mashkov.mvvm.util.navigate

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
    private val dialogVm: DiiaSystemDFVM by activityViewModels()
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
                    vm.getUser(args.login)
                DiiaSystemDFVM.Action.NEGATIVE ->
                    activity?.finish()
            }
        }
        return binding.root
    }
}
package com.tonynowater.mypokedex.app

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tonynowater.core.base.BaseActivity
import com.tonynowater.core.changeStatusBarColor
import com.tonynowater.core.repository.network.exception.ErrorState
import com.tonynowater.mypokedex.R
import com.tonynowater.mypokedex.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {

            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                supportActionBar?.title = destination.label ?: getString(R.string.app_name)

                if (destination.id == R.id.pokemonListFragment) {
                    changeStatusBarColor()
                }

                Timber.d("[DEBUG] OnDestinationChangedListener: controller:$controller, destination:$destination, args:$arguments")
            }
        }

    }

    override fun onError(errorState: ErrorState) {
        super.onError(errorState)
        dialogManager.showDoNothingDialog(
            this,
            getString(R.string.app_name),
            getString(R.string.error_no_network),
            getString(android.R.string.ok)
        )
    }
}

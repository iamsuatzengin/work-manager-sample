package com.suatzengin.workmanagersample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.suatzengin.workmanagersample.databinding.FragmentSecondBinding
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        val workManager = WorkManager.getInstance(requireContext())
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val data: Data = workDataOf(
            "KEY_TITLE" to "This is a PeriodicWorkRequest!"
        )

        val reminderWorker: WorkRequest =
            PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.MINUTES)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(data)
                .build()

        binding.btnSchedule.setOnClickListener {
            workManager.enqueue(reminderWorker)
        }

        binding.btnCancel.setOnClickListener {
            workManager.cancelWorkById(reminderWorker.id)
        }
        workManager.getWorkInfoByIdLiveData(reminderWorker.id)
            .observe(viewLifecycleOwner) { workInfo ->
                Snackbar.make(view, "State: ${workInfo.state.name}", Snackbar.LENGTH_LONG)
                    .setAction("OK", null).show()
                if (workInfo.state.isFinished) {
                    Snackbar.make(view, "bitti - ${workInfo.state.name}", Snackbar.LENGTH_LONG)
                        .setAction("OK", null).show()
                }

            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
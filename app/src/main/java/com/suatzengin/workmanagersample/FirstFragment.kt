package com.suatzengin.workmanagersample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.suatzengin.workmanagersample.databinding.FragmentFirstBinding
import java.util.concurrent.TimeUnit


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        val workManager = WorkManager.getInstance(requireContext())

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val myData: Data = workDataOf(
            "KEY_TITLE" to "This is an OneTimeWorkRequest!"
        )

        val workRequest: WorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setInputData(myData)
            .setConstraints(constraints)
            .build()

        binding.btnWorkRequest.setOnClickListener {
            workManager.enqueue(workRequest)
        }

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val myResult = workInfo.outputData.getString(ReminderWorker.KEY_OUTPUT_TEXT)
                    binding.textviewFirst.text = myResult ?: "da"
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
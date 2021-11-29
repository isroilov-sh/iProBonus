package com.test.iprobonusview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.iprobonusview.viewmodel.MyViewModelFactory
import com.test.iprobonusapi.repository.NetworkRepository
import com.test.iprobonusview.viewmodel.MainViewModel
import java.time.format.DateTimeFormatter

class MainFragment : Fragment() {

    private var accessKey: String? = null
    private var clientId: String? = null
    private var deviceId: String? = null

    private lateinit var viewModel: MainViewModel

    private lateinit var tvBonus: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvBurnBonus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            accessKey = it.getString(ARG_ACCESS_KEY)
            clientId = it.getString(ARG_CLIENT_ID)
            deviceId = it.getString(ARG_DEVICE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBonus = view.findViewById(R.id.tvBonus)
        tvDate = view.findViewById(R.id.tvBurn)
        tvBurnBonus = view.findViewById(R.id.tvBonusSecondary)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, MyViewModelFactory(NetworkRepository()))
            .get(MainViewModel::class.java)

        viewModel.getTokenData.observe(viewLifecycleOwner, { accessToken ->
            accessKey?.let {
                viewModel.getBonusData(it, accessToken.accessToken)
            }
        })

        viewModel.getBonusData.observe(viewLifecycleOwner, {
            val bonusText = "${it.data.currentQuantity} " +
                        "${context?.getString(R.string.bonus)}"

            val dateText = "${convertToDate(it.data.dateBurning)} " +
                        "${context?.getString(R.string.burn)}"

            val burnBonusText = "${it.data.forBurningQuantity} " +
                        "${context?.getString(R.string.bonus)}"

            tvBonus.text = bonusText
            tvDate.text = dateText
            tvBurnBonus.text = burnBonusText
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {

        })

        if (!(accessKey.isNullOrEmpty() || clientId.isNullOrEmpty() || deviceId.isNullOrEmpty()))
            viewModel.postAccessToken(accessKey!!, clientId!!, deviceId!!)
        else
            Log.e(TAG, "onViewCreated: gotNullArgument")
    }


    companion object {
        const val TAG = "MainFragment"

        private const val ARG_ACCESS_KEY = "accessKey"
        private const val ARG_CLIENT_ID = "clientId"
        private const val ARG_DEVICE_ID = "deviceId"

        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        private const val DATE_PATTERN = "dd.MM"

        @JvmStatic
        fun instanceWithParams(accessKey: String, clientId: String, deviceId: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ACCESS_KEY, accessKey)
                    putString(ARG_CLIENT_ID, clientId)
                    putString(ARG_DEVICE_ID, deviceId)
                }
            }

        @JvmStatic
        fun convertToDate(stringDate: String): String {
            val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
            val date = formatter.parse(stringDate)
            return DateTimeFormatter.ofPattern(DATE_PATTERN).format(date)
        }
    }
}
package com.bravo.invoice.ui.setupinfo


import android.Manifest
import android.animation.Animator
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.bravo.basic.extensions.clicks
import com.bravo.basic.extensions.hideKeyboard
import com.bravo.basic.extensions.showKeyboard
import com.bravo.invoice.databinding.BottomSheetEnterAddressBinding
import com.bravo.invoice.models.NearbyAddress
import com.bravo.invoice.ui.intro.NearbyAddressAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDispose
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class EnterAddressBottomSheet(
    private val currentAddress: String,
    private val result: (String) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetEnterAddressBinding
    @Inject lateinit var nearbyAddressAdapter: NearbyAddressAdapter
    private val locationManager: LocationManager by lazy {
        return@lazy requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = BottomSheetEnterAddressBinding.inflate(inflater, container, false)
        initView()
        initListeners()
        return binding.root
    }

    private fun initView() {
        binding.rvNearbyAddress.adapter = nearbyAddressAdapter
        binding.edAddress.apply {
            setText(currentAddress)
            setSelection(currentAddress.length)
            requestFocus()
            showKeyboard()
        }
    }

    private fun initListeners() {
        binding.edAddress.doOnTextChanged { text, _, _, _ ->
            if (text != null) {
                if(text.isEmpty()){
                    showLoading()
                }
            }
        }
        nearbyAddressAdapter.itemClicks.autoDispose(scope()).subscribe { address ->
            binding.edAddress.apply {
                setText(address)
                setSelection(text.length)
            }
        }
        binding.tvDone.clicks {
            result.invoke(binding.edAddress.text.toString())
            requireActivity().hideKeyboard()
            dismiss()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNearbyAddress()
    }

    private fun findNearbyAddress() {
        if (requireContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    showData(getAddressList(location))
                }
            }
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            showData(getAddressList(location))
        }
    }
    private fun showData(addressList : List<NearbyAddress>){
        binding.viewLoading.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
                binding.apply {
                    viewLoading.cancelAnimation()
                    viewLoading.isVisible = false
                    nearbyAddressAdapter.data = addressList
                    rvNearbyAddress.isVisible = true
                }

            }
        })
    }
    private fun showLoading(){
        binding.apply {
            rvNearbyAddress.isVisible = false
            viewLoading.isVisible = true
            viewLoading.playAnimation()
        }
        findNearbyAddress()
    }
    private fun getAddressList(location: Location?): List<NearbyAddress> {
        val geocoder = Geocoder(requireContext())
        val addresses = location?.let {
            geocoder.getFromLocation(it.latitude, it.longitude, 5)
        } ?: emptyList()
        val currentLocation = Location("current")
        currentLocation.latitude = location?.latitude ?: 0.0
        currentLocation.longitude = location?.longitude ?: 0.0
        return addresses.map { address ->
            val addressLocation = Location("address")
            addressLocation.latitude = address.latitude
            addressLocation.longitude = address.longitude
            val distanceInMeters = currentLocation.distanceTo(addressLocation)
            val distanceInKilometers = String.format(Locale.US, "%.1f", distanceInMeters / 1000.0).toFloat()
           val formattedAddress   =  address.getAddressLine(0).split(", ").take(3).joinToString(", ") + ", " + address.adminArea + ", " + address.countryName
            return@map NearbyAddress(formattedAddress  , distance = distanceInKilometers)
        }.take(5).toSet().toList()
    }
}
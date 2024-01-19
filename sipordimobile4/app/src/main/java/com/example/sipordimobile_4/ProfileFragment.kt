package com.example.sipordimobile_4

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.sipordimobile_4.R
import com.example.sipordimobile_4.databinding.FragmentProfileBinding
import com.google.android.material.navigation.NavigationView

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val rootView = binding.root

        // Inisialisasi VideoView
        val videoUri = Uri.parse("android.resource://${requireContext().packageName}/${R.raw.profil_polinema}")
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView2)
        binding.videoView2.setMediaController(mediaController)
        binding.videoView2.setVideoURI(videoUri)
        binding.videoView2.setOnPreparedListener {
            binding.videoView2.setOnClickListener { _ ->
                if (binding.videoView2.isPlaying) {
                    binding.videoView2.pause()
                } else {
                    binding.videoView2.start()
                }
            }
        }
// Inisialisasi ImageView Instagram
        val instagram = rootView.findViewById<ImageView>(R.id.instagram)
        instagram.setOnClickListener {
            val uri = Uri.parse("http://instagram.com/_u/polinema_campus")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            // Tambahkan kode untuk memeriksa apakah aplikasi Instagram terpasang pada perangkat
            intent.setPackage("com.instagram.android")

            // Jika aplikasi Instagram terpasang, buka halaman profil pengguna
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Jika aplikasi Instagram tidak terpasang, buka halaman Instagram melalui browser
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/polinema_campus"))
                startActivity(webIntent)
            }
        }

// Inisialisasi ImageView Website
        val website = rootView.findViewById<ImageView>(R.id.imageView3)
        website.setOnClickListener {
            val uri = Uri.parse("https://spmb.polinema.ac.id/info/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        // Inisialisasi Button
        val maps = rootView.findViewById<ImageView>(R.id.maps)
        maps.setOnClickListener {
            val latitude = -7.8021457
            val longitude = 111.9798302
            val locationName = "Lokasi Anda"
// Menambahkan deskripsi lokasi
            val uri = "geo:$latitude,$longitude?q=$latitude,$longitude($locationName)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }

        // Tindakan logout

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

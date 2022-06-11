package com.android.screen_capture.ui.screenshot

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.android.screen_capture.R
import com.android.screen_capture.databinding.FragmentScreenShotBinding
import java.io.File


class ScreenShotFragment : DialogFragment() {
    private lateinit var binding: FragmentScreenShotBinding

    private val args: ScreenShotFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScreenShotBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivScreenShot.setImageBitmap(BitmapFactory.decodeFile(args.image))
        binding.btSend.setOnClickListener {
            sendToGmail()
        }
        binding.btShare.setOnClickListener {
            shareScreenshot()
        }

    }

    private fun sendToGmail() {
        dismiss()
        Log.d("filename", args.image)
        val file = File(args.image)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("chohtet.227@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Screenshot Attachment")
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        intent.type = "image/jpeg"
        intent.data = Uri.parse("mailto:")

        if (intent.resolveActivity(requireActivity().packageManager) !=null){
            startActivity(intent)
        } else{
            Toast.makeText(requireContext(),getString(R.string.gmail_error_message),Toast.LENGTH_LONG).show()
        }

    }

    private fun shareScreenshot(){
        dismiss()
        Log.d("filename", args.image)
        val file = File(args.image)
        val uri = FileProvider.getUriForFile(requireContext().applicationContext,
            "${requireActivity().packageName}.fileprovider",file)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Screenshot Attachment")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.type = "image/*"

        if (intent.resolveActivity(requireActivity().packageManager) !=null){
            startActivity(Intent.createChooser(intent,"share screenshot"))
        } else{
            Toast.makeText(requireContext(),getString(R.string.gmail_error_message),Toast.LENGTH_LONG).show()
        }

    }
    /* private fun decodeByteArray(image:String):Bitmap{
         val byteArray = Base64.decode(image,Base64.DEFAULT)
         return  BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
     }*/

    /*override fun getTheme(): Int {
        return R.style.DialogTheme
    }*/

}
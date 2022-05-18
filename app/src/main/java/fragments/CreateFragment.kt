package fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.coffeerater.CoffeeShopModel
import com.example.coffeerater.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val REQUEST_CODE = 12

class CreateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    lateinit var photo: ImageView
    lateinit var imgBitmap: Bitmap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var button = requireView().findViewById<Button>(R.id.addButton)
        var nameInput = requireView().findViewById<TextView>(R.id.editName)
        var locationInput = requireView().findViewById<TextView>(R.id.editLocation)
        photo = requireView().findViewById<ImageView>(R.id.imageView3)

        button.setOnClickListener {
            if (TextUtils.isEmpty(nameInput.text))
                Toast.makeText(context, "Name required!", Toast.LENGTH_SHORT).show()
            else if (TextUtils.isEmpty(locationInput.text))
                Toast.makeText(context, "Location required!", Toast.LENGTH_SHORT).show()
            else {
                coffeeShopModels.add(CoffeeShopModel(nameInput.text.toString(), locationInput.text.toString(), imgBitmap))
                Toast.makeText(context, "Added entry!", Toast.LENGTH_SHORT).show()
                nameInput.text = ""
                locationInput.text = ""
                photo.setImageResource(R.drawable.ic_camera)
            }
        }

        photo.setOnClickListener {
            Toast.makeText(context, "Take a photo!", Toast.LENGTH_SHORT).show()
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }catch (e: ActivityNotFoundException)
            {
                Toast.makeText(context, "Could not complete action", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("poze","3")
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val resized = Bitmap.createScaledBitmap(data?.extras?.get("data") as Bitmap, 500, 500, true)
            Log.d("poze","1")
            photo.setImageBitmap(resized)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            val img = data?.extras?.get("data") as Bitmap
            val resized = Bitmap.createScaledBitmap(img, 500, 500, true)
            imgBitmap = resized
            photo.setImageBitmap(resized)

        }else
        {
            super.onActivityResult(requestCode,resultCode, data)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
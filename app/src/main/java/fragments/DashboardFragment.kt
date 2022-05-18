package fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeerater.CS_RecyclerViewAdapter
import com.example.coffeerater.CoffeeShopModel
import com.example.coffeerater.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

public var coffeeShopModels: MutableList<CoffeeShopModel> = ArrayList()
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var initiated: Boolean = false


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CS_RecyclerViewAdapter

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
        if (!initiated) {
            initiated = true
            setUpCoffeeShopModels()
        }
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = requireView().findViewById(R.id.cardRecyclerView)
        adapter = CS_RecyclerViewAdapter(coffeeShopModels)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        Log.d("Success", "Successsssss")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        var item: MenuItem = menu.findItem(R.id.action_search)
        var searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) : Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?) : Boolean {
                Log.d("yoo","yooo")
                adapter.filter.filter(newText)
                return false
            }
        });
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpCoffeeShopModels() {
        var coffeeShopNames: Array<String> = resources.getStringArray(R.array.nameOfCoffeeShop)
        var coffeeShopLocations: Array<String> = resources.getStringArray(R.array.locationOfCoffeeShop)

        for (i in coffeeShopNames.indices) {
            Log.d("index", coffeeShopNames[i])

            val conf = Bitmap.Config.ARGB_8888

            val bmp = Bitmap.createBitmap(30, 30, conf)

            coffeeShopModels.add(CoffeeShopModel(coffeeShopNames[i], coffeeShopLocations[i], bmp))
            //coffeeShopModels.add(CoffeeShopModel(coffeeShopNames[i], coffeeShopLocations[i], R.drawable.ic_shop))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.sociolla.pagination

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sociolla.R
import com.example.sociolla.data.remote.ApiConstants
import com.example.sociolla.data.remote.ApiConstants.Companion.STARTING_OFFSET
import com.example.sociolla.data.remote.sources.HorizontalPhoto
import com.example.sociolla.databinding.ActivityMainBinding
import com.example.sociolla.utils.EndlessScrollListener
import com.example.sociolla.utils.Utilities
import com.mingle.chatapp.chat.multiviewadapter.AdapterDataModel
import com.mingle.chatapp.chat.multiviewadapter.MultiMovieViewAdapter
import com.mingle.chatapp.chat.multiviewadapter.ViewModelTypeFactoryImpl.Companion.HORIZONTAL
import com.mingle.chatapp.chat.multiviewadapter.ViewModelTypeFactoryImpl.Companion.VERTICAL
import com.mingle.chatapp.data.remote.sources.VerticalPhoto
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter: MultiMovieViewAdapter

    private var movieDataList : ArrayList<AdapterDataModel> = ArrayList(arrayListOf())

    private var verticalImageDataList : ArrayList<VerticalPhoto> = ArrayList(arrayListOf())
    private var verticalHashSet : HashSet<VerticalPhoto> = HashSet(hashSetOf())

    private var horizontalHashSet : HashSet<HorizontalPhoto> = HashSet(hashSetOf())
    private var horizontalImageDataList : ArrayList<HorizontalPhoto> = ArrayList(arrayListOf())

    private lateinit var binding: ActivityMainBinding
    private var count : Int = 0
    private var horizontal_count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (Utilities.isNetworkConnected(this@MainActivity)) {
            viewModel.getData(ApiConstants.GET_PHOTO_METHOD, ApiConstants.API_KEY, ApiConstants.RESPONSE_FORMAT, STARTING_OFFSET)
            cl_no_internet.visibility = View.GONE
        }else{
            cl_no_internet.visibility = View.VISIBLE
        }

        binding.showRecyclerView = false
        initAdapter()
        addScrolling()
        addObserver()
    }

    fun initAdapter(){

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        rv_list.layoutManager = linearLayoutManager

        rv_list.adapter = MultiMovieViewAdapter.newInstance()
                .also {
                    adapter = it
                }
    }

    fun addScrolling(){

        val endlessScrollListener : EndlessScrollListener = object : EndlessScrollListener(linearLayoutManager){
            override fun onLoadMore(current_page: Int) {

                if (Utilities.isNetworkConnected(this@MainActivity)) {
                    cl_no_internet.visibility = View.GONE
                    viewModel.getMoreData(ApiConstants.GET_PHOTO_METHOD, ApiConstants.API_KEY, ApiConstants.RESPONSE_FORMAT, current_page)
                }else{
                    cl_no_internet.visibility = View.VISIBLE
                }
            }
        }
        rv_list.addOnScrollListener(endlessScrollListener)
    }

    fun addObserver(){

        viewModel.zippedLiveData.observe(this, Observer {
            showRecyclerView(true)

            val verticalImageData = it.verticalDataModel.photos.photo
            val horizontalImageData = it.horizontalDataModel.photos.photo

            //for vetical
            for (i in 0 until verticalImageData.size){
                this.verticalHashSet.add(verticalImageData[i])
            }
            verticalImageDataList.clear()
            verticalImageDataList.addAll(verticalHashSet)

            //for horizontal
            for (i in 0 until horizontalImageData.size){
                horizontalHashSet.add(horizontalImageData[i])
            }
            horizontalImageDataList.clear()
            horizontalImageDataList.addAll(horizontalHashSet)

            appendData(verticalImageDataList, horizontalImageDataList)

        })

        viewModel.moreZippedLiveData.observe(this, Observer {
            showRecyclerView(true)

            val moreVerticalImgData = it.verticalDataModel.photos.photo
            val moreHorizontalImgData = it.horizontalDataModel.photos.photo

            for (i in 0 until moreVerticalImgData.size){
                verticalHashSet.add(moreVerticalImgData[i])
            }
            verticalImageDataList.clear()
            verticalImageDataList.addAll(verticalHashSet)

            for (i in 0 until moreHorizontalImgData.size){
                horizontalHashSet.add(moreHorizontalImgData[i])
            }
            horizontalImageDataList.clear()
            horizontalImageDataList.addAll(horizontalHashSet)

            moreAppendData(verticalImageDataList, horizontalImageDataList)

        })

        viewModel.isLoading.observe(this, Observer {

            showLoading(it)
        })

        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(this, "Please try again !", Toast.LENGTH_SHORT).show()
        })
    }

    fun appendData(verticalImageData: List<VerticalPhoto>, horizontalImageData: List<HorizontalPhoto>) {
        count = verticalImageData.size
        for (i in 0 until verticalImageData.size) {
            if (i != 0 && i % 3 == 0) {
                addHorizontalView(horizontalImageData)
            } else {
                movieDataList.add(AdapterDataModel(verticalImageData.get(i).id, verticalImageData.get(i).title, verticalImageData.get(i).farm, verticalImageData.get(i).server, verticalImageData.get(i).secret, VERTICAL))
            }
        }
        adapter.submitList(movieDataList)
    }

    fun moreAppendData(verticalImageData: List<VerticalPhoto>, moreHorizontalImgData: List<HorizontalPhoto>) {
        for (i in count until verticalImageData.size) {
            if (i != 0 && i % 3 == 0) {
                addHorizontalView(moreHorizontalImgData)
            } else {
                movieDataList.add(AdapterDataModel(verticalImageData.get(i).id, verticalImageData.get(i).title, verticalImageData.get(i).farm, verticalImageData.get(i).server, verticalImageData.get(i).secret, VERTICAL))
            }
            count++
        }
        adapter.submitList(movieDataList)
    }

    fun showLoading(isLoading: Boolean) {
        binding.showLoading = isLoading
    }

    fun showRecyclerView(isVisible: Boolean) {
        binding.showRecyclerView = isVisible
    }

    fun addHorizontalView(horizontalListData: List<HorizontalPhoto>) {

        var k = 0
        var horizontalListItems : ArrayList<HorizontalPhoto> = arrayListOf()
        horizontalListItems.clear()

        for (i in horizontal_count until horizontalListData.size){
            if (k < 5){
                horizontalListItems.add(horizontalListData[i])
                horizontal_count++
                k++
            }else{
                break
            }
        }
        movieDataList.add(AdapterDataModel(type = HORIZONTAL, horizontalList = horizontalListItems as ArrayList))

    }

}
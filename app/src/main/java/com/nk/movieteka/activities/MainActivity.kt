package com.nk.movieteka.activities

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nk.movieteka.adapters.PromotionAdapter
import com.nk.movieteka.models.MainViewModel
import android.view.View
import android.graphics.drawable.ColorDrawable
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.nk.movieteka.helpers.RecyclerItemClickListener
import com.nk.movieteka.models.MovieModel
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.nk.movieteka.R


class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel? = null
    lateinit var recyclerView: RecyclerView
    var progressBar : ProgressBar? = null
    var searchEditText : EditText? = null
    var isSearch : Boolean = false
    var myDialog: Dialog? = null
    var movies: List<MovieModel>? = null
    var clickPosition : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDialog = Dialog(this);
        progressBar = findViewById(R.id.progressBar)
        searchEditText = findViewById(R.id.searchEditText)

        // bind RecyclerView
        recyclerView = findViewById(R.id.viewEmployees)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setHasFixedSize(true)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java!!)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    showProgressView()
                    mainViewModel!!.page++
                    if (!isSearch) {
                        getAllPopularFilms()
                    } else searchFilms(searchEditText!!.text.toString())
                }
            }
        })

        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerView,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        clickPosition = position
                        showPopup()
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                    }
                })
        )
        // Onetime load first page with popular movies
        getAllPopularFilms()
    }

    private fun getAllPopularFilms() {
        mainViewModel!!.allPopularMovies.observe(this, Observer { items ->
            movies = items
            if (mainViewModel!!.page == 1) {
                recyclerView.adapter = PromotionAdapter(items)
            } else {
                recyclerView.adapter!!.notifyDataSetChanged()
                hideProgressView()
            }
        })
    }

    private fun searchFilms(query : String) {
        mainViewModel!!.query = query
        mainViewModel!!.searchMovies.observe(this, Observer { items ->
            movies = items
            if (mainViewModel!!.page == 1) {
                recyclerView.adapter = PromotionAdapter(items)
            } else {
                recyclerView.adapter!!.notifyDataSetChanged()
                hideProgressView()
            }
        })
    }

    fun search(view: View) {
        mainViewModel!!.page = 1
        isSearch = true
        if (searchEditText!!.text.length > 0)
        {
            searchFilms(searchEditText!!.text.toString())
        } else Toast.makeText(this@MainActivity, "Enter any movie name", Toast.LENGTH_SHORT).show()
    }

    fun showProgressView() {
        progressBar!!.visibility = (View.VISIBLE)
        progressBar!!.alpha = 1f
    }

    fun hideProgressView() {
        progressBar!!.animate().alpha(0f).setDuration(100)
    }

    fun showPopup() {
        var imageView : ImageView
        var title : TextView
        var overview : TextView
        var card : CardView

        myDialog!!.setContentView(R.layout.popup_window)
        card = myDialog!!.findViewById(R.id.cardView)
        var mSwiping : Boolean = false
        card.setOnTouchListener(object : View.OnTouchListener {
            var mDownY: Float = 0.toFloat()
            private var mSwipeSlop = -1

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (mSwipeSlop < 0) {
                    mSwipeSlop = ViewConfiguration.get(this@MainActivity).scaledTouchSlop
                }
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        mDownY = event.y
                    }
                    MotionEvent.ACTION_CANCEL -> myDialog!!.cancel()
                    MotionEvent.ACTION_MOVE -> {
                        val y = event.y + v.translationY
                        val deltaY = y - mDownY
                        mSwiping = false
                        var mAlpha = 1 - deltaY / (v.width/2)
                        if (!mSwiping) {
                            mSwiping = true
                            if (deltaY > mSwipeSlop) {
                                mAlpha = 1 - deltaY / (v.width/2)
                            } else {
                                mAlpha = 1 + deltaY / (v.width/2)
                            }
                        }
                        if (mSwiping) {
                            v.alpha = mAlpha
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if(v.alpha <= 0.2f) {
                            myDialog!!.cancel()
                        } else {
                            v.alpha = 1f
                        }
                    }
                    else -> return false
                }
                return true
            }
        })
        imageView = myDialog!!.findViewById(R.id.image)
        title = myDialog!!.findViewById(R.id.title)
        overview = myDialog!!.findViewById(R.id.overview)
        title.text = movies!!.get(clickPosition!!).title
        overview.text = movies!!.get(clickPosition!!).overivew
        Glide.with(this).load("https://image.tmdb.org/t/p/original" +movies!!.get(clickPosition!!).backdrop_path).into(imageView)
        myDialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog!!.setCancelable(false)
        myDialog!!.show()
    }
}

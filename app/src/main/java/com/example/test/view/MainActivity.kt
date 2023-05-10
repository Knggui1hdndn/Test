package com.example.test.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.RealPathUtil
import com.example.test.model.Animation
import com.example.test.model.dao.AnimationDao
import com.example.test.model.dao.DataBase
import com.example.test.view.adapter.MainAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private val pickMultipleMedia =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { uris ->
            if (uris.data?.data != null) {
                val uri = uris.data?.data
                val intent = Intent(this@MainActivity, ShowActivity::class.java)
                intent.putExtra("ani",
                    uri?.let { RealPathUtil.getRealPath(applicationContext, it).toString() }?.let {
                        Log.d("shakk", it)
                        Animation(
                            0,
                            it,
                            repeat = true,
                            dateTime = true,
                            lever = true,
                            type = "video",
                            selected = false
                        )
                    })
                intent.putExtra("add", true)
                startActivity(intent)
            }
        }


    private lateinit var rcy: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var dao: AnimationDao
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "video/*"
                pickMultipleMedia.launch(intent)
            } else {
               Toast.makeText(applicationContext,"Bạn chưa cấp quyền truy cập vào nội dung",Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "BatteryLife")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcy = findViewById(R.id.rcy)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        dao = DataBase.getDatabase(this).dao()
        setSupportActionBar(toolbar)
        findViewById<FloatingActionButton>(R.id.fbtn).setOnClickListener {

            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)


        }

        if (dao.getAll().isEmpty()) {
            dao.insert(
                Animation(
                    0, R.drawable.img.toString(), true, true, true, "fadeAnimation", false
                )
            )
            dao.insert(
                Animation(
                    0, R.drawable.img_1.toString(), true, true, true, "scaleAnimation", false
                )
            )
            dao.insert(
                Animation(
                    0, R.drawable.img_2.toString(), true, true, true, "translateAnimation", false
                )
            )
            dao.insert(
                Animation(
                    0, R.drawable.img_3.toString(), true, true, true, "rotateAnimation", false
                )
            )
        }
        Log.d("ssssssssss", dao.getAll().toString())
        rcy.layoutManager = GridLayoutManager(this, 2)
        mainAdapter = MainAdapter(dao.getAll())
        rcy.adapter = mainAdapter


    }

    override fun onResume() {
        super.onResume()
        mainAdapter.notify(dao.getAll())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            startActivity(Intent(this, Settings::class.java))
        }
        return true
    }
}

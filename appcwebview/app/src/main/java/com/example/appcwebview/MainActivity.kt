package com.example.appcwebview
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView
import com.example.appcwebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener,
    NavigationBarView.OnItemSelectedListener {
    lateinit var b: ActivityMainBinding
    lateinit var ft: FragmentTransaction
    lateinit var fragmentWebView: FragmentWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        fragmentWebView = FragmentWebView()
        b.btYt.setOnClickListener(this)
        b.btTiktok.setOnClickListener(this)
        b.btMessage.setOnClickListener(this)
        b.btData.setOnClickListener(this)
        b.bottomNavigationView.setOnItemSelectedListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btYt -> replaceFragment("https://youtube.com")
            R.id.btTiktok -> replaceFragment("https://tiktok.com")
            R.id.btMessage -> replaceFragment(
                "https://rnfrenp.000webhostapp.com/form_send_message.php"
            )
            R.id.btData -> openNewActivity()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemHome -> b.frameLayout.visibility = View.GONE
            R.id.itemLoc -> replaceFragment("https://www.google.co.id/maps/place/PSDKU+Politeknik+Negeri+Malang+di+Kota+Kediri/@-7.8025621,111.9772606,17z/data=!3m1!4b1!4m6!3m5!1s0x2e7856c8ee550497:0x3f8ff2e0bc9b9718!8m2!3d-7.8025674!4d111.9798355!16s%2Fg%2F11c6f2rz1c?entry=ttu")
            R.id.itemProfil -> replaceFragment(
                "https://psdkukediri.polinema.ac.id/sejarah-singkat/"
            )
            R.id.itemShop -> replaceFragment("https://www.shopee.co.id")
        }
        return true
    }

    override fun onKeyDown(KeyCode: Int, event: KeyEvent?): Boolean {
        if (b.frameLayout.isVisible == true) {
            if (KeyCode.equals(KeyEvent.KEYCODE_BACK) &&
                fragmentWebView.b.webV.canGoBack()
            ) {
                fragmentWebView.b.webV.goBack()
                return true
            } else {
                b.frameLayout.visibility = View.GONE
                return true
            }
        }
        return super.onKeyDown(KeyCode, event)
    }


    fun replaceFragment(url: String) {
        GlobalVariables.url = url
        b.frameLayout.visibility = View.GONE
        ft = supportFragmentManager.beginTransaction()
        ft.remove(fragmentWebView).commitNow()
        ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameLayout, fragmentWebView).commit()
        b.frameLayout.visibility = View.VISIBLE
        }

    private fun openNewActivity() {
        // Replace NewActivity::class.java with the actual class for your new activity
        val intent = Intent(this, DataActivity::class.java)
        startActivity(intent)
    }
}
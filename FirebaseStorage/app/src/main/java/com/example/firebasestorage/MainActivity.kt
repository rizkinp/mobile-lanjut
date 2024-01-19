package com.example.firebasestorage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.ArrayList
import java.util.HashMap

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var storage: FirebaseStorage
    lateinit var db: CollectionReference
    lateinit var alFile: ArrayList<HashMap<String, Any>>
    lateinit var adapter: CustomAdapter
    lateinit var uri: Uri
    val F_NAME = "file_name"
    val F_TYPE = "file_type"
    val F_URL = "file_url"
    val RC_OK = 100
    var fileType = ""
    var fileName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val btnUpPdf = findViewById<Button>(R.id.btnUpPdf)
        val btnUpWord = findViewById<Button>(R.id.btnUpWord)
        val btnUpVideo = findViewById<Button>(R.id.btnUpVideo)
        val btnUpImage = findViewById<Button>(R.id.btnUpImage)
        val btnUpload = findViewById<Button>(R.id.btnUpload)

        btnUpPdf.setOnClickListener(this)
        btnUpWord.setOnClickListener(this)
        btnUpVideo.setOnClickListener(this)
        btnUpImage.setOnClickListener(this)
        btnUpload.setOnClickListener(this)

        storage = Firebase.storage
        alFile = ArrayList()
        uri = Uri.EMPTY
    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance().collection("files")
        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null) {
                firebaseFirestoreException.message?.let {
                    Log.e("firestore :", it)
                }
            }
            showData()
        }
    }

    fun showData() {
        db.get().addOnSuccessListener { result ->
            alFile.clear()
            for (doc in result) {
                val hm = HashMap<String, Any>()
                hm[F_NAME] = doc.get(F_NAME).toString()
                hm[F_TYPE] = doc.get(F_TYPE).toString()
                hm[F_URL] = doc.get(F_URL).toString()
                alFile.add(hm)
            }
            adapter = CustomAdapter(this, alFile)
            val listView = findViewById<ListView>(R.id.lsV)
            listView.adapter = adapter
        }
            .addOnFailureListener { }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if ((resultCode == Activity.RESULT_OK) && (requestCode == RC_OK)) {
            if (intent != null) {
                uri = intent.data!!
                val cursor = contentResolver.query(
                    uri, null,
                    null, null, null
                )
                val COL_FILE_NAME =
                    cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                fileName = cursor.getString(COL_FILE_NAME)
                findViewById<MaterialTextView>(R.id.txSelectedFile).text = uri.toString()

            }
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        when (v?.id) {
            R.id.btnUpWord -> {
                fileType = "Docx"
                intent.type =
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            }
            R.id.btnUpVideo -> {
                fileType = "Video"
                intent.type = "video/*"
            }
            R.id.btnUpImage -> {
                fileType = "Image"
                intent.type = "image/*"
            }
            R.id.btnUpPdf -> {
                fileType = "PDF"
                intent.type = "application/pdf"
            }
            R.id.btnUpload -> {
                if (uri != null) {
                    val fileRef = storage.reference.child(fileName)
                    val uploadTask = fileRef.putFile(uri)
                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        fileRef.downloadUrl
                    }
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val hm = HashMap<String, Any>()
                                hm[F_NAME] = fileName
                                hm[F_TYPE] = fileType
                                hm[F_URL] = task.result.toString()
                                db.document(fileName).set(hm)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            this, "File successfully uploaded",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        findViewById<MaterialTextView>(R.id.txSelectedFile).text = ""
                                    }
                            }
                        }
                }
            }
        }
        if (v?.id != R.id.btnUpload) {
            startActivityForResult(intent, RC_OK)
        }
    }
}


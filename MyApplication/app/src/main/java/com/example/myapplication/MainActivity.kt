package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var edId: TextInputEditText
    private lateinit var edName: TextInputEditText
    private lateinit var edAddress: TextInputEditText
    private lateinit var edPhone: TextInputEditText
    private lateinit var btnInsert: MaterialButton
    private lateinit var btnUpdate: MaterialButton
    private lateinit var btnDelete: MaterialButton
    private lateinit var listView: ListView

    private lateinit var firestore: FirebaseFirestore
    private lateinit var siswaList: MutableList<Mahasiswa>
    private lateinit var adapter: ArrayAdapter<Mahasiswa>

    private lateinit var databaseReference: DatabaseReference
    private lateinit var siswaListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edId = findViewById(R.id.edId)
        edName = findViewById(R.id.edName)
        edAddress = findViewById(R.id.edAddress)
        edPhone = findViewById(R.id.edPhone)
        btnInsert = findViewById(R.id.btnInsert)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        listView = findViewById(R.id.listView)

        firestore = FirebaseFirestore.getInstance()
        siswaList = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, siswaList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedSiswa = siswaList[position]
            fillEditText(selectedSiswa)
        }


        btnInsert.setOnClickListener {
            val name = edName.text.toString()
            val address = edAddress.text.toString()
            val phone = edPhone.text.toString()

            if (name.isNotBlank() && address.isNotBlank() && phone.isNotBlank()) {
                val siswa = Mahasiswa(
                    name = name,
                    address = address,
                    phone = phone
                )
                addSiswaToFirebase(siswa)
            }
        }

        btnUpdate.setOnClickListener {
            val id = edId.text.toString()
            val name = edName.text.toString()
            val address = edAddress.text.toString()
            val phone = edPhone.text.toString()

            if (id.isNotBlank() && name.isNotBlank() && address.isNotBlank() && phone.isNotBlank()) {
                val siswa = Mahasiswa(
                    id = id,
                    name = name,
                    address = address,
                    phone = phone
                )

                updateSiswaInFirebase(siswa)
            }
        }

        btnDelete.setOnClickListener {
            val id = edId.text.toString()

            if (id.isNotBlank()) {
                deleteSiswaFromFirebase(id)
            }
        }

        // Inisialisasi Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().reference.child("mahasiswa")

        // ...

        loadSiswaFromFirebase()
    }

    private fun fillEditText(siswa: Mahasiswa) {
        edId.setText(siswa.id)
        edName.setText(siswa.name)
        edAddress.setText(siswa.address)
        edPhone.setText(siswa.phone)
    }

//    =================REALTIME DATABASE==================
private fun addSiswaToFirebase(siswa: Mahasiswa) {
    val id = edId.text.toString()
    val siswaWithId = siswa.copy(id = id)

    // Simpan data ke Firebase Realtime Database
    databaseReference.child(id).setValue(siswaWithId)
        .addOnSuccessListener {
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            clearInputFields()
        }
        .addOnFailureListener { e ->
            // Handle error
        }
}

    private fun updateSiswaInFirebase(siswa: Mahasiswa) {
        // Simpan data yang diperbarui ke Firebase Realtime Database
        databaseReference.child(siswa.id).setValue(siswa)
            .addOnSuccessListener {
                Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                clearInputFields()
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }

    private fun deleteSiswaFromFirebase(id: String) {
        // Hapus data dari Firebase Realtime Database
        databaseReference.child(id).removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                clearInputFields()
            }
            .addOnFailureListener { e ->
                // Handle error
            }
    }

    private fun loadSiswaFromFirebase() {
        siswaListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                siswaList.clear()
                for (snapshot in dataSnapshot.children) {
                    val siswa = snapshot.getValue(Mahasiswa::class.java)
                    siswa?.id = snapshot.key ?: ""
                    siswaList.add(siswa ?: continue)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }


//    ============FIRESTORE DATABASE=============
//    private fun addSiswaToFirestore(siswa: Mahasiswa) {
//        val id = edId.text.toString()
//        val siswaWithId = siswa.copy(id = id)
//
//        firestore.collection("mahasiswa")
//            .document(id)
//            .set(siswaWithId)
//            .addOnSuccessListener {
//                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
//                clearInputFields()
//            }
//            .addOnFailureListener { e ->
//                // Handle error
//            }
//    }
//
//    private fun updateSiswaInFirestore(siswa: Mahasiswa) {
//        firestore.collection("mahasiswa")
//            .document(siswa.id)
//            .set(siswa)
//            .addOnSuccessListener {
//                Toast.makeText(this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
//                clearInputFields()
//            }
//            .addOnFailureListener { e ->
//
//            }
//    }
//
//    //delete data dari firestore
//    private fun deleteSiswaFromFirestore(id: String) {
//        firestore.collection("mahasiswa")
//            .document(id)
//            .delete()
//            .addOnSuccessListener {
//                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
//                clearInputFields()
//            }
//            .addOnFailureListener { e ->
//
//            }
//    }


//    private fun loadSiswaFromFirestore() {
//        firestore.collection("mahasiswa")
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    return@addSnapshotListener
//                }
//
//                siswaList.clear()
//                for (doc in snapshot?.documents ?: emptyList()) {
//                    val siswa = doc.toObject(Mahasiswa::class.java)
//                    siswa?.id = doc.id
//                    siswaList.add(siswa ?: continue)
//                }
//                adapter.notifyDataSetChanged()
//            }
//    }

    private fun clearInputFields() {
        edId.text?.clear()
        edName.text?.clear()
        edAddress.text?.clear()
        edPhone.text?.clear()
    }
}


package com.styvencatagua.pr15_miniagendaambrecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val contacts = ArrayList<Contact>()
    private lateinit var adapter: ContactAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateContacts()

        adapter = ContactAdapter(contacts)
        recycler = findViewById(R.id.contactsRecycler)
        recycler.layoutManager = LinearLayoutManager(this) // imprescindible!
        recycler.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showAddContactDialog()
        }
    }

    private fun generateContacts() {
        val names = listOf("Anna", "Marc", "Laia", "Pau", "Núria",
            "Jordi", "Marta", "Arnau", "Clara", "Oriol")
        val surnames = listOf("Garcia", "Martí", "Puig", "Ferrer", "Soler",
            "Vidal", "Roca", "Serra", "Pons", "Camps")

        repeat(40) {
            val fullName = "${names.random()} ${surnames.random()}"
            val phone = (100000000..999999999).random().toString()
            contacts.add(Contact(fullName, phone))
        }
    }

    private fun showAddContactDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null)
        val inputName = view.findViewById<EditText>(R.id.inputName)
        val inputPhone = view.findViewById<EditText>(R.id.inputPhone)

        MaterialAlertDialogBuilder(this)
            .setTitle("Nou contacte")
            .setView(view)
            .setPositiveButton("Afegir") { _, _ ->
                val name = inputName.text.toString().trim()
                val phone = inputPhone.text.toString().trim()
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(this, "Omple tots els camps", Toast.LENGTH_SHORT).show()
                } else {
                    contacts.add(0, Contact(name, phone))
                    adapter.notifyItemInserted(0)
                    recycler.scrollToPosition(0)
                }
            }
            .setNegativeButton("Cancel·lar", null)
            .show()
    }
}
package edu.uw.info448.cocktailcreations

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserDrinkAdapter (private var userDrinkList: MutableList<Cocktail>):
    RecyclerView.Adapter<UserDrinkAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cocktailImage: ImageView = view.findViewById(R.id.drink_thumbnail)
        val cocktailName: TextView = view.findViewById(R.id.drink_name)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_drink_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_user_cocktail, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = userDrinkList[position]
        val decodedString: ByteArray = item.image.toByteArray()
        val parsedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        holder.cocktailImage.setImageBitmap(parsedBitmap)
        holder.cocktailName.text = item.name
        holder.deleteButton.setOnClickListener {
            removeDrink(item)
        }
    }

    override fun getItemCount(): Int {
        return userDrinkList.size
    }

    private fun removeDrink(drink: Cocktail) {
        userDrinkList.remove(drink)
        val user = Firebase.auth.currentUser
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(user!!.uid)
            .update("drinks", FieldValue.arrayRemove(drink))
        notifyDataSetChanged()
    }
}
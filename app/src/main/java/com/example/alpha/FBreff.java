package com.example.alpha;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBreff {

    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refUser = FBDB.getReference("Users");

    //public static DatabaseReference reff = FBDB.getReference("Alpha");

}

package com.example.alpha;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBreff {

    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refUsers = FBDB.getReference("Users");

    public static DatabaseReference reff = FBDB.getReference("Alpha");

    public static StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();



}

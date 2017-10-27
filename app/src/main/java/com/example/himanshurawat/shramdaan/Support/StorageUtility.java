package com.example.himanshurawat.shramdaan.Support;


import com.google.firebase.storage.FirebaseStorage;

public class StorageUtility {
    //Storage Root Reference
    private static FirebaseStorage firebaseStorage;

    public static FirebaseStorage getFirebaseStorageReference(){
        if(firebaseStorage==null){
            firebaseStorage= FirebaseStorage.getInstance();
        }
        return firebaseStorage;
    }

}

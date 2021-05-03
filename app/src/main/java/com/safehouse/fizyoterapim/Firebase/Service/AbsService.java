package com.safehouse.fizyoterapim.Firebase.Service;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.safehouse.fizyoterapim.Firebase.Model.Form;
import com.safehouse.fizyoterapim.Firebase.Model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbsService {

    public static final String FORMCOLLECTIONNAME = "form";
    public static final String EGZERSİZCOLLECTIONNAME = "exercise";
    public static final String RANDEVUCOLLECTIONNAME = "date";
    public static final String USERCOLLECTIONNAME = "user";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String,Object> objectMap = new HashMap<>();
    private Response response = new Response();


    public Response add(String collectionName, Object o){

         db.collection(collectionName)
                 .add(o)
                 .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                     @Override
                     public void onSuccess(DocumentReference documentReference) {

                         response.setObject(documentReference.getId());
                         Log.d("Firebase Add", "onSuccess");

                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {

                         response.setErrorMessage(e.getMessage());
                         Log.d("Firebase Add", "onFailed " + e.getMessage());
                     }
                 });
         return response;
     }

    public Response get(String collectionName, String queryName, String queryValue,Class<?> clss){


        if(queryName.length() == 0){

            db.collection(collectionName)
                    .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                            if(e == null){
                                response.setObject(queryDocumentSnapshots.toObjects(clss));
                            }
                            else{
                                response.setErrorMessage(e.getMessage());
                            }


                            Log.d("Firebase get", new Gson().toJson(response));
                        }
                    });
        }

        else{

            db.collection(collectionName)
                    .whereEqualTo(queryName,queryValue)
                    .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            if(e == null){
                                response.setObject(queryDocumentSnapshots.toObjects(clss));
                            }
                            else{
                                response.setErrorMessage(e.getMessage());
                            }

                            Log.d("Firebase get", "onSuccess: " + new Gson().toJson(response));
                        }
                    });
        }



        return response;

    }

    public void update(String collectionName, HashMap<String,Object> map,String id){

         db.collection(collectionName)
                 .document(id)
                 .update(map)
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Log.d("Firebase update", "onSuccess: ");

                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.d("Firebase update", "onFailed: " + e.getMessage());

                     }
                 });


     }

    public void delete(String collectionName, String id){

         db.collection(collectionName).document(id)
                 .delete()
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Log.d("Firebase delete", "onSuccess");
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.w("Firebase delete", "Error deleting document", e);
                     }
                 });


     }


}

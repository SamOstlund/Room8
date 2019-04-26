package com.example.room8;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class user
{

/*Start of member data*/
  private String firstName;
  private String lastName;
  private int age;
  private int zipCode;
  private String bio;
  private int maxPrice;
  private int minPrice;
  private String ID;
  private String profileUrl;
  private FirebaseAuth mAuth;
  private DatabaseReference databaseReference;

/*End of member data*/

  /*Constructor,setter,and getters below*/
   public user(String first, String last, int ag, int zip, String biography, int max, int min, String uniqueID, String URL)
   {
       this.firstName = first;
       this.lastName = last;
       this.age = ag;
       this.zipCode = zip;
       this.bio = biography;
       this.maxPrice = max;
       this.minPrice = min;
       this.ID = uniqueID;
       this.profileUrl = URL;
   }

   public user(final String userID)
   {
       mAuth = FirebaseAuth.getInstance();
       databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

       databaseReference.addValueEventListener(new ValueEventListener()
       {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {
               if (dataSnapshot.exists())
               {
                   if (dataSnapshot.child("firstName").getValue() != null)
                    firstName = dataSnapshot.child("firstName").getValue().toString();
                   if (dataSnapshot.child("lastName").getValue() != null)
                    lastName = dataSnapshot.child("lastName").getValue().toString();
                   if (dataSnapshot.child("age").getValue() != null)
                     age = (int) dataSnapshot.child("age").getValue();
                   if (dataSnapshot.child("zip").getValue() != null)
                    zipCode = (int) dataSnapshot.child("zip").getValue();
                   if (dataSnapshot.child("bio").getValue() != null)
                    bio = dataSnapshot.child("bio").getValue().toString();
                   if (dataSnapshot.child("maxprice").getValue() != null)
                    maxPrice = (int) dataSnapshot.child("maxprice").getValue();
                   if (dataSnapshot.child("minprice").getValue() != null)
                    minPrice = (int) dataSnapshot.child("minprice").getValue();
                   if (dataSnapshot.child("profileImageUrl").getValue() != null)
                     profileUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                   ID = userID;
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError)
           {
               return;
           }
       });

   }

   public user(String fname, int value)
   {
       firstName = fname;
   }

   public void setProfileUrl(String URL)
   {
    profileUrl = URL;
   }

   public String getProfileUrl(){
       return profileUrl;
    }

   public String getFirstName()
   {
       return firstName;
   }

   public String getLastName()
   {
       return lastName;
   }

   public int getAge()
   {
       return age;
   }

   public int getZipCode()
   {
       return zipCode;
   }

   public String getBio()
   {
       return bio;
   }

   public int getMaxPrice()
   {
       return maxPrice;
   }

   public int getMinPrice()
   {
       return minPrice;
   }

   public String getID()
   {
       return ID;
   }
}

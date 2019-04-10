package com.example.room8;

public class user
{

/*Start of member data*/
  private String firstName;
  private  String lastName;
  private int age;
  private int zipcode;
  private   String bio;
  private    int maxPrice;
  private int minPrice;
  private String ID;
  private String profileUrl;

/*End of member data*/

  /*Constructor,setter,and getters below*/
   public user(String first, String last, int ag, int zip, String biography, int max, int min, String uniqueID)
   {
       this.firstName = first;
       this.lastName = last;
       this.age = ag;
       this.zipcode = zip;
       this.bio = biography;
       this.maxPrice = max;
       this.minPrice = min;
       this.ID = uniqueID;
   }
   public void setProfileUrl(String URL){{
    profileUrl = URL;
   }
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
       return zipcode;
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

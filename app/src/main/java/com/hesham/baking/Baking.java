package com.hesham.baking;

import java.util.Arrays;
import java.util.ArrayList;
import android.os.Parcelable;
import android.os.Parcel;
import com.google.gson.annotations.SerializedName;

public class Baking implements Parcelable{

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;

    @SerializedName("ingredients")
    public ingredients[] myIngredients;
    @SerializedName("steps")
    public steps[] mySteps;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ingredients[] getMyIngredients() {
        return myIngredients;
    }

    public ArrayList<ingredients> getMyIngredientsArr() {
        return new ArrayList<>(Arrays.asList(myIngredients)) ;
    }

    public void setMyIngredients(ingredients[] myIngredients) {
        this.myIngredients = myIngredients;
    }

    public steps[] getMySteps() {
        return mySteps;
    }
    public ArrayList<steps> getMyStepsArr() {
       return new ArrayList<>(Arrays.asList(mySteps)) ;
    }

    public void setMySteps(steps[] mySteps) {
        this.mySteps = mySteps;
    }

    public static class ingredients implements Parcelable{
        @SerializedName("quantity")
        float quantity;
        @SerializedName("measure")
        String measure;
        @SerializedName("ingredient")
        String ingredient;

        private ingredients(Parcel in){
            quantity = in.readFloat();
            measure = in.readString();
            ingredient = in.readString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeFloat(quantity);
            parcel.writeString(measure);
            parcel.writeString(ingredient);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final static Parcelable.Creator<ingredients> CREATOR = new Parcelable.Creator<ingredients>() {
            @Override
            public ingredients createFromParcel(Parcel parcel) {
                return new ingredients(parcel);
            }

            @Override
            public ingredients[] newArray(int i) {
                return new ingredients[i];
            }

        };
    }

    public static class steps implements Parcelable {
        @SerializedName("id")
         int id;
        @SerializedName("shortDescription")
         String shortDescription;
        @SerializedName("description")
         String description;
        @SerializedName("videoURL")
         String videoURL;
        @SerializedName("thumbnailURL")
         String thumbnailURL;

        private steps(Parcel in){
            id = in.readInt();
            shortDescription = in.readString();
            description = in.readString();
            videoURL = in.readString();
            thumbnailURL = in.readString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(shortDescription);
            parcel.writeString(description);
            parcel.writeString(videoURL);
            parcel.writeString(thumbnailURL);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final static Parcelable.Creator<steps> CREATOR = new Parcelable.Creator<steps>() {
            @Override
            public steps createFromParcel(Parcel parcel) {
                return new steps(parcel);
            }

            @Override
            public steps[] newArray(int i) {
                return new steps[i];
            }
        };
    }

    private Baking(Parcel in){

        id = in.readInt();
        name = in.readString();
        myIngredients = (ingredients[]) in.readParcelableArray(ingredients.class.getClassLoader());
        mySteps = (steps[]) in.readParcelableArray(steps.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeParcelableArray(myIngredients, flag);
        parcel.writeParcelableArray(mySteps, flag);
    }

    public final static Parcelable.Creator<Baking> CREATOR = new Parcelable.Creator<Baking>() {
        @Override
        public Baking createFromParcel(Parcel parcel) {
            return new Baking(parcel);
        }

        @Override
        public Baking[] newArray(int i) {
            return new Baking[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}

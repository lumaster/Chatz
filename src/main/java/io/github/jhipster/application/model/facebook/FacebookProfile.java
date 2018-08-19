package io.github.jhipster.application.model.facebook;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FacebookProfile {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("profile_pic")
    private String profilePic;

    private String name_chaoe;

}

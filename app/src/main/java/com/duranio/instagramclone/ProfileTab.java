package com.duranio.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText profileNameEditText, biographyEditText, professionEditText, hobbiesEditText, favoriteSportEditText;
    private Button updateInfoButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        profileNameEditText = view.findViewById(R.id.profileNameEditText);
        biographyEditText = view.findViewById(R.id.biographyEditText);
        professionEditText = view.findViewById(R.id.professionEditText);
        hobbiesEditText = view.findViewById(R.id.hobbiesEditText);
        favoriteSportEditText = view.findViewById(R.id.favoriteSportEditText);
        updateInfoButton = view.findViewById(R.id.updateInfoButton);


        ParseUser user = ParseUser.getCurrentUser();

        if(user.get("profileName")!=null) {
            profileNameEditText.setText(user.get("profileName").toString());
        }
        if(user.get("biography")!=null) {
            biographyEditText.setText(user.get("biography").toString());
        }
        if(user.get("profession")!=null) {
            professionEditText.setText(user.get("profession").toString());

        }
        if(user.get("hobbiew")!=null) {
            hobbiesEditText.setText(user.get("hobbies").toString());
        }
        if(user.get("favoriteSport")!=null) {
            favoriteSportEditText.setText(user.get("favoriteSport").toString());
        }
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.put("profileName", profileNameEditText.getText().toString().trim());
                user.put("profileBiography", biographyEditText.getText().toString().trim());
                user.put("profileProfession", professionEditText.getText().toString().trim());
                user.put("profileHobbies", hobbiesEditText.getText().toString().trim());
                user.put("profileFavoriteSport", favoriteSportEditText.getText().toString().trim());

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("updating info...");
                progressDialog.show();

                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(),
                                    "Info Updated",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.INFO,
                                    true).show();
                        } else {
                            FancyToast.makeText(getContext(),
                                    "Error",
                                    FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }
}
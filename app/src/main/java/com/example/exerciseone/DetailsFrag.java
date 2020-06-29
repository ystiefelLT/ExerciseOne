package com.example.exerciseone;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

public class DetailsFrag extends Fragment {

    private ContactsViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        if(viewModel.selectedContact == null){
            showImage(view, null);
            view.findViewById(R.id.hide_btn).setVisibility(View.GONE);
            return view;
        }
        showImage(view, viewModel.selectedContact.image);
        showText(viewModel.selectedContact.name, (TextView) view.findViewById(R.id.name_text));
        showText(viewModel.selectedContact.email, (TextView) view.findViewById(R.id.email_text));
        showText(viewModel.selectedContact.phoneNumber, (TextView) view.findViewById(R.id.phone_num_text));
        initBtn(view, viewModel.selectedContact.isHidden);
        return view;
    }

    private void initBtn(View view, final boolean isHidden){
        Button btn = view.findViewById(R.id.hide_btn);
        if(isHidden){
            btn.setText("Show contact");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isHidden){
                    //move contact to show list
                    viewModel.contactList.getValue().add(viewModel.selectedContact);
                    viewModel.hiddenList.getValue().remove(viewModel.selectedContact);
                }
                else {
                    //move contact to hidden list
                    viewModel.hiddenList.getValue().add(viewModel.selectedContact);
                    viewModel.contactList.getValue().remove(viewModel.selectedContact);
                }
                viewModel.selectedContact.isHidden = !isHidden; // flips the hidden boolean
                // switch fragments
                if (Navigation.findNavController(view).getCurrentDestination().getId() == R.id.detailsFrag) {
                    Navigation.findNavController(view).navigate(R.id.action_detailsFrag_to_contactLisFrag);
                }
            }
        });
    }



    private void showImage(View view, String image){
        if(image == null){
            Glide.with(this)
                    .load(getActivity().getDrawable(R.drawable.empty_portrait))
                    .into((ImageView) view.findViewById(R.id.contact_image));
        }
        else{
            Glide.with(this)
                    .load(Uri.parse(image))
                    .into((ImageView) view.findViewById(R.id.contact_image));
        }
    }

    private void showText(String text, TextView textView){
        // if the field either does not exist or is an empty string
        if(text == null || text.equals("")){
            textView.setVisibility(View.GONE);
        }
        else{
            textView.setText(text);
        }
    }

}

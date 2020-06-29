package com.example.exerciseone.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseone.R;
import com.example.exerciseone.adapters.ContactAdapter;
import com.example.exerciseone.models.ContactsViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HiddenContactsFrag extends Fragment implements ContactAdapter.ItemClickListener{

    private ContactsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        // init ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ContactsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container!= null){
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ContactAdapter adapter = new ContactAdapter(getContext(), viewModel.hiddenList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onItemClick(View view, int position) {
        NavController navController = Navigation.findNavController(view);
        if (navController.getCurrentDestination() != null
                && navController.getCurrentDestination().getId() == R.id.hiddenContacts) {
            viewModel.selectedContact = viewModel.hiddenList.getValue().get(position);
            navController.navigate(R.id.action_hiddenFrag_to_detailsFrag);
        }
    }
}

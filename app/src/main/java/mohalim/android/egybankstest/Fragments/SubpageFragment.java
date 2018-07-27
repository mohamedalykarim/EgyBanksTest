package mohalim.android.egybankstest.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mohalim.android.egybankstest.Adapters.MainMenuRecyclerAdapter;
import mohalim.android.egybankstest.Models.MainMenuItem;
import mohalim.android.egybankstest.QuizActivity;
import mohalim.android.egybankstest.R;

public class SubpageFragment extends Fragment implements MainMenuRecyclerAdapter.MainMenuClickListener {

    private static final String MENU = "menu";
    private static final String MENU_NAME = "menu_name";

    private static final String ALAHLY_MENU = "alahly menu";
    private static final String BANQUEMISR_MENU = "banquemisr_menu";
    private static final String SELECTED_QUIZ = "selected_quiz";
    private static final String IQ_ALAHLY = "iq_alahly";
    private static final String ENGLISH_ALAHLY = "english_alahly";
    private static final String TECHNICAL_ALAHLY = "technical_alahly";


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<MainMenuItem> menuItems;
    String menuName;
    MainMenuRecyclerAdapter mainMenuRecyclerAdapter;

    public static SubpageFragment newInstance(ArrayList<MainMenuItem> menuItems, String menuName) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(MENU, menuItems);
        args.putString(MENU_NAME,menuName);

        SubpageFragment fragment = new SubpageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            menuItems = getArguments().getParcelableArrayList(MENU);
            menuName = getArguments().getString(MENU_NAME);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.subpage_fragment,container,false);
        recyclerView = view.findViewById(R.id.subpage_recycler);
        linearLayoutManager = new LinearLayoutManager(container.getContext());
        mainMenuRecyclerAdapter = new MainMenuRecyclerAdapter(container.getContext(),menuItems,this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainMenuRecyclerAdapter);
        mainMenuRecyclerAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onMenuItemClickListener(int position) {
        if (menuName.equals(ALAHLY_MENU)){
            if (position == 0){
                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra(SELECTED_QUIZ, IQ_ALAHLY);
                startActivity(intent);
                
            }else if (position == 1){
                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra(SELECTED_QUIZ, ENGLISH_ALAHLY);
                startActivity(intent);
                
            }else if (position == 2){
                Intent intent = new Intent(getContext(), QuizActivity.class);
                intent.putExtra(SELECTED_QUIZ, TECHNICAL_ALAHLY);
                startActivity(intent);
            }

        }else if (menuName.equals(BANQUEMISR_MENU)){

        }

    }


}

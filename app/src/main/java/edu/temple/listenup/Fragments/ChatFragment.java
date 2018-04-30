package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


import edu.temple.listenup.ChatActivity;
import edu.temple.listenup.Adapters.ChatMessagesAdapter;

import edu.temple.listenup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ChatMessagesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList myDataset;
    private String username;


    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(ArrayList messages, String username) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putSerializable("messages", messages);
        args.putString("username", username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setMyDataset((ArrayList)(getArguments().getSerializable("messages")));
        this.username = getArguments().getString("username");


        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.messagesView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new ChatMessagesAdapter(myDataset, username);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText messageContentView = (EditText) view.findViewById(R.id.messageSendingView);

        view.findViewById(R.id.messageSendingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = messageContentView.getText().toString();
                if(!messageContent.equals("")){
                    MessageCreator actMessageCreator = (MessageCreator)getActivity();
                    ChatActivity.Message message = actMessageCreator.generateMessage(messageContent);
                    actMessageCreator.addMessageToFirebase(message);
                }else{
                    Toast.makeText(getActivity(), "Please write a message before sending", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void setMyDataset(ArrayList myDataset) {
        this.myDataset = myDataset;
    }

    public void notifyAdapterDataChanged(){
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }


    public interface MessageCreator{
        ChatActivity.Message generateMessage(String message);
        void addMessageToFirebase(ChatActivity.Message message);
    }
}

package com.helloWorldTech.funQuest.ui.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.helloWorldTech.funQuest.Config;
import com.helloWorldTech.funQuest.R;
import com.helloWorldTech.funQuest.data.local.entity.UserEntity;
import com.helloWorldTech.funQuest.databinding.ActivityChatBinding;
import com.helloWorldTech.funQuest.ui.base.BaseActivity;
import com.helloWorldTech.funQuest.util.Utils;
import com.helloWorldTech.funQuest.viewModel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends BaseActivity {
    public static final String TAG = "ChatActivity";

    private ActivityChatBinding binding;
    private FirebaseFirestore db;
    private UserViewModel userViewModel;
    private String documentId = "";
    private List<Message> messages = new ArrayList<>();
    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        showLoading(true);

        initialViewModel();
        initialUiAndActions();
        initialAdapter();
        initialData();
    }

    private void initialViewModel() {
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
    }

    private void initialUiAndActions() {
        db = FirebaseFirestore.getInstance();

        binding.toolbarHeader.ivArrowBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.includeChat.btnSend.setOnClickListener(view -> sendMessage());

        binding.includeChat.etSendMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                binding.includeChat.recycleViewMessageList.smoothScrollToPosition(binding.includeChat.recycleViewMessageList.getAdapter().getItemCount());
                return false;
            }
        });

    }

    private void sendMessage() {
        String messageText = binding.includeChat.etSendMessage.getText().toString();

        if (!TextUtils.isEmpty(messageText)) {
            saveNewMessage(messageText);
        }
    }

    private void initialAdapter() {
        binding.includeChat.recycleViewMessageList.setLayoutManager(new LinearLayoutManager(this));
        messagesAdapter = new MessagesAdapter(this, messages, pref);
        binding.includeChat.recycleViewMessageList.setAdapter(messagesAdapter);
        binding.includeChat.recycleViewMessageList.smoothScrollToPosition(binding.includeChat.recycleViewMessageList.getAdapter().getItemCount());
    }

    private void initialData() {

        binding.toolbarHeader.tvName.setText(getString(R.string.label_techical_support));
        binding.toolbarHeader.profileImage.setVisibility(View.VISIBLE);

        String userImage = pref.getUserImage();
        Glide.with(this)
                .load(Config.BASE_IMAGE_URl + userImage)
                .placeholder(R.drawable.unknown_user)
                .error(R.drawable.unknown_user)
                .into(binding.toolbarHeader.profileImage);

        binding.includeChat.messageSwipeLayout.setRefreshing(false);
        binding.includeChat.messageSwipeLayout.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserExist();
    }

    private void saveNewMessage(String messageText) {
        Map<String, Object> message = new HashMap<>();
        message.put("content", messageText);
        message.put("created", FieldValue.serverTimestamp());  //ServerValue.TIMESTAM
        message.put("from", "user");
        message.put("to", "staff");
        message.put("from_id", pref.getUserId());
        message.put("to_id", 0);
        message.put("media_type", "text");
        message.put("image", "");
        message.put("user_new", true);
        message.put("room_id", documentId);
        message.put("url", "");

//        Message message = new Message(messageText, "" + FieldValue.serverTimestamp(), "user", "staff", pref.getUserId(), 0,
//                "text", documentId, "", true);

        db.collection("messages")
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.includeChat.etSendMessage.setText("");
                        updateSeenMessagesAndOnlineStatus();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void checkUserExist() {
        int userId = pref.getUserId();
        if (userId != 0) {
            db.collection("rooms")
                    .whereEqualTo("user_id", userId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    if (document.exists()) {
                                        documentId = document.getId();
                                        getUser(false);
                                    }
                                }
                            } else {
                                getUser(true);
                            }
                            showLoading(false);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error getting documents." + e.getMessage());
                            showLoading(false);
                        }
                    });
        } else {
            navigationController.navigateToLoginActivity(ChatActivity.this);
            showLoading(false);
        }

    }

    private void getUser(boolean isAdd) {
        userViewModel.getLoginUser().observe(this, data -> {
            if (data != null) {
                UserEntity.User user = data.getData().getUser();

                if (isAdd == true) {
                    addNewUser(user);
                } else {
                    updateUser(user);
                }

            }
        });

    }

    private void addNewUser(UserEntity.User mainUser) {
        DocumentReference addedDocRef = db.collection("rooms").document();
        documentId = addedDocRef.getId();

        Map<String, Object> user = new HashMap<>();
        user.put("name", mainUser.getName());
        user.put("updated", FieldValue.serverTimestamp());
        user.put("new", 0);
        user.put("new_app", 0);
        user.put("user_id", mainUser.getId());
        user.put("room_id", documentId);
        user.put("from", "user");
        user.put("from_id", mainUser.getId());
        user.put("to", "staff");
        user.put("to_id", 0);

        addedDocRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //updateSeenMessagesAndOnlineStatus();
                    getMessages();
                }else {
                    Log.w(TAG, "Error Add User" + task.getException().getMessage());
                }
            }
        });

    }

    private void updateUser(UserEntity.User mainUser) {
        DocumentReference addedDocRef = db.collection("rooms").document(documentId);
        Map<String, Object> user = new HashMap<>();
        user.put("name", mainUser.getName());
        user.put("updated", FieldValue.serverTimestamp());
        user.put("user_id", mainUser.getId());
        user.put("room_id", documentId);
        user.put("from", "user");
        user.put("from_id", mainUser.getId());
        user.put("to", "staff");
        user.put("to_id", 0);

        addedDocRef.set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //updateSeenMessagesAndOnlineStatus();
                    getMessages();
                }else {
                    Log.w(TAG, "Error Update User" + task.getException().getMessage());
                }
            }
        });

    }

    private void updateSeenMessagesAndOnlineStatus() {
        Map<String, Object> map = new HashMap<>();
        map.put("new", 1);
        map.put("updated", FieldValue.serverTimestamp());

        db.collection("rooms").document(documentId)
                .set(map, SetOptions.merge());

    }

    private void getMessages() {
        Utils.log(TAG + " getMessages");
        db.collection("messages")
                .whereEqualTo("room_id", documentId)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException error) {
                        Utils.log(TAG + " onEvent");
                        if (error != null) {
                            Utils.log(TAG + " Listen failed:" + error);
                            return;
                        }

                        db.collection("messages")
                                .whereEqualTo("room_id", documentId)
                                .orderBy("created")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (!messages.isEmpty())
                                                messages.clear();

                                            List<Message> messageList = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                if (document.exists()) {
                                                    Message message = document.toObject(Message.class);
                                                    messageList.add(message);
                                                }
                                            }

                                            Utils.log(TAG + " after for" + messageList.size());
                                            messages.addAll(messageList);
                                            messagesAdapter.notifyDataSetChanged();
                                            binding.includeChat.recycleViewMessageList.smoothScrollToPosition(binding.includeChat.recycleViewMessageList.getAdapter().getItemCount());

                                        } else {
                                            Log.w(TAG, "Error getting documents.", task.getException());

                                        }
                                    }
                                });
                    }

                });
    }

    @Override
    protected void onStop() {
        super.onStop();

//        Map<String, Object> map = new HashMap<>();
//        map.put("updated", FieldValue.serverTimestamp());
//
//        db.collection("rooms").document(documentId)
//                .set(map, SetOptions.merge());

    }

    public void showLoading(Boolean state) {
        if (state) {
            Utils.showCustomLoadingDialog(this);
        } else {
            Utils.hideCustomLoadingDialog();
        }
    }

}
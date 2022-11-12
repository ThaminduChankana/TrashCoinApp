package com.example.trashcoinapp.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_FULL_NAME = "fullname";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_TYPE = "type";
    public static final String KEY_PREFERENCE_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversations";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_ROLE = "senderRole";
    public static final String KEY_RECEIVER_ROLE = "receiverRole";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    public static final String KEY_COLLECTION_COLLECTOR_DETAILS = "collectorDetails";
    public static final String KEY_COLLECTOR_NAME = "collectorName";
    public static final String KEY_COLLECTOR_PHONE = "collectorPhone";
    public static final String KEY_COLLECTOR_COMPANY = "collectorCompany";
    public static final String KEY_COLLECTOR_START_TIME = "collectorStartTime";
    public static final String KEY_COLLECTOR_END_TIME = "collectorEndTime";
    public static final String KEY_COLLECTOR_AREA = "collectorArea";
    public static final String KEY_COLLECTOR_AVAILABILITY = "collectorAvailability";
    public static final String KEY_COLLECTOR_DETAILS_ADDED = "isAdded";

    public static HashMap<String, String> remoteMsgHeaders = null;

    public static HashMap<String, String> getRemoteMsgHeaders(){
        if(remoteMsgHeaders == null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAAGFBcv0U:APA91bEWC8T-iUvDs3c4-iJHPsoLt4M-bBW55B9EsJinlRlHpzJKwbOcr0V4AEIpMis0ikl0PpmnISjJTybYz4VNhEcNVz-z3CBkom4lalwy1UIcuBDZjSLITRkymw2y5-ENVKaVWcF9"
            );
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );

        }
        return remoteMsgHeaders;
    }

}
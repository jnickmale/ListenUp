package edu.temple.listenup.Adapters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import edu.temple.listenup.ChatActivity;
import edu.temple.listenup.R;

public class ChatMessagesAdapter extends RecyclerView.Adapter {
    private ArrayList messages;
    private String username;

    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 0;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;


    public static class ReceivedViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextContent, messageTextTime;
        public ReceivedViewHolder(View v) {
            super(v);
            messageTextContent = v.findViewById(R.id.messageReceivedContent);
            messageTextTime = v.findViewById(R.id.messageReceivedTime);
        }

        void bind(ChatActivity.Message message) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(message.getDateSent());
            calendar.setTimeZone(TimeZone.getTimeZone("EST"));
            messageTextContent.setText(message.getContent());
            messageTextTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        }
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextContent, messageTextTime;
        public SentViewHolder(View v) {
            super(v);
            messageTextContent = v.findViewById(R.id.messageSentContent);
            messageTextTime = v.findViewById(R.id.messageSentTime);
        }

        void bind(ChatActivity.Message message) {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(message.getDateSent());
            calendar.setTimeZone(TimeZone.getTimeZone("EST"));

            messageTextContent.setText(message.getContent());
            messageTextTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        }
    }



    public ChatMessagesAdapter(ArrayList messages, String username){
        this.messages = messages;
        this.username = username;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received, parent, false);
            return new ReceivedViewHolder(v);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent, parent, false);
            return new SentViewHolder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatActivity.Message message = (ChatActivity.Message) messages.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedViewHolder) holder).bind(message);
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentViewHolder) holder).bind(message);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ChatActivity.Message message = (ChatActivity.Message) messages.get(position);

        if (message.getFromUsername().equals(username)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }
}

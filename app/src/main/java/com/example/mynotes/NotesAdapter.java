package com.example.mynotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private static final String TAG = "NotesAdapter";
    private NotesSource notesSource;
    private OnItemClickListener listener;
    private Note currentNote;

    //передаем в конструктор источник данных
    public NotesAdapter(NotesSource notesSource) {
        this.notesSource = notesSource;
    }

    //создаем новый элемент пользоватеьского интерфейса, запускается менеджером
    //вызывается, когда надо создавать вью холдер
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_note_item, parent, false);
        return new NotesViewHolder(itemView);
    }

    //вызывается, когда надо забиндить вьюхолдер
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(notesSource.getNote(position));
    }

    @Override
    public int getItemCount() {
        return notesSource.size();
    }

    //класс хранит связь между данными и элементами View
    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final ImageView image;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);
            image = itemView.findViewById(R.id.item_image);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(getAdapterPosition());

                    }
                }
            });
        }

        public void bind(Note note) {
            title.setText(note.getNoteName());
            try{
                description.setText(note.getIndexDescription());

            }catch (NullPointerException e){
                e.printStackTrace();
            }
            image.setImageResource(note.getPictureNumber());
        }
    }

    interface  OnItemClickListener {
        void onItemClick(int position);
    }
}

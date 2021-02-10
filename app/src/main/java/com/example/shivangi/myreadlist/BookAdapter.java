package com.example.shivangi.myreadlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter <BookAdapter.BookHolder> {

    private List<Book> books = new ArrayList<>();
    private onItemClickListener listener;

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                parent,false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book current = books.get(position);

        holder.bookName.setText(current.getTitle());
        holder.author.setText(current.getAuthor());
        holder.category.setText(current.getCat());
        holder.status.setText(current.getStatus());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> book){
        this.books = book;
        notifyDataSetChanged();
    }

    public  Book getBookAt(int pos){
        return books.get(pos);
    }

    class BookHolder extends RecyclerView.ViewHolder{

        private TextView bookName;
        private TextView author;
        private TextView category;
        private TextView status;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            author = itemView.findViewById(R.id.authorName);
            category = itemView.findViewById(R.id.bkCategory);
            status = itemView.findViewById(R.id.bkStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION){
                        listener.onItemClick(books.get(pos));
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(Book book);
    }

    public void setOnClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}

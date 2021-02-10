package com.example.shivangi.myreadlist;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class BookViewModel extends AndroidViewModel {

    private BookRepository bookRepository;
    private LiveData<List<Book>> bookList;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookRepository = new BookRepository(application);
        bookList = bookRepository.getBookList();
    }

    public void insert(Book book){
        bookRepository.insert(book);
    }

    public void update(Book book){
        bookRepository.update(book);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public void deleteAll(){
        bookRepository.deleteAll();
    }

    public LiveData<List<Book>> getBookList() {
        return bookList;
    }
}

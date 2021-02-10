package com.example.shivangi.myreadlist;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class BookRepository {

    private BookDao bookDao;
    private LiveData<List<Book>> bookList;

    BookRepository(Application application){
        BookDatabase db = BookDatabase.getInstance(application);
        bookDao = db.bookDao();
        bookList = bookDao.getBookList();
    }

    public void insert(Book book){
        new insertAsyncTask(bookDao).execute(book);
    }

    public void update(Book book){
        new updateAsyncTask(bookDao).execute(book);
    }

    public void delete(Book book){
        new deleteAsyncTask(bookDao).execute(book);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(bookDao).execute();
    }

    public LiveData<List<Book>> getBookList(){
        return bookList;
    }

    private static class insertAsyncTask extends AsyncTask<Book, Void, Void>{

        private BookDao asyncDao;

        insertAsyncTask(BookDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            asyncDao.insert(books[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Book, Void, Void>{

        private BookDao asyncDao;

        updateAsyncTask(BookDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            asyncDao.update(books[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Book, Void, Void>{

        private BookDao asyncDao;

        deleteAsyncTask(BookDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            asyncDao.delete(books[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private BookDao asyncDao;

        deleteAllAsyncTask(BookDao dao){
            asyncDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncDao.deleteAll();
            return null;
        }
    }
}

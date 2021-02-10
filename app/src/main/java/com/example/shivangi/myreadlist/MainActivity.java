package com.example.shivangi.myreadlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BookViewModel viewModel;
    public static final int REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        BookAdapter adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        viewModel.getBookList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getBookAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,"Book Deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnClickListener(new BookAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                intent.putExtra(Add.EXTRA_ID,book.getId());
                intent.putExtra(Add.EXTRA_BOOK,book.getTitle());
                intent.putExtra(Add.EXTRA_AUTHOR,book.getAuthor());
                intent.putExtra(Add.EXTRA_CATEGORY,book.getCat());
                intent.putExtra(Add.EXTRA_STATUS,book.getStatus());
                startActivityForResult(intent,EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.deleteAll:
                viewModel.deleteAll();
                Toast.makeText(this,"All book data deleted",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String book = data.getStringExtra(Add.EXTRA_BOOK);
            String author = data.getStringExtra(Add.EXTRA_AUTHOR);
            String cat = data.getStringExtra(Add.EXTRA_CATEGORY);
            String status = data.getStringExtra(Add.EXTRA_STATUS);

            Book books = new Book(book,author,cat,status);
            viewModel.insert(books);

            Toast.makeText(this,"Book Saved",Toast.LENGTH_LONG).show();
        } else if(requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK){
            int id = data.getIntExtra(Add.EXTRA_ID,-1);

            if(id == -1){
                Toast.makeText(this,"Book not updated",Toast.LENGTH_LONG).show();
                return;
            }

            String book = data.getStringExtra(Add.EXTRA_BOOK);
            String author = data.getStringExtra(Add.EXTRA_AUTHOR);
            String cat = data.getStringExtra(Add.EXTRA_CATEGORY);
            String status = data.getStringExtra(Add.EXTRA_STATUS);

            Book books = new Book(book,author,cat,status);
            books.setId(id);
            viewModel.update(books);

            Toast.makeText(this,"Book updated",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Book not Saved",Toast.LENGTH_LONG).show();
        }
    }
}
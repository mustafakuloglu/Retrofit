package com.example.comert.retrofitproje;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView lvComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        lvComment= (ListView)findViewById(R.id.lvComment);
        retrofit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.comment) {
            // Handle the camera action
            Intent user = new Intent(CommentActivity.this,MainActivity.class);
            startActivity(user);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void retrofit()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CommentAPI service = retrofit.create(CommentAPI.class);
        service.getCommit().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                //  Toast.makeText(MainActivity.this, response.body().get(0).getWebsite().toString()+response.body().size(), Toast.LENGTH_SHORT).show();
                AdaptorPerson adapter=new AdaptorPerson(CommentActivity.this,response.body());
                lvComment.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

    }
    private class AdaptorPerson extends ArrayAdapter<Comment>
    {
        private List<Comment> listComment;
        public AdaptorPerson(Context context, List<Comment> comm)
        {
            super(context, R.layout.listitem_comment, comm);
            listComment =comm;
        }
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater=LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_comment,null);

            int i= listComment.get(position).getId();
            String s=Integer.toString(i);
            TextView textId = (TextView)item.findViewById(R.id.txtId);
            textId.setText(s);

            i= listComment.get(position).getPostId();
            s=Integer.toString(i);
            TextView txtPostId = (TextView)item.findViewById(R.id.postId);
            txtPostId.setText(s);

            TextView textName=(TextView)item.findViewById(R.id.txtName);
            textName.setText(listComment.get(position).getName());

            TextView textemail=(TextView)item.findViewById(R.id.email);
            textemail.setText(listComment.get(position).getEmail());

            TextView textBody=(TextView)item.findViewById(R.id.body);
            textBody.setText(listComment.get(position).getEmail());





            return item;
        }
    }
}


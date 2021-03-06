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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView lvPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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



        lvPerson= (ListView)findViewById(R.id.lvPerson);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.users) {
            // Handle the camera action
            Intent comment = new Intent(MainActivity.this,CommentActivity.class);
            startActivity(comment);
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

        PersonAPI service = retrofit.create(PersonAPI.class);
        service.getPerson().enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                //  Toast.makeText(MainActivity.this, response.body().get(0).getWebsite().toString()+response.body().size(), Toast.LENGTH_SHORT).show();
                AdaptorPerson adapter=new AdaptorPerson(MainActivity.this,response.body());
                lvPerson.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {

            }
        });

    }
    private class AdaptorPerson extends ArrayAdapter<Person>
    {
        private List<Person> listPerson;
        public AdaptorPerson(Context context, List<Person> pers)
        {
            super(context, R.layout.listitem_person, pers);
            listPerson=pers;
        }
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater=LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.listitem_person,null);

            int i=listPerson.get(position).getId();
            String s=Integer.toString(i);
            TextView textId = (TextView)item.findViewById(R.id.txtId);
            textId.setText(s);

            TextView textName=(TextView)item.findViewById(R.id.txtName);
            textName.setText(listPerson.get(position).getName());

            TextView textUsername=(TextView)item.findViewById(R.id.txtUsername);
            textUsername.setText(listPerson.get(position).getUsername());

            TextView textemail=(TextView)item.findViewById(R.id.email);
            textemail.setText(listPerson.get(position).getEmail());

            TextView street=(TextView)item.findViewById(R.id.street);
            street.setText(listPerson.get(position).getAddress().getStreet());

            TextView suite=(TextView)item.findViewById(R.id.suite);
            suite.setText(listPerson.get(position).getAddress().getSuite());

            TextView city=(TextView)item.findViewById(R.id.city);
            city.setText(listPerson.get(position).getAddress().getCity());

            TextView zipkode=(TextView)item.findViewById(R.id.zipkode);
            zipkode.setText(listPerson.get(position).getAddress().getZipcode());

            TextView geo1=(TextView)item.findViewById(R.id.geo1);
            geo1.setText(listPerson.get(position).getAddress().getGeo().getLat());

            TextView geo2=(TextView)item.findViewById(R.id.geo2);
            geo2.setText(listPerson.get(position).getAddress().getGeo().getLng());

            TextView phone=(TextView)item.findViewById(R.id.phone);
            phone.setText(listPerson.get(position).getPhone());

            TextView website=(TextView)item.findViewById(R.id.website);
            website.setText(listPerson.get(position).getWebsite());

            TextView comname=(TextView)item.findViewById(R.id.comname);
            comname.setText(listPerson.get(position).getCompany().getName());

            TextView catc=(TextView)item.findViewById(R.id.cathc);
            catc.setText(listPerson.get(position).getCompany().getCatchPhrase());

            TextView bs=(TextView)item.findViewById(R.id.bs);
            bs.setText(listPerson.get(position).getCompany().getBs());





            return item;
        }
    }
}

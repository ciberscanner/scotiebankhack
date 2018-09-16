package com.kiwabolab.scotiabankloyalty.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kiwabolab.scotiabankloyalty.R;
import com.kiwabolab.scotiabankloyalty.model.Product;
import com.kiwabolab.scotiabankloyalty.network.RestApiAdapter;
import com.kiwabolab.scotiabankloyalty.view.adapter.DividerItemDecoration;
import com.kiwabolab.scotiabankloyalty.view.adapter.ProducAdapter;
import com.kiwabolab.scotiabankloyalty.view.adapter.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kiwabolab.scotiabankloyalty.model.Servidor.API;

public class Catalogo extends Activity {
    //----------------------------------------------------------------------------------------------
    //Variables
    private List<Product> products;
    private RecyclerView recyclerView;
    private ProducAdapter mAdapter;
    private Context context;
    private TextView title;
    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);
        products= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.mi_recicler);
        context= this;
        mAdapter = new ProducAdapter(products, context);

        title= (TextView) findViewById(R.id.title);

        Intent intent = getIntent();

        String idTienda = intent.getStringExtra("idTienda");
        String name = intent.getStringExtra("name");
        title.setText(name);

        getProductos(this,idTienda);

    }
    //----------------------------------------------------------------------------------------------
    //
    public void getProductos(Context context, String id) {
        Call<List<Product>> call = new RestApiAdapter().EstablecerConexion(API, context).getProducts(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    cargarLista(response.body());
                } else {
                    errorgetTiendas();
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                errorgetTiendas();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //
    private void cargarLista(List<Product> stores){
        //this.products = stores;

        for(Product p: stores){
            products.add(p);
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product movie = products.get(position);
                Intent intent = new Intent(context, VistaProducto.class);
                intent.putExtra("product",movie);
                context.startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
    //----------------------------------------------------------------------------------------------
    //
    private void errorgetTiendas(){
        Log.v("ERROR","Consulta Productos");
    }

}
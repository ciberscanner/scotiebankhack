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

import com.kiwabolab.scotiabankloyalty.R;
import com.kiwabolab.scotiabankloyalty.model.Product;
import com.kiwabolab.scotiabankloyalty.model.Store;
import com.kiwabolab.scotiabankloyalty.network.RestApiAdapter;
import com.kiwabolab.scotiabankloyalty.view.adapter.DividerItemDecoration;
import com.kiwabolab.scotiabankloyalty.view.adapter.ProducAdapter;
import com.kiwabolab.scotiabankloyalty.view.adapter.RecyclerTouchListener;
import com.kiwabolab.scotiabankloyalty.view.adapter.StoreAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kiwabolab.scotiabankloyalty.model.Servidor.API;

public class Tiendas extends Activity {
    //----------------------------------------------------------------------------------------------
    //Variables
    private List<Store> products;
    private RecyclerView recyclerView;
    private StoreAdapter mAdapter;
    private Context context;
    //----------------------------------------------------------------------------------------------
    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);
        products= new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.mi_recicler);
        context= this;
        mAdapter = new StoreAdapter(products, context);
        getTiendas(this);

    }
    //----------------------------------------------------------------------------------------------
    //
    public void getTiendas(Context context) {
        Call<List<Store>> call = new RestApiAdapter().EstablecerConexion(API, context).getStores();
        call.enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                if (response.isSuccessful()) {
                    cargarLista(response.body());
                } else {
                    errorgetTiendas();
                }
            }
            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                errorgetTiendas();
            }
        });
    }
    //----------------------------------------------------------------------------------------------
    //
    private void cargarLista(List<Store> stores){
        //this.products = stores;

        for(Store p: stores){
            products.add(p);
        }

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Store movie = products.get(position);
                Intent intent = new Intent(context, Catalogo.class);
                intent.putExtra("idTienda",movie.getId());
                intent.putExtra("name",movie.getName());
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
        Log.v("ERROR","Consulta Tiendas");
    }

}
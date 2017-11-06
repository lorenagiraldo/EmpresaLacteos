package com.example.lorena.empresalacteos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThirdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView pedidosL;
    private Spinner pedidoE;
    private Button botonEliminarPedido;
    private TextView textSucesoEliminarPedido;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_third, container, false);
        textSucesoEliminarPedido=(TextView) view.findViewById(R.id.textSucesoEliminarPedido);
        pedidosL=(ListView)view.findViewById(R.id.pedidosL);
        pedidoE=(Spinner)view.findViewById(R.id.pedidoE);
        PedidoOperations operaciones=new PedidoOperations(this.getContext());
        operaciones.open();
        List<Pedido> pedidos=operaciones.getAllPedidos();
        operaciones.close();
        ArrayAdapter<Pedido> adapter1=new ArrayAdapter<Pedido>(this.getContext(),android.R.layout.simple_list_item_1, pedidos);
        pedidosL.setAdapter(adapter1);
        List<String>codigosPedidos=new ArrayList<>();
        for (int i=0; i<pedidos.size(); i++)
        {
            codigosPedidos.add(pedidos.get(i).getCodigo());
        }
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_dropdown_item, codigosPedidos);
        pedidoE.setAdapter(adapter2);
        botonEliminarPedido=(Button)view.findViewById(R.id.botonEliminarPedido);
        botonEliminarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo=pedidoE.getSelectedItem().toString();
                Pedido pedido=new Pedido(-1,codigo, "", -1);
                eliminarPedido(pedido);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void eliminarPedido(Pedido pedido)
    {
        try{
            //PARA GUARDAR EN SQLITE
            PedidoOperations operaciones=new PedidoOperations(this.getContext());
            operaciones.open();
            operaciones.removePedido(pedido);
            operaciones.close();
            textSucesoEliminarPedido.setText("Pedido eliminado");
            //PARA GUARDAR EN SERVIDOR
            String url = "http://"+MainActivity.ip+"eliminarPedido/" + pedido.getCodigo();
            String response =new WSC().execute(url).get();
            Gson json=new Gson();
            String message=json.fromJson(response, String.class);
            operaciones=new PedidoOperations(this.getContext());
            operaciones.open();
            List<Pedido> pedidos=operaciones.getAllPedidos();
            operaciones.close();
            ArrayAdapter<Pedido> adapter1=new ArrayAdapter<Pedido>(this.getContext(),android.R.layout.simple_list_item_1, pedidos);
            pedidosL.setAdapter(adapter1);
            List<String>codigosPedidos=new ArrayList<>();
            for (int i=0; i<pedidos.size(); i++)
            {
                codigosPedidos.add(pedidos.get(i).getCodigo());
            }
            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_dropdown_item, codigosPedidos);
            pedidoE.setAdapter(adapter2);
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }

}

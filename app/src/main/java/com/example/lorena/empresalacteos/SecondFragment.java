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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Spinner pedidoM;
    private Button botonModificarPedido;
    private EditText editNombreMPedido;
    private EditText editCantidadMPedido;
    private TextView textSucesoFactura;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
        View view= inflater.inflate(R.layout.fragment_second, container, false);

        editNombreMPedido=(EditText)view.findViewById(R.id.editNombreMPedido);
        editCantidadMPedido=(EditText)view.findViewById(R.id.editCantidadMPedido);
        textSucesoFactura=(TextView) view.findViewById(R.id.textSucesoFactura);
        PedidoOperations operaciones=new PedidoOperations(this.getContext());
        operaciones.open();
        List<Pedido> pedidos=operaciones.getAllPedidos();
        List<String>codigosPedidos=new ArrayList<>();
        for (int i=0; i<pedidos.size(); i++)
        {
            codigosPedidos.add(pedidos.get(i).getCodigo());
        }
        pedidoM=(Spinner)view.findViewById(R.id.pedidoM);
        ArrayAdapter adapter=new ArrayAdapter(this.getContext(),android.R.layout.simple_spinner_dropdown_item,codigosPedidos);
        pedidoM.setAdapter(adapter);
        botonModificarPedido=(Button)view.findViewById(R.id.botonModificarPedido);
        botonModificarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo=pedidoM.getSelectedItem().toString();
                String nombre=editNombreMPedido.getText().toString();
                int cantidad=Integer.parseInt(editCantidadMPedido.getText().toString());
                Pedido pedido=new Pedido(-1,codigo,nombre,cantidad);
                modificarPedido(pedido);
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

    public void modificarPedido(Pedido pedido)
    {
        try {
            if (pedido.getNombre().equals("") || pedido.getCantidad()==0) {
                textSucesoFactura.setText("Error campos vacios");
            } else {
                //PARA GUARDAR EN SQLITE
                PedidoOperations operaciones=new PedidoOperations(this.getContext());
                operaciones.open();
                int success=operaciones.updatePedido(pedido);
                operaciones.close();
                if (success!=0)
                {
                    textSucesoFactura.setText("Pedido modificado");
                }
                else
                {
                    textSucesoFactura.setText("Error al modificar pedido");
                }
                //PARA GUARDAR EN SERVIDOR
                String url = "http://"+MainActivity.ip+"modificarPedido/" + pedido.getCodigo() + "/" + pedido.getNombre()+"/"+pedido.getCantidad();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}

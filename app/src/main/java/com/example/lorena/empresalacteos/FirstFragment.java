package com.example.lorena.empresalacteos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText editNombrePedido;
    private EditText editCodigoPedido;
    private EditText editCantidadPedido;
    private TextView textSucesoPedido;
    private Button botonRegistrarPedido;


    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View view= inflater.inflate(R.layout.fragment_first, container, false);
        editCodigoPedido=(EditText) view.findViewById(R.id.editCodigoPedido);
        editNombrePedido=(EditText) view.findViewById(R.id.editNombrePedido);
        editCantidadPedido=(EditText) view.findViewById(R.id.editCantidadPedido);
        textSucesoPedido=(TextView) view.findViewById(R.id.textSucesoPedido);
        botonRegistrarPedido=(Button)view.findViewById(R.id.botonRegistrarPedido);
        botonRegistrarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo=editCodigoPedido.getText().toString();
                String nombre=editNombrePedido.getText().toString();
                int cantidad=Integer.parseInt(editCantidadPedido.getText().toString());
                Pedido pedido=new Pedido(-1,codigo,nombre,cantidad);
                registrarPedido(pedido);
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

    public void registrarPedido(Pedido pedido)
    {
        try {
            if (pedido.getCodigo().equals("") || pedido.getCantidad()==0) {
                textSucesoPedido.setText("Error campos vacios");
            } else {
                //GUARDAR EN SQLITE
                PedidoOperations operaciones=new PedidoOperations(this.getContext());
                operaciones.open();
                pedido=operaciones.addPedido(pedido);
                operaciones.close();
                if (pedido!=null) {
                    textSucesoPedido.setText("registrado: "+pedido.getNombre());
                } else {
                    textSucesoPedido.setText("Error al registrar pedido");
                }
                //GUARDAR EN EL SERVIDOR
                String url = "http://"+MainActivity.ip+"crearPedido/" + pedido.getCodigo() + "/" + pedido.getNombre()+"/"+pedido.getCantidad();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
                if (message.equals("Fallo")) {
                    EnterpriseActivity.pedidosCola.add(pedido);
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}

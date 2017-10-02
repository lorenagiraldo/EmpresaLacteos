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

    private Button botonRegistrarFactura;
    private EditText editCodigoFactura;
    private EditText editNombreFactura;
    private EditText editCantidadFactura;
    private EditText editValoruFactura;
    private EditText editValortFactura;
    private TextView textSucesoFactura;

    public Factura facturaG=null;


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
        editCodigoFactura=(EditText) view.findViewById(R.id.editCodigoFactura);
        editNombreFactura=(EditText)view.findViewById(R.id.editNombreFactura);
        editCantidadFactura=(EditText)view.findViewById(R.id.editCantidadFactura);
        editValoruFactura=(EditText)view.findViewById(R.id.editValoruFactura);
        editValortFactura=(EditText)view.findViewById(R.id.editValortFactura);
        textSucesoFactura=(TextView) view.findViewById(R.id.textSucesoFactura);

        editCodigoFactura.setText(FirstFragment.pedidoG.getCodigo());
        //editNombreFactura.setText(FirstFragment.pedidoG.getNombre());
        editCantidadFactura.setText(FirstFragment.pedidoG.getCantidad());

        botonRegistrarFactura=(Button)view.findViewById(R.id.botonRegistrarFactura);
        botonRegistrarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valoru=Integer.parseInt(editValoruFactura.getText().toString());
                int valort=Integer.parseInt(editValortFactura.getText().toString());
                Factura factura=new Factura(valoru,valort,Main2Activity.usuarioG.getDocumento());
                registrarFactura(factura);
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

    public void registrarFactura(Factura factura)
    {
        try {
            if (factura.getValoru()==0 || factura.getValort()==0 || factura.getDocumentoUsuario().equals("")) {
                textSucesoFactura.setText("Error campos vacios");
            } else {

                String url = "http://"+MainActivity.ip+"/EmpresaLacteosServidor/rest/services/registrarFactura/" + factura.getValoru() + "/" + factura.getValort()+"/"+factura.getDocumentoUsuario();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
                if (message.equals("Success")) {
                    textSucesoFactura.setText("registrada: Factura");
                    facturaG=factura;
                } else {
                    textSucesoFactura.setText("Error al registrar Factura");
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}

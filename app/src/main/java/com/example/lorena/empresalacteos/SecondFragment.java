package com.example.lorena.empresalacteos;

import android.content.Context;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


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

    private ArrayList<Product> products;
    private ArrayList<String> listProducts;
    private ArrayAdapter<String> adapter;
    private ListView listviewProducts;
    private Button buttonProductDelete;
    private EditText editProductDelete;
    private TextView textProductDelete;

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
        listviewProducts=(ListView)view.findViewById(R.id.listviewProducts);
        productShow(view);
        textProductDelete=(TextView) view.findViewById(R.id.textProductDelete);
        editProductDelete=(EditText)view.findViewById(R.id.editProductDelete);
        buttonProductDelete=(Button)view.findViewById(R.id.buttonProductDelete);
        buttonProductDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productCode = editProductDelete.getText().toString();
                Product product=new Product(-1, productCode, null, -1, -1);
                productDelete(product);
                textProductDelete.setText("Del: "+productCode);
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

    public void productShow(View view)
    {
        try {
            String url = "http://"+Session.ip+"/EmpresaLacteosServidor/rest/services/productShow";
            String response =new WSC().execute(url).get();
            Gson json= new Gson();
            Type type=new TypeToken<ArrayList<Product>>() {}.getType();
            products=json.fromJson(response,type);
            listProducts=new ArrayList<>();
            listProducts.add("CODIGO / NOMBRE / PRECIO");
            for (int i=0; i<products.size();i++)
            {
                listProducts.add(products.get(i).getProductCode()+" / "+products.get(i).getProductName()+" / "+products.get(i).getProductPrice());
            }
            adapter =new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, listProducts);
            listviewProducts.setAdapter(adapter);
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }

    public void productDelete(Product product)
    {
        try {
            if (product.getProductCode().equals("")) {
                textProductDelete.setText("Error campos vacios");
            } else {

                String url = "http://"+Session.ip+"/EmpresaLacteosServidor/rest/services/productDelete/" + product.getProductCode();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
                if (message.equals("Success")) {
                    textProductDelete.setText("registrado: "+product.getProductName());
                } else {
                    textProductDelete.setText("Error al registrar producto");
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}

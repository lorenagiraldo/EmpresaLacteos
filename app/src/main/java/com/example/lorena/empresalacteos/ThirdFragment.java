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

    private EditText editProductCodeUpdate;
    private EditText editProductNameUpdate;
    private EditText editProductPriceUpdate;
    private EditText editProductQuantityUpdate;
    private TextView textProductSuccessUpdate;
    private Button buttonProductUpdate;

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
        editProductCodeUpdate=(EditText)view.findViewById(R.id.editProductCodeUpdate);
        editProductNameUpdate=(EditText)view.findViewById(R.id.editProductNameUpdate);
        editProductPriceUpdate=(EditText)view.findViewById(R.id.editProductPriceUpdate);
        editProductQuantityUpdate=(EditText)view.findViewById(R.id.editProductQuantityUpdate);
        textProductSuccessUpdate=(TextView) view.findViewById(R.id.textProductSuccessUpdate);
        buttonProductUpdate=(Button)view.findViewById(R.id.buttonProductUpdate);
        buttonProductUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productCode = editProductCodeUpdate.getText().toString();
                String productName = editProductNameUpdate.getText().toString();
                int productPrice=Integer.parseInt(editProductPriceUpdate.getText().toString());
                int productQuantity=Integer.parseInt(editProductQuantityUpdate.getText().toString());
                Product product=new Product(-1, productCode, productName, productPrice, productQuantity);
                productUpdate(product);
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

    public void productUpdate(Product product)
    {
        try {
            if (product.getProductCode().equals("")) {
                textProductSuccessUpdate.setText("Error campos vacios");
            } else {

                String url = "http://"+Session.ip+"/EmpresaLacteosServidor/rest/services/productUpdate/" + product.getProductCode() + "/" + product.getProductName()+"/"+product.getProductPrice()+"/"+product.getProductQuantity();
                String response =new WSC().execute(url).get();
                Gson json=new Gson();
                String message=json.fromJson(response, String.class);
                if (message.equals("Success")) {
                    textProductSuccessUpdate.setText("Actualizado: "+product.getProductCode());
                } else {
                    textProductSuccessUpdate.setText("Error al actualizar producto");
                }
            }
        }catch(Exception ex)
        {
            Log.d("Error", "Exception: "+ex.toString());
        }
    }
}

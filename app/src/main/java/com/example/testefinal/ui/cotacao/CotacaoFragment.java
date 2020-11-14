package com.example.testefinal.ui.cotacao;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.testefinal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class CotacaoFragment extends Fragment {

    private CotacaoViewModel cotacaoViewModel;
    TextView txtbtcrs, txtbtcus, txtltcrs,txtltcus,txtetcbr,txtetcus,txtrtcrs,txtrtcus,txtbchrs,txtbchus;
    ProgressBar progressBar;
    float dolar;
    float antigabtc = 0;
    float antigaltc = 0;
    float antigaetc = 0;
    float antigartc = 0;
    float antigabch = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cotacaoViewModel = ViewModelProviders.of(this).get(CotacaoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cota, container, false);
        txtbtcrs = root.findViewById(R.id.btcrs);
        txtbtcus = root.findViewById(R.id.btcus);
        txtltcrs = root.findViewById(R.id.ltcrs);
        txtltcus = root.findViewById(R.id.ltcus);
        txtetcbr = root.findViewById(R.id.etcbr);
        txtetcus = root.findViewById(R.id.etcus);
        txtrtcrs = root.findViewById(R.id.ripbr);
        txtrtcus = root.findViewById(R.id.ripus);
        txtbchrs = root.findViewById(R.id.bchbr);
        txtbchus = root.findViewById(R.id.bchus);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        new JSONTaskUSD().execute("https://api.hgbrasil.com/finance?key=46c23ef4");
        new JSONTask().execute("https://www.mercadobitcoin.net/api/BTC/ticker/");
        new JSONTaskLTC().execute("https://www.mercadobitcoin.net/api/LTC/ticker/");
        new JSONTaskETC().execute("https://www.mercadobitcoin.net/api/ETH/ticker/");
        new JSONTaskRIP().execute("https://www.mercadobitcoin.net/api/XRP/ticker/");
        new JSONTaskBCH().execute("https://www.mercadobitcoin.net/api/BCH/ticker/");
        CountDownTimer x= new CountDownTimer(10000,10000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                progressBar.setVisibility(View.INVISIBLE);
                new JSONTask().execute("https://www.mercadobitcoin.net/api/BTC/ticker/");
                new JSONTaskLTC().execute("https://www.mercadobitcoin.net/api/LTC/ticker/");
                new JSONTaskETC().execute("https://www.mercadobitcoin.net/api/ETH/ticker/");
                new JSONTaskRIP().execute("https://www.mercadobitcoin.net/api/XRP/ticker/");
                new JSONTaskBCH().execute("https://www.mercadobitcoin.net/api/BCH/ticker/");
                start();
            }
        };
        x.start();
        return root;
    }
    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentObject2 = parentObject.getJSONObject("ticker");

                String price = parentObject2.getString("last");
                // String time = parentObject.getString("time");

                return price;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            float nova = Float.parseFloat(result);
            float valor = Float.valueOf(String.format(Locale.US,"%.2f",nova));
            float resultado = Float.valueOf(String.format(Locale.US,"%.2f",valor/dolar));
            if (nova> antigabtc){
                txtbtcus.setText(resultado+" $");
                txtbtcrs.setText(valor+" R$");
                txtbtcrs.setTextColor(Color.GREEN);
                txtbtcus.setTextColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtbtcrs.setTextColor(Color.WHITE);
                        txtbtcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else if (nova < antigabtc){
                txtbtcus.setText(resultado+" $");
                txtbtcrs.setText(valor+" R$");
                txtbtcrs.setTextColor(Color.RED);
                txtbtcus.setTextColor(Color.RED);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtbtcrs.setTextColor(Color.WHITE);
                        txtbtcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else{
                txtbtcus.setText(resultado+" $");
                txtbtcrs.setText(valor+" R$");
            }
            antigabtc = Float.parseFloat(result);
        }
    }
    public class JSONTaskUSD extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentObject2 = parentObject.getJSONObject("results");
                JSONObject parentObject3 = parentObject2.getJSONObject("currencies");
                JSONObject parentObject4 = parentObject3.getJSONObject("USD");

                String price = parentObject4.getString("buy");;
                // String time = parentObject.getString("time");

                return price;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dolar = Float.parseFloat(result);
        }
    }
    public class JSONTaskLTC extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentObject2 = parentObject.getJSONObject("ticker");

                String price = parentObject2.getString("last");
                // String time = parentObject.getString("time");

                return price;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            float nova = Float.parseFloat(result);
            float valor = Float.valueOf(String.format(Locale.US,"%.2f",nova));
            float resultado = Float.valueOf(String.format(Locale.US,"%.2f",valor/dolar));
            if (nova> antigaltc){
                txtltcus.setText(resultado+" $");
                txtltcrs.setText(valor+" R$");
                txtltcrs.setTextColor(Color.GREEN);
                txtltcus.setTextColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtltcrs.setTextColor(Color.WHITE);
                        txtltcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else if (nova < antigaltc){
                txtltcus.setText(resultado+" $");
                txtltcrs.setText(valor+" R$");
                txtltcrs.setTextColor(Color.RED);
                txtltcus.setTextColor(Color.RED);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtltcrs.setTextColor(Color.WHITE);
                        txtltcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else{
                txtltcus.setText(resultado+" $");
                txtltcrs.setText(valor+" R$");
            }
            antigaltc = Float.parseFloat(result);
        }
    }
    public class JSONTaskETC extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentObject2 = parentObject.getJSONObject("ticker");

                String price = parentObject2.getString("last");
                // String time = parentObject.getString("time");

                return price;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            float nova = Float.parseFloat(result);
            float valor = Float.valueOf(String.format(Locale.US,"%.2f",nova));
            float resultado = Float.valueOf(String.format(Locale.US,"%.2f",valor/dolar));
            if (nova> antigaetc){
                txtetcus.setText(resultado+" $");
                txtetcbr.setText(valor+" R$");
                txtetcbr.setTextColor(Color.GREEN);
                txtetcus.setTextColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtetcbr.setTextColor(Color.WHITE);
                        txtetcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else if (nova < antigaetc){
                txtetcus.setText(resultado+" $");
                txtetcbr.setText(valor+" R$");
                txtetcbr.setTextColor(Color.RED);
                txtetcus.setTextColor(Color.RED);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtetcbr.setTextColor(Color.WHITE);
                        txtetcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else{
                txtetcus.setText(resultado+" $");
                txtetcbr.setText(valor+" R$");
            }
            antigaetc = Float.parseFloat(result);
        }
    }
    public class JSONTaskRIP extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentObject2 = parentObject.getJSONObject("ticker");

                String price = parentObject2.getString("last");
                // String time = parentObject.getString("time");

                return price;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            float nova = Float.parseFloat(result);
            float valor = Float.valueOf(String.format(Locale.US,"%.2f",nova));
            float resultado = Float.valueOf(String.format(Locale.US,"%.2f",valor/dolar));
            if (nova> antigartc){
                txtrtcus.setText(resultado+" $");
                txtrtcrs.setText(valor+" R$");
                txtrtcrs.setTextColor(Color.GREEN);
                txtrtcus.setTextColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtrtcrs.setTextColor(Color.WHITE);
                        txtrtcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else if (nova < antigartc){
                txtrtcus.setText(resultado+" $");
                txtrtcrs.setText(valor+" R$");
                txtrtcrs.setTextColor(Color.RED);
                txtrtcus.setTextColor(Color.RED);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtrtcrs.setTextColor(Color.WHITE);
                        txtrtcus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else{
                txtrtcus.setText(resultado+" $");
                txtrtcrs.setText(valor+" R$");
            }
            antigartc = Float.parseFloat(result);
        }
    }
    public class JSONTaskBCH extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String ... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONObject parentObject2 = parentObject.getJSONObject("ticker");

                String price = parentObject2.getString("last");
                // String time = parentObject.getString("time");

                return price;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                connection.disconnect();
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            float nova = Float.parseFloat(result);
            float valor = Float.valueOf(String.format(Locale.US,"%.2f",nova));
            float resultado = Float.valueOf(String.format(Locale.US,"%.2f",valor/dolar));
            if (nova> antigabch){
                txtbchus.setText(resultado+" $");
                txtbchrs.setText(valor+" R$");
                txtbchrs.setTextColor(Color.GREEN);
                txtbchus.setTextColor(Color.GREEN);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtbchrs.setTextColor(Color.WHITE);
                        txtbchus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else if (nova < antigabch){
                txtbchus.setText(resultado+" $");
                txtbchrs.setText(valor+" R$");
                txtbchrs.setTextColor(Color.RED);
                txtbchus.setTextColor(Color.RED);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtbchrs.setTextColor(Color.WHITE);
                        txtbchus.setTextColor(Color.WHITE);
                    }
                },  1000);
            }else{
                txtbchus.setText(resultado+" $");
                txtbchrs.setText(valor+" R$");
            }
            antigabch = Float.parseFloat(result);
        }
    }
}
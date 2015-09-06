package com.ccparram.tupi.navigation.articles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccparram.tupi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class PronosticoFragment extends Fragment {
    public static final String ARG_ARTICLES_NUMBER = "articles_number";

    private final String TAG = this.getClass().getName();

    public PronosticoFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pronostico, container, false);
        int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        String article = getResources().getStringArray(R.array.Tags)[i];

        getActivity().setTitle(article);

        TextView txVcabecera = (TextView)rootView.findViewById(R.id.cabecera);
        TextView txVtemperaturaHoy = (TextView)rootView.findViewById(R.id.temperaturaHoy);
        TextView txVminHoy = (TextView)rootView.findViewById(R.id.minHoy);
        TextView txVmaxHoy = (TextView)rootView.findViewById(R.id.maxHoy);

        ImageView iconHoy = (ImageView)rootView.findViewById(R.id.iconHoy);
        ImageView iconMañana = (ImageView)rootView.findViewById(R.id.iconMañana);

        ObtenerCLimaTask pronosticoTask = new ObtenerCLimaTask();

        try {
            JSONObject pronosticoJson = pronosticoTask.execute().get();

            String cabecera;
            String temperatura;
            String minHoy;
            String maxHoy;

            JSONObject city = pronosticoJson.getJSONObject("city");
            cabecera = city.getString("name");
            JSONObject tempHoy = pronosticoJson.getJSONArray("list").getJSONObject(0).getJSONObject("temp");
            temperatura = tempHoy.getString("day");
            minHoy = tempHoy.getString("min");
            maxHoy = tempHoy.getString("max");





            txVcabecera.setText(getResources().getString(R.string.cabecera) + " " + cabecera);
            txVtemperaturaHoy.setText(Integer.toString(Math.round(Float.parseFloat(temperatura))));
            txVminHoy.setText(getResources().getString(R.string.min) + " " + Integer.toString(Math.round(Float.parseFloat(minHoy))));
            txVmaxHoy.setText(getResources().getString(R.string.max) + " " + Integer.toString(Math.round(Float.parseFloat(maxHoy))));

            int id = pronosticoJson.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getInt("id");
            iconHoy.setImageResource(getArtResourceForWeatherCondition(id));

            id = pronosticoJson.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getInt("id");
            iconMañana.setImageResource(getArtResourceForWeatherCondition(id));



            }
        catch (InterruptedException e) {
            e.printStackTrace();
            }
        catch (ExecutionException e) {
            e.printStackTrace();
            } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }


    public int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }


    private class ObtenerCLimaTask extends AsyncTask<Void, Void, JSONObject>{

        String pronosticoJsonStr = null;


        @Override
        protected JSONObject doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            JSONObject pronosticoJson = new JSONObject();


            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=medellin,co&units=metric&cnt=2");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                pronosticoJsonStr = buffer.toString();

                Log.e(TAG, " Json" + pronosticoJsonStr);

                try {
                    pronosticoJson = new JSONObject(pronosticoJsonStr);


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "error");
                }

            }
            catch (IOException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }


            return pronosticoJson;
        }



    }




}

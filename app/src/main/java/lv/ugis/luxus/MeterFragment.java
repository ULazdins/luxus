package lv.ugis.luxus;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeterFragment extends Fragment {
    private SensorManager mSensorManager;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float lux = event.values[0];

            Log.d("MeterFragment", "The light is: " + lux);

            outputView.setText("The light is: " + lux);

            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) maskVisible.getLayoutParams();
            p.weight = lux;
            maskVisible.setLayoutParams(p);

            LinearLayout.LayoutParams p2 = (LinearLayout.LayoutParams) maskInvisible.getLayoutParams();
            p2.weight = 40000 - lux;
            maskInvisible.setLayoutParams(p2);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    };
    private TextView outputView;
    private View maskVisible;
    private View maskInvisible;

    public MeterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_meter, container, false);

        outputView = rootView.findViewById(R.id.output_text);
        maskVisible = rootView.findViewById(R.id.mask_visible);
        maskInvisible = rootView.findViewById(R.id.mask_invisible);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Context context = getActivity();

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null){
            mSensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        mSensorManager.unregisterListener(sensorEventListener);
    }
}

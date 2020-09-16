package com.example.diewithme;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends MainActivity  implements SensorEventListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flappy_phone);

        //Retrieve SensorManager, Android's tool for manipulating phone sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Set SensorManager to retrieve accelerometer data
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register this Activity as a listener for changes in phone acceleration detected by the accelerometer
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);

        //Retrieve TextViews that will be modified (result, high score, and replay button)
        result = (TextView) findViewById(R.id.Result);
        highScore = (TextView) findViewById(R.id.HighScore);
        playAgain = (TextView) findViewById(R.id.PlayAgain);
    }

    //TextViews for result, high score, and replay button
    TextView result;
    TextView highScore;
    TextView playAgain;

    //Variable to access SensorManager, Android's tool for sensor manipulation
    SensorManager sensorManager;

    //Variable to access accelerometer, the particular sensor this app is interested in
    Sensor accel;

    //Doubles for each component of acceleration
    double ax = 0;
    double ay = 0;
    double az = 0;

    //Double for acceleration magnitude
    double a = 0;

    //The app will perceive that the phone is in freefall if the magnitude of acceleration detected dips below this value
    final double FREEFALL_CONSTANT = 7;

    //Indicates the phone is primed to be thrown
    boolean primed = false;

    //Indicates that the phone has been thrown and entered freefall
    boolean thrown = false;

    //Time of phone entering freefaal
    long startTime = 0;

    //Time of phone leaving freefall
    long stopTime = 0;

    //Total time phone spends in freefall
    double totalTime = 0;

    //Double to store current high score
    double highScoreDouble = 0;

    //Method that is called whenever the user exits or "pauses" the app
    protected void onPause() {

        super.onPause();

        //Unregister this Activity as a listener for changes in phone acceleration detected by the accelerometer every time the user exits the app
        sensorManager.unregisterListener(this);
    }

    //Method that is called whenever the user re-enters or "resumes" the app after exiting it
    protected void onResume() {

        super.onResume();

        //Re-register this Activity as a listener for changes in phone acceleration detected by the accelerometer every time the user re-enters the app
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Method that is called whenever the replay button is pressed
    public void throwPhone(View v) {

        //Hide the replay button
        playAgain.setVisibility(View.INVISIBLE);

        //Set the result display field to display "Throw!" to inform the user the phone is ready to be thrown
        result.setText("扔!");

        //Indicate that the phone is primed to be thrown
        primed = true;
    }

    //Method called whenever a phone sensor registers a new value
    @Override
    public void onSensorChanged(SensorEvent event) {

        //If the sensor changed is the accelerometer
        if (event.sensor.getType()== Sensor.TYPE_ACCELEROMETER){

            //Store the x, y, z components of acceleration measured by the accelerometer in these doubles
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];

            //Calculate the magnitude of accleration on the phone at this instant by
            a = Math.sqrt(Math.pow(ax, 2) + Math.pow(ay, 2) + Math.pow(az, 2));

            //If the phone is primed to throw AND the acceleration detected by the phone has dipped below the freefall constant (the phone is in the air)
            if(primed && a < FREEFALL_CONSTANT) {

                //Indicate that the phone is no longer primed to throw
                primed = false;

                //Indicate that the phone has now been thrown and is in freefall
                thrown = true;

                //Record the phone's time of entry to freefall
                startTime = System.currentTimeMillis();
            }

            //If the phone has been thrown and entered freefall AND the acceleration detected by the phone has risen above the freefall constant again (the phone is no longer in the air)
            if(thrown && ay > FREEFALL_CONSTANT){

                //Record the phone's time of exiting freefall
                stopTime = System.currentTimeMillis();

                //Indicate that the phone is no longer in the air
                thrown = false;

                //Calculate the total time the phone was in the air
                totalTime = (double) (stopTime - startTime) / 1000;

                //Set the current result display text to display the air time of the phone
                result.setText(totalTime + " s");

                //Make the replay button visible again
                playAgain.setVisibility(View.VISIBLE);

                //Set the display text of the replay button to "Play Again"
                playAgain.setText("再飞一次");

                //If the total air time of the phone this round is greater than the current high score
                if(totalTime > highScoreDouble){

                    //Replace the high score with this round's air time
                    highScoreDouble = totalTime;

                    //Set the current high score display this round's air time
                    highScore.setText(highScoreDouble + " s");
                }
            }
        }
    }

    //Unmodified method, not relevant
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

//    //Unmodified method, not relevant
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_send_me_to_heaven, menu);
//        return true;
//    }

//    //Unmodified method, not relevant
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}

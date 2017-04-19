package comnikitc.github.mobdev_hw_2_simple;

import android.content.Context;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateColorPicker();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        ColorImageView chooseColorView = (ColorImageView) findViewById(R.id.chooseColorImage);
        savedInstanceState.putFloatArray("chooseColor", chooseColorView.getHsvColor());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        DisplayCurrentStatus(savedInstanceState.getFloatArray("chooseColor"));
    }


    protected void DisplayCurrentStatus(float[] hsvColor) {

        ColorImageView chooseColorView = (ColorImageView) findViewById(R.id.chooseColorImage);
        chooseColorView.setHsvColor(hsvColor);

        TextView rgbText = (TextView) findViewById(R.id.chooseValueRGB);
        TextView hsvText = (TextView) findViewById(R.id.chooseValueHSV);

        chooseColorView.setBackgroundColor(Color.HSVToColor(hsvColor));

        String newRGBColor = ColorButton.GetStringRGBColor(hsvColor);
        String newHSVColor = ColorButton.GetStringHSVColor(hsvColor);

        rgbText.setText(newRGBColor);
        hsvText.setText(newHSVColor);
    }

    protected View.OnTouchListener GetOnTouchListener() {
        View.OnTouchListener onClickListener = new View.OnTouchListener() {
            float x = 0;
            float y = 0;
            ColorPickerScroll scroll = (ColorPickerScroll) findViewById(R.id.colorPickerScroll);

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                ColorButton currentColorButton = (ColorButton) view;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        HandleActionDown(currentColorButton);
                        break;
                }
                return false;
            }
        };
        return onClickListener;
    }

    protected int getSign(double number) {
        if (number > 0)
            return 1;
        else if (number < 0)
            return  -1;

        return 0;
    }

    protected void HandleActionDown(ColorButton currentColorButton) {
        DisplayCurrentStatus(currentColorButton.getCurrentColor());
    }

    protected void DisableColorPickerScroll() {
        ColorPickerScroll scroll = (ColorPickerScroll) findViewById(R.id.colorPickerScroll);
        scroll.setIsCanMove(false);
    }

    protected void CallVibrator() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    protected void CreateColorPicker() {
        float[] pixelHSV = new float[3];
        pixelHSV[0] = 12.25f;
        pixelHSV[1] = 1;
        pixelHSV[2] = 1;
        int countButtons = 16;
        final LinearLayout colorPickerLayout = (LinearLayout) findViewById(R.id.colorPickerLayout);

        for (int i = 0; i < countButtons; i++) {
            ColorButton button = new ColorButton(this, pixelHSV.clone());
            button.setOnTouchListener(GetOnTouchListener());
            colorPickerLayout.addView(button);
            pixelHSV[0] += 22.25;
        }
        GradientColorPicker.SetGradientBackGround(colorPickerLayout);
    }

}
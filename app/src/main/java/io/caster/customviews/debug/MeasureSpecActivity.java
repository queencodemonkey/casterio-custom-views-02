package io.caster.customviews.debug;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.caster.customviews.R;
import io.caster.customviews.TimerView;

/**
 * Activity that compares the layout parameters, parent-given measure specs, and ultimate size
 * of {@link TimerView} instances based on the parent's dimensions.
 */
public class MeasureSpecActivity extends AppCompatActivity {
    private TimerView timerViewA;
    private TimerView timerViewB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurespec);

        timerViewA = (TimerView)findViewById(R.id.timer_mp);
        timerViewB = (TimerView)findViewById(R.id.timer_wc);
    }

    @Override
    protected void onResume() {
        super.onResume();

        timerViewA.start();
        timerViewB.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        timerViewA.stop();
        timerViewB.stop();
    }
}

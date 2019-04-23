package carlos.farfan.yearpickerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YearPickerView view = findViewById(R.id.ypv_years);

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.set(1900, Calendar.JANUARY, 1);
        maxDate.set(2100, Calendar.DECEMBER, 31);

//        view.setRange(minDate, maxDate);
//        view.setYear(2019);

        view.setOnYearSelectedListener(new OnYearSelectedListener() {
            @Override
            public void onYearChanged(YearPickerView view, int year) {
                Toast.makeText(MainActivity.this, String.valueOf(year), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
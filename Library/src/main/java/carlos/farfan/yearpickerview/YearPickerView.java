package carlos.farfan.yearpickerview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.core.content.res.ResourcesCompat;

import java.util.Calendar;

/**
 * Created by Carlos Farfan on 22/04/2019.
 * Email: carlos.farfan015@outlook.com
 */
public class YearPickerView extends ListView {

    private YearAdapter yearAdapter;

    private OnYearSelectedListener listener;

    private int textYearSize;
    private int textYearColor;
    private int textYearColorActivated;

    private int viewSize;
    private int childSize;

    public YearPickerView(Context context) {
        super(context);
    }

    public YearPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        loadStyle(context, attrs);

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.set(1900, Calendar.JANUARY, 1);
        maxDate.set(2100, Calendar.DECEMBER, 31);

        final Resources res = context.getResources();
        viewSize = res.getDimensionPixelOffset(R.dimen.size_226dp);
        childSize = res.getDimensionPixelOffset(R.dimen.size_64dp);

        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int year = yearAdapter.getYearForPosition(position);
                yearAdapter.setSelection(year);

                if (listener != null) {
                    listener.onYearChanged(YearPickerView.this, year);
                }
            }
        });

        yearAdapter = new YearAdapter(context);
        yearAdapter.setTextYearSize(textYearSize);
        yearAdapter.setTextColors(textYearColorActivated, textYearColor);
        setAdapter(yearAdapter);
        setRange(minDate, maxDate);
        setYear(Calendar.getInstance().get(Calendar.YEAR));
    }

    private void loadStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YearPickerView);

        try {
            textYearSize = typedArray.getDimensionPixelSize(R.styleable.YearPickerView_textYearSize, 41);
            textYearColor = typedArray.getColor(R.styleable.YearPickerView_textYearColor, Color.BLACK);
            textYearColorActivated = typedArray.getColor(R.styleable.YearPickerView_textYearColorActivated, Color.BLACK);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    public void setYear(final int year) {
        yearAdapter.setSelection(year);

        post(new Runnable() {
            @Override
            public void run() {
                int position = yearAdapter.getPositionForYear(year);
                if (position >= 0 && position < getCount()) {
                    setSelectionCentered(position);
                }
            }
        });
    }

    public void setSelectionCentered(int position) {
        final int offset = viewSize / 2 - childSize / 2;
        setSelectionFromTop(position, offset);
    }

    public void setRange(Calendar min, Calendar max) {
        yearAdapter.setRange(min, max);
    }

    public void setOnYearSelectedListener(OnYearSelectedListener listener) {
        this.listener = listener;
    }
}
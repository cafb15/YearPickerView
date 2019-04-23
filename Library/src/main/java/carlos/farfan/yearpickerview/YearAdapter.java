package carlos.farfan.yearpickerview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Carlos Farfan on 22/04/2019.
 * Email: carlos.farfan015@outlook.com
 */
public class YearAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private int textYearSize;
    private int textYearColor;
    private int textYearColorActivated;

    private int activatedYear;
    private int minYear;
    private int count;

    YearAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Integer getItem(int position) {
        return getYearForPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return getYearForPosition(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView tvYear;
        final boolean hasNewView = convertView == null;

        if (hasNewView) {
            tvYear = (TextView) inflater.inflate(R.layout.year_label, parent, false);
        } else {
            tvYear = (TextView) convertView;
        }

        final int year = getYearForPosition(position);
        final boolean activated = activatedYear == year;

        if (hasNewView || tvYear.isActivated() != activated) {
            fillText(tvYear, activated);
        }

        tvYear.setText(String.valueOf(year));

        return tvYear;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    void setTextYearSize(int textYearSize) {
        this.textYearSize = textYearSize;
    }

    void setTextColors(int textYearColorActivated, int textYearColor) {
        this.textYearColorActivated = textYearColorActivated;
        this.textYearColor = textYearColor;
    }

    void setRange(Calendar minDate, Calendar maxDate) {
        final int minYear = minDate.get(Calendar.YEAR);
        final int count = maxDate.get(Calendar.YEAR) - minYear + 1;

        if (this.minYear != minYear || this.count != count) {
            this.minYear = minYear;
            this.count = count;
            notifyDataSetInvalidated();
        }
    }

    void setSelection(int year) {
        if (activatedYear != year) {
            activatedYear = year;
            notifyDataSetChanged();
        }
    }

    int getPositionForYear(int year) {
        return year - minYear;
    }

    int getYearForPosition(int position) {
        return minYear + position;
    }

    private void fillText(TextView tvYear, boolean activated) {
        tvYear.setActivated(activated);

        if (activated) {
            tvYear.setTypeface(tvYear.getTypeface(), Typeface.BOLD);
            tvYear.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tvYear.setTextColor(textYearColorActivated);
        } else {
            tvYear.setTextSize(TypedValue.COMPLEX_UNIT_PX, textYearSize);
            tvYear.setTextColor(textYearColor);
        }
    }
}
package com.example.fitnessforge.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessforge.databinding.FragmentReportBinding;
import com.example.fitnessforge.entity.WorkoutLog;
import com.example.fitnessforge.viewmodel.WorkoutLogViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;
    private WorkoutLogViewModel workoutLogViewModel;

    private String chartType;
    private Date startDate;
    private Date endDate;

    public ReportFragment (){}

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //ViewModel
        workoutLogViewModel = new ViewModelProvider(this).get(WorkoutLogViewModel.class);

        //spinner - changes visibility of frequency charts given the selection
        displaySpinner();
        binding.chartTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chartType = binding.chartTypeSpinner.getSelectedItem().toString();
                if (chartType.equals("pie chart")){
                    binding.monthlyPieChartFreq.setVisibility(View.VISIBLE);
                    binding.monthlyBarChartFreq.setVisibility(View.GONE);
                } else if (chartType.equals("bar chart")) {
                    binding.monthlyBarChartFreq.setVisibility(View.VISIBLE);
                    binding.monthlyPieChartFreq.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //updates the frequency charts whenever workout logs are changed
        workoutLogViewModel.getWorkoutLogsForCurrentMonth(getCurrentUserId()).observe(getViewLifecycleOwner(), new Observer<List<WorkoutLog>>() {
            @Override
            public void onChanged(List<WorkoutLog> workoutLogs) {
                    populateFreqBarChart(mapFreqToDay(workoutLogs));
                    populateFreqPieChart(mapFreqToDay(workoutLogs));
            }
        });

        //opens the start date picker dialog when pressed
        binding.selectDatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatePickerDialog();
            }
        });

        return view;
    }



    //FREQ CHART
    //Maps the logs to a map - assign workout log to key with integer that relates to day of the week
    private Map<Integer, Integer> mapFreqToDay(List<WorkoutLog> logs){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 0);
        map.put(2, 0);
        map.put(3, 0);
        map.put(4, 0);
        map.put(5, 0);
        map.put(6, 0);
        map.put(7, 0);

        Date date = null;
        Calendar calendar = null;
        for (WorkoutLog workoutLog : logs){
            date = workoutLog.getDate();
            calendar = Calendar.getInstance();
            calendar.setTime(date);

            int day = calendar.get(Calendar.DAY_OF_WEEK);
            map.put(day, map.getOrDefault(day,0) + 1);
        }

        return map;
    }

    //FREQ BAR CHART
    //Populates the frequency bar chart given the map of workout logs
    private void populateFreqBarChart(Map<Integer, Integer> workoutFreqMap) {
        List<BarEntry> entries = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : workoutFreqMap.entrySet()) {
            entries.add(new BarEntry(entry.getKey() - 1, entry.getValue()));
        }

        int[] colours = new int[]{
                Color.rgb(173, 216, 230),
                Color.rgb(144,238,144),
                Color.rgb(255,182,193),
                Color.rgb(216,191,216),
                Color.rgb(255,204,255),
                Color.rgb(255,204,153),
                Color.rgb(211,211,211)
        };

        BarDataSet dataSet = new BarDataSet(entries, "Workout Frequency");
        dataSet.setColors(colours);

        BarData barData = new BarData(dataSet);

        BarChart barChart = binding.monthlyBarChartFreq;

        barChart.getAxisRight().setEnabled(false);

        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getDayLabels()));
        barChart.getXAxis().setLabelCount(7);
        barChart.invalidate();
    }

    private List<String> getDayLabels() {
        List<String> labels = new ArrayList<>();
        labels.add("Sun");
        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thu");
        labels.add("Fri");
        labels.add("Sat");
        return labels;
    }


    //FREQ PIE CHART
    //Populates the frequency pie chart given the map of workout logs
    private void populateFreqPieChart(Map<Integer, Integer> workoutFreqMap) {
        PieChart pieChart = binding.monthlyPieChartFreq;
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry: workoutFreqMap.entrySet()){
//            entries.add(new PieEntry(entry.getValue(), getDayLabel(entry.getKey())));
            if (entry.getValue() > 0) {
                entries.add(new PieEntry(entry.getValue(), getDayLabel(entry.getKey())));
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Workout Frequency");
        int[] colours = new int[]{
                Color.rgb(173, 216, 230),
                Color.rgb(144,238,144),
                Color.rgb(255,182,193),
                Color.rgb(216,191,216),
                Color.rgb(255,204,255),
                Color.rgb(255,204,153),
                Color.rgb(211,211,211)
        };
        dataSet.setColors(colours);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.invalidate();
    }

    private String getDayLabel(int dayIndex) {
        switch (dayIndex) {
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            case 7:
                return "Sat";
            default:
                return "";
        }
    }

    //Spinner to select chart type
    private void displaySpinner(){
        Spinner muscleSpinner = binding.chartTypeSpinner;
        List<String> list = new ArrayList<>();
        list.add("Select a chart type");
        list.add("pie chart");
        list.add("bar chart");

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>
                (requireContext(), android.R.layout.simple_spinner_item, list);
        muscleSpinner.setAdapter(spinnerAdapter);

    }


    //TIME CHART
    //Start date dialog
    private void showStartDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        startDate = calendar.getTime();
                        showEndDatePickerDialog();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(getStartOfMonth().getTime());
        datePickerDialog.getDatePicker().setMaxDate(getEndOfMonth().getTime());

        datePickerDialog.show();
    }

    //End date dialog
    private void showEndDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        endDate = calendar.getTime();

                        // Query data between startDate and endDate and populate TimeBarChart
                        workoutLogViewModel.getWorkoutLogsBetweenDates(getCurrentUserId(), startDate, endDate)
                                .observe(getViewLifecycleOwner(), new Observer<List<WorkoutLog>>() {
                                    @Override
                                    public void onChanged(List<WorkoutLog> workoutLogs) {
                                        populateTimeBarChart(workoutLogs);
                                    }
                                });
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
        datePickerDialog.getDatePicker().setMaxDate(getEndOfMonth().getTime());
        datePickerDialog.show();
    }

    private Date getStartOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private Date getEndOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    //populates the time bar chart given the user's workout logs
    private void populateTimeBarChart(List<WorkoutLog> logs) {
        BarChart barChart2 = binding.monthlyBarChartTime;

        List<BarEntry> entries = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();

        Map<Date, Long> dateDurationMap = new HashMap<>();
        for (WorkoutLog log : logs) {
            Date date = log.getDate();
            if (date.after(startDate) && date.before(endDate)) {
                long duration = log.getDuration();
                dateDurationMap.put(date, dateDurationMap.getOrDefault(date, 0L) + duration);
            }
        }

        List<Map.Entry<Date, Long>> sortedEntries = new ArrayList<>(dateDurationMap.entrySet());
        Collections.sort(sortedEntries, (e1, e2) -> e1.getKey().compareTo(e2.getKey()));

        int index = 0;
        for (Map.Entry<Date, Long> entry : sortedEntries) {
            entries.add(new BarEntry(index, entry.getValue()));
            index++;
        }

        //creates the x-axis labels given the workout log dates
        List<String> labels = new ArrayList<>();
        for (Map.Entry<Date, Long> entry : sortedEntries) {
            Date date = entry.getKey();
            String formattedDate = formatDate(date);
            labels.add(formattedDate);
        }

        int color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        for (int i = 0; i < entries.size(); i++) {
            colours.add(color);
        }

        BarDataSet dataSet = new BarDataSet(entries, "Workout duration");
        dataSet.setColors(colours);

        BarData barData = new BarData(dataSet);
        barChart2.setData(barData);
        barChart2.getDescription().setEnabled(false);
        barChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart2.getAxisRight().setEnabled(false);
        barChart2.getLegend().setEnabled(false);
        barChart2.invalidate();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    private String getCurrentUserId(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        return currentUser.getUid();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

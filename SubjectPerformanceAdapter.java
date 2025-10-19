package com.example.studyplanner.ui.main;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studyplanner.R;
import com.example.studyplanner.data.model.SubjectPerformance;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class SubjectPerformanceAdapter extends RecyclerView.Adapter<SubjectPerformanceAdapter.ViewHolder> {

    private List<SubjectPerformance> performanceList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_performance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubjectPerformance performance = performanceList.get(position);
        holder.subjectName.setText(performance.getSubjectName());
        holder.performancePercentage.setText(String.format("%d%%", performance.getOverallPercentage()));
        setupBarChart(holder.barChart, performance.getWeeklyPerformance());
    }

    @Override
    public int getItemCount() {
        return performanceList.size();
    }

    public void setPerformanceList(List<SubjectPerformance> performanceList) {
        this.performanceList = performanceList;
        notifyDataSetChanged();
    }

    private void setupBarChart(BarChart barChart, List<Float> weeklyData) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < weeklyData.size(); i++) {
            entries.add(new BarEntry(i, weeklyData.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Weekly Performance");
        dataSet.setColor(ContextCompat.getColor(barChart.getContext(), R.color.soft_light_blue));
        dataSet.setDrawValues(false);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.setTouchEnabled(false);

        // Customize X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"W1", "W2", "W3", "W4"}));

        // Customize Y-axis
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisLeft().setAxisMaximum(100f);
        barChart.getAxisRight().setEnabled(false);

        barChart.invalidate(); // refresh
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView performancePercentage;
        BarChart barChart;

        ViewHolder(View view) {
            super(view);
            subjectName = view.findViewById(R.id.tv_subject_name);
            performancePercentage = view.findViewById(R.id.tv_subject_percentage);
            barChart = view.findViewById(R.id.bar_chart);
        }
    }
}

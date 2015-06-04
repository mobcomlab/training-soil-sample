package th.co.egat.day3.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import th.co.egat.day3.DetailActivity;
import th.co.egat.day3.R;
import th.co.egat.day3.models.SoilSample;

public class SoilSampleAdapter
        extends RecyclerView.Adapter<SoilSampleAdapter.ViewHolder> {

    public static final class ViewHolder
            extends RecyclerView.ViewHolder {

        public final TextView sampleId;
        public final TextView sampleLocation;
        public final TextView sampleDate;
        public final TextView samplePh;

        public ViewHolder(final View itemView) {
            super(itemView);
            sampleId = (TextView) itemView.findViewById(R.id.sample_id);
            sampleLocation = (TextView) itemView.findViewById(R.id.sample_location);
            sampleDate = (TextView) itemView.findViewById(R.id.sample_date);
            samplePh = (TextView) itemView.findViewById(R.id.sample_ph);
        }

    }

    private final Context context;
    private final List<SoilSample> data;

    public SoilSampleAdapter(final Context context, final List<SoilSample> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public SoilSampleAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.row_sample, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SoilSampleAdapter.ViewHolder holder, final int position) {
        final SoilSample sample = data.get(position);

        // Bind data
        holder.sampleId.setText(sample.getId());
        holder.samplePh.setText(String.valueOf(sample.getPh()));
        holder.sampleLocation.setText(
                String.format("%s, %s",
                              sample.getxCoord(), sample.getyCoord()));
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        holder.sampleDate.setText(sdf.format(sample.getDate()));

        // Clicks
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // Launch detail activity
                final Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("soilSampleId", sample.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

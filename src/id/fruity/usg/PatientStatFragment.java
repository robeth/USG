package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.PhotoConverter;
import id.fruity.usg.database.converter.ValidationConverter;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.grow.GestationalAge;
import id.fruity.usg.grow.ProportionalityFunction;
import id.fruity.usg.grow.TOW;
import id.fruity.usg.model.KandunganModel;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Ellipse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class PatientStatFragment extends SherlockFragment {
	private LinearLayout container;
	private GraphicalView chartView;
	private Spinner pregnancySpinner;
	private LayoutInflater minflater;
	private USGDBHelper helper;
	private String patientId;
	private ArrayList<KandunganModel> kms;
	private int selectedIndex;
	private double[] dates, weights;
	private int selectedItem;
	private boolean isEmpty;
	private ArrayList<Validation> vals;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		patientId = b.getString("patientId");
		helper = USGDBHelper.getInstance(getActivity());
		helper.open();
		KandunganModel km = new KandunganModel();
		kms = km.getItems(helper.getKandunganOfPatient(patientId));

		for (int i = 0; i < kms.size(); i++) {
			ArrayList<Photo> photos = PhotoConverter.convertAll(helper
					.getPhotosOfKandungan(patientId, kms.get(i)
							.getNoKandungan()));
			Log.d("Pasien USG Fragment", "Photos size:" + photos.size());
			kms.get(i).setFotos(photos);
		}
		Collections.sort(kms);
		helper.close();
		selectedIndex = kms.size() - 1;
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.patient_stats, container, false);
		if (!kms.isEmpty()) {

			container = (LinearLayout) v.findViewById(R.id.container);
			minflater = inflater;
			pregnancySpinner = (Spinner) v.findViewById(R.id.pregnancy);
			pregnancySpinner.setSelection(selectedIndex);
			pregnancySpinner.setAdapter(new PregnancySpinnerAdapter(kms));
			pregnancySpinner
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parentView,
								View selectedItemView, int position, long id) {
							selectedItem = position;
							refreshGraph();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}

					});
		}
		return v;
	}

	private void getCurrentGrowthLine(float tow) {
		ArrayList<Photo> selectedPhotos = kms.get(selectedItem).getFotos();
		Collections.sort(selectedPhotos, new Photo.TimestampComparator());
		
		if (selectedPhotos.isEmpty()) {
			Toast.makeText(
					this.getActivity(),
					"No photos found in pregnancy "
							+ kms.get(selectedItem).getNoKandungan(),
					Toast.LENGTH_SHORT).show();
			isEmpty = true;
			dates = new double[] { 0 };
			weights = new double[] { 0 };
			return;
		}
		dates = new double[selectedPhotos.size()];
		weights = new double[selectedPhotos.size()];

		helper.open();
		Cursor c = helper.getValidations(selectedPhotos.get(0).getNoPhoto(),
				selectedPhotos.get(0).getNoKandungan(), selectedPhotos.get(0)
						.getIdPasien());
		vals = ValidationConverter.convertAll(c);

		// calculate first week
		long firstTime = selectedPhotos.get(0).getCreateTimestampLong();
		float firstHC = Ellipse.keliling(vals.isEmpty() ? selectedPhotos.get(0)
				.getA() : vals.get(0).getA(), vals.isEmpty() ? selectedPhotos
				.get(0).getB() : vals.get(0).getB())
				* Ellipse.SCALING * 0.1f;
		float firstWeek = GestationalAge.approximateGA1(firstHC);
		float lastWeek = firstWeek;

		dates[0] = firstWeek;
		weights[0] = ProportionalityFunction.idealTow(tow, firstWeek);
		Log.d("First node ", "HC-Week-Weight-tow :" + firstHC + "-" + firstWeek
				+ "-" + weights[0]);
		for (int i = 1; i < selectedPhotos.size(); i++) {
			c = helper.getValidations(selectedPhotos.get(i).getNoPhoto(),
					selectedPhotos.get(i).getNoKandungan(), selectedPhotos.get(i)
							.getIdPasien());
			vals = ValidationConverter.convertAll(c);
			float hc = Ellipse.keliling(vals.isEmpty() ? selectedPhotos.get(i).getA() : vals.get(0).getA(),
					vals.isEmpty()? selectedPhotos.get(i).getB() : vals.get(0).getB()) * Ellipse.SCALING * 0.1f;
			float deltaTime = Math.abs(selectedPhotos.get(i)
					.getCreateTimestampLong()
					- selectedPhotos.get(i - 1).getCreateTimestampLong());
			lastWeek = (deltaTime < DateUtils.ONE_WEEK) ? lastWeek + 1
					: deltaTime / DateUtils.ONE_WEEK;

			dates[i] = lastWeek;
			weights[i] = ProportionalityFunction.idealTow(tow,
					GestationalAge.approximateGA1(hc));
			Log.d("node " + i, "HC-Week-Weight :" + hc + "-" + lastWeek + "-"
					+ weights[i]);
		}
		helper.close();

	}

	private void refreshGraph() {
		container = (LinearLayout) getView().findViewById(R.id.container);

		String[] titles = new String[] { "10th centil", "Proportional", "90th",
				"Actual" };
		int startWeek = ProportionalityFunction.DEFAULT_START_WEEK;
		int lastWeek = ProportionalityFunction.DEFAULT_LAST_WEEK;
		List<double[]> x = new ArrayList<double[]>();
		double[] axis = new double[lastWeek - startWeek + 1];
		for (int i = 0; i < axis.length; i++) {
			axis[i] = startWeek + i;
		}
		x.add(axis);
		x.add(axis);
		x.add(axis);

		getCurrentGrowthLine(TOW.getDefaultTOW());
		x.add(dates);

		List<double[]> values = new ArrayList<double[]>();
		values.add(ProportionalityFunction.getLowerCentileGWSeries(TOW
				.getDefaultTOW()));
		values.add(ProportionalityFunction.getIdealGWSeries(TOW.getDefaultTOW()));
		values.add(ProportionalityFunction.getUpperCentileGWSeries(TOW
				.getDefaultTOW()));
		values.add(weights);

		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.BLUE,
				Color.RED };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT,
				PointStyle.POINT, PointStyle.POINT, PointStyle.X };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		setChartSettings(renderer, "Fetal Growth", "Gestational Age",
				"Gestational Weight", startWeek - 2, lastWeek + 2, 0,
				TOW.getDefaultTOW(), Color.BLACK, Color.BLACK);
		renderer.setXLabels(10);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -5, 60, -10,
				TOW.getDefaultTOW() + 1000 });
		renderer.setZoomLimits(new double[] { -5, 60, -10,
				TOW.getDefaultTOW() + 1000 });
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setGridColor(Color.DKGRAY);
		renderer.setXLabelsColor(Color.BLUE);
		renderer.setYLabelsColor(0, Color.BLUE);
		renderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));

		container.removeView(chartView);
		chartView = ChartFactory.getCubeLineChartView(getActivity(),
				buildDataset(titles, x, values), renderer, 0.33f);
		container.addView(chartView, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		chartView.invalidate();
	}

	@Override
	public void onResume() {
		super.onResume();
		refreshGraph();

	}

	protected XYMultipleSeriesRenderer buildRenderer(int[] colors,
			PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer, colors, styles);
		return renderer;
	}

	protected void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
			PointStyle[] styles) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesDataset buildDataset(String[] titles,
			List<double[]> xValues, List<double[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
			List<double[]> xValues, List<double[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			double[] xV = xValues.get(i);
			double[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}

	private class PregnancySpinnerAdapter implements SpinnerAdapter {
		private ArrayList<KandunganModel> models;

		public PregnancySpinnerAdapter(ArrayList<KandunganModel> models) {
			this.models = models;
		}

		@Override
		public int getCount() {
			return models.size();
		}

		@Override
		public Integer getItem(int position) {
			return models.get(position).getNoKandungan();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = minflater.inflate(R.layout.pregnancy_item, null);
			}
			TextView text = (TextView) convertView
					.findViewById(R.id.pregnancy_text);
			text.setText("Pregnancy " + models.get(position).getNoKandungan());
			return convertView;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			return kms.isEmpty();
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			// TODO Auto-generated method stub

		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = minflater.inflate(R.layout.pregnancy_item, null);
			}
			TextView text = (TextView) convertView
					.findViewById(R.id.pregnancy_text);
			text.setText("Pregnancy " + kms.get(position).getNoKandungan());
			return convertView;
		}

	}
}

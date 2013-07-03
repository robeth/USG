package id.fruity.usg.model;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.ValidationConverter;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.grow.GestationalAge;
import id.fruity.usg.grow.ProportionalityFunction;
import id.fruity.usg.grow.TOW;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Ellipse;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class Analyzer {
	public static Activity A;
	public static final int NORMAL_GROWTH = 0,
			UNDER_GROWTH = 1, 
			OVERGROWTH = 2,
			NO_GROWTH = 3; 
	private static USGDBHelper helper;
	private static double[] dates, weights;
	private static final float tow = TOW.getDefaultTOW();
	
	public static int analyzeGrowth(ArrayList<Photo> ps){
		Collections.sort(ps, new Photo.TimestampComparator());
		if(ps.size() < 2){
			return NORMAL_GROWTH;
		}
		
		// Check if no growth in the last photos
		getCurrentStats(ps);
		if(weights[weights.length-1] <= weights[weights.length -2]){
			return NO_GROWTH;
		}
		
		//Check if last node is normal or not;
		float lower = ProportionalityFunction.lowerTow(tow,(float) dates[dates.length-1]);
		float upper = ProportionalityFunction.upperTow(tow,(float) dates[dates.length-1]);
		if(weights[weights.length -1] <= lower) return UNDER_GROWTH;
		else if(weights[weights.length -1] >= upper) return OVERGROWTH;
		return NO_GROWTH;
		
	}
	
	private static void getCurrentStats(ArrayList<Photo> ps){
		helper = USGDBHelper.getInstance(A);
		helper.open();
		
		//Check if last growth still in normal range
		dates = new double[ps.size()];
		weights = new double[ps.size()];
		// calculate first week
		Cursor c = helper.getValidations(ps.get(0).getNoPhoto(),
				ps.get(0).getNoKandungan(), ps.get(0)
						.getIdPasien());
		ArrayList<Validation> vals = ValidationConverter.convertAll(c);
		
		long firstTime = ps.get(0).getCreateTimestampLong();
		float firstHC = Ellipse.keliling(vals.isEmpty() ? ps.get(0)
				.getA() : vals.get(0).getA(), vals.isEmpty() ? ps
				.get(0).getB() : vals.get(0).getB())
				* Ellipse.SCALING * 0.1f;
		float firstWeek = GestationalAge.approximateGA1(firstHC);
		float lastWeek = firstWeek;

		
		
		dates[0] = firstWeek;
		weights[0] = ProportionalityFunction.idealTow(tow, firstWeek);
		Log.d("First node ", "HC-Week-Weight-tow :" + firstHC + "-" + firstWeek
				+ "-" + weights[0]);
		for (int i = 1; i < ps.size(); i++) {
			c = helper.getValidations(ps.get(i).getNoPhoto(),
					ps.get(i).getNoKandungan(), ps.get(i)
							.getIdPasien());
			vals = ValidationConverter.convertAll(c);
			float hc = Ellipse.keliling(vals.isEmpty() ? ps.get(i).getA() : vals.get(0).getA(),
					vals.isEmpty()? ps.get(i).getB() : vals.get(0).getB()) * Ellipse.SCALING * 0.1f;
			float deltaTime = Math.abs(ps.get(i)
					.getCreateTimestampLong()
					- ps.get(i - 1).getCreateTimestampLong());
			lastWeek = (deltaTime < DateUtils.ONE_WEEK) ? lastWeek + 1
					: deltaTime / DateUtils.ONE_WEEK;

			dates[i] = lastWeek;
			weights[i] = ProportionalityFunction.idealTow(tow,
					GestationalAge.approximateGA1(hc));
			Log.d("node " + i, "HC-Week-Weight :" + hc + "-" + lastWeek + "-"
					+ weights[i]);
		}
	}

}

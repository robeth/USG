package id.fruity.usg;

import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.experiment.USGConstant;
import id.fruity.usg.experiment.USGSet;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Ellipse;

import java.io.InputStream;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class ExperimentActivity extends SherlockActivity {
	private Bitmap sourceBitmap, tempBitmap;
	private Mat m1;
	private ImageView im;
	int i = 9;
	private static final int[] ids = { R.drawable.im13, R.drawable.im14,
			R.drawable.im15, R.drawable.im16, R.drawable.im17, R.drawable.im18,
			R.drawable.im20, R.drawable.im21, R.drawable.im22, R.drawable.im23,
			R.drawable.im24, R.drawable.im25, R.drawable.im26, R.drawable.im27,
			R.drawable.im28, R.drawable.im29, R.drawable.im30, R.drawable.im31,
			R.drawable.im33, R.drawable.im34, R.drawable.im35, R.drawable.im37,
			R.drawable.im38, R.drawable.im40, R.drawable.im41, R.drawable.im42,
			R.drawable.im43, R.drawable.im44, R.drawable.im45, R.drawable.im46,
			R.drawable.im47, R.drawable.im48, R.drawable.im49, R.drawable.im50,
			R.drawable.im52, R.drawable.im54, R.drawable.im55, R.drawable.im56,
			R.drawable.im58, R.drawable.im59 };
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				System.loadLibrary("mixed_sample");
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.experiment);
		im = (ImageView) findViewById(R.id.picture);
		// Configure action bar
		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		getSupportActionBar().setTitle("Experiment");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	USGConstant c;

	public void doMethod(int indexMethod) {
		i++;
		i = i % ids.length;
		c = USGSet.get(ids[i]);
		if (tempBitmap != null) {
			tempBitmap.recycle();
		}
		if (m1 != null) {
			m1.release();
		}

		m1 = new Mat();
		InputStream is = this.getResources().openRawResource(ids[i]);
		tempBitmap = BitmapFactory.decodeStream(is);
		Utils.bitmapToMat(tempBitmap, m1);
		float[] rs = null;
		if (indexMethod == 0) {
			rs = UsgActivity.RHT(m1.getNativeObjAddr());
		} else if (indexMethod == 1) {
			rs = UsgActivity.IRHT(m1.getNativeObjAddr());
		} else if (indexMethod == 2) {
			rs = UsgActivity.PSO(m1.getNativeObjAddr());
		}
		float circumference = (Ellipse.keliling(rs[2], rs[3]) * c.getRatio()) / 10;
		float biparietalDiameter = (rs[3] * 2 * c.getRatio()) / 10;
		Log.d("pictid-x-y-a-b-t-hc-bpd-rx-ry-rhc-rbpd-errX-errY-errHC-errBPD,time",
				c.getImageFilename() + ";" + rs[0] + ";" + rs[1] + ";" + rs[2] + ";" + rs[3] + ";"
						+ rs[4] + ";" + circumference + ";"
						+ biparietalDiameter + ";" + c.getX() + ";" + c.getY() + ";"
						+ c.getA() + ";"
						+ c.getB() + ";"
						+ c.getTetha() + ";"
						+ c.getHc() + ";" + c.getBpd() + ";"
						+ Math.abs(rs[0] - c.getX()) + ";"
						+ Math.abs(rs[1] - c.getY()) + ";"
						+ Math.abs(rs[2] - c.getA()) + ";"
						+ Math.abs(rs[3] - c.getB()) + ";"
						+ Math.abs(rs[4] - c.getTetha()) + ";"
						+ Math.abs(c.getHc() - circumference ) + ";"
						+ Math.abs(c.getBpd() - biparietalDiameter) + ";"
						+ rs[5]);
		Utils.matToBitmap(m1, tempBitmap);
		im.setImageBitmap(tempBitmap);
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_4, this,
				mLoaderCallback);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("RHT").setIcon(R.drawable.content_edit)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add("IRHT").setIcon(R.drawable.content_new_picture)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menu.add("PSO").setIcon(R.drawable.content_discard)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("RHT")) {
			doMethod(0);
		} else if (item.getTitle().equals("IRHT")) {
			doMethod(1);
		} else if (item.getTitle().equals("PSO")) {
			doMethod(2);
		} else if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}
}

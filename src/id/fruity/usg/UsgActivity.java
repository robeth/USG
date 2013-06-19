package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.PatientConverter;
import id.fruity.usg.database.converter.PhotoConverter;
import id.fruity.usg.database.converter.ValidationConverter;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.Validation;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Ellipse;
import id.fruity.usg.util.SDUtils;

import java.util.ArrayList;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class UsgActivity extends SherlockActivity {
	private Spinner methodSpinner, choiceSpinner;
	private String patientId;
	private int kandunganId;
	private int photoId;
	private boolean isDoctor;
	private boolean hasAutomated;
	private boolean hasContributed;
	private String userId;
	private ActionMode validateMode, automaticMode;
	private ArrayList<Validation> choices;
	private ValidationSpinnerAdapter choiceAdapter;

	private static final String TAG = "OCVSample::Activity";
	private Mat m1, m2;

	private int selectedMethod, selectedChoice, contributionPosition,
			automaticPosition;
	private Photo currentPhoto;
	private Validation validation;
	private USGDBHelper helper;

	private ImageView usgImage;
	private TextView hc, bpd, x, y, a, b, theta, scale;
	private Bitmap sourceBitmap, tempBitmap;
	private RelativeLayout basicContainer, seekContainer;
	private SeekBar seekX, seekY, seekA, seekB, seekT;
	private int currentX, currentY, currentA, currentB, currentT;
	private boolean isOpenCVLoaded = false;
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			Log.d(TAG, "OpenCV Onmanagerconnected");
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");
				System.loadLibrary("mixed_sample");
				isOpenCVLoaded = true;

				selectedChoice = hasContributed ? contributionPosition : 0;
				updateParameterBox(selectedChoice);
				markEllipse(selectedChoice);
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
		Bundle bb = getIntent().getExtras();
		patientId = bb.getString("patientId");
		kandunganId = bb.getInt("pregnancyId");
		photoId = bb.getInt("photoId");
		isDoctor = bb.getBoolean("isDoctor");
		userId = bb.getString("userId");
		helper = USGDBHelper.getInstance(this);
		helper.open();

		Cursor c = helper.getPhotos(patientId, kandunganId, photoId);
		currentPhoto = PhotoConverter.convert(c);
		Validation automaticValidation = new Validation("-1", true, true,
				82233213123L, 82233213123L, "dummy", -1, -1,
				currentPhoto.getMethod(), currentPhoto.getX(),
				currentPhoto.getY(), currentPhoto.getA(), currentPhoto.getB(),
				currentPhoto.getTheta(), false, -1, -1, "-1");

		c = helper.getPasien(patientId);
		Patient p = PatientConverter.convert(c);

		c = helper.getValidations(photoId, kandunganId, patientId);
		choices = ValidationConverter.convertAll(c);

		helper.close();

		hasAutomated = (currentPhoto.getA() > -1 && currentPhoto.getB() > -1
				&& currentPhoto.getX() > -1 && currentPhoto.getY() > -1);

		hasContributed = false;
		for (int i = 0; i < choices.size(); i++) {
			if (choices.get(i).getIdDokter().equals(userId)) {
				hasContributed = true;
				contributionPosition = i;
			}
		}
		choices.add(automaticValidation);
		automaticPosition = choices.size() - 1;

		setContentView(R.layout.usg);
		hc = (TextView) findViewById(R.id.usg_hc_value);
		bpd = (TextView) findViewById(R.id.usg_bpd_value);
		scale = (TextView) findViewById(R.id.usg_scale_value);
		a = (TextView) findViewById(R.id.usg_params_a_value);
		b = (TextView) findViewById(R.id.usg_params_b_value);
		x = (TextView) findViewById(R.id.usg_params_x_value);
		y = (TextView) findViewById(R.id.usg_params_y_value);
		theta = (TextView) findViewById(R.id.usg_params_t_value);
		methodSpinner = (Spinner) findViewById(R.id.usg_methods);
		choiceSpinner = (Spinner) findViewById(R.id.choice);
		methodSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selectedMethod = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		choiceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				selectedChoice = position;
				updateParameterBox(position);
				markEllipse(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		usgImage = (ImageView) findViewById(R.id.usg_photo);
		basicContainer = (RelativeLayout) findViewById(R.id.basic_container);
		seekContainer = (RelativeLayout) findViewById(R.id.seekbar_container);
		seekX = (SeekBar) findViewById(R.id.seekbar_x);
		seekY = (SeekBar) findViewById(R.id.seekbar_y);
		seekA = (SeekBar) findViewById(R.id.seekbar_a);
		seekB = (SeekBar) findViewById(R.id.seekbar_b);
		seekT = (SeekBar) findViewById(R.id.seekbar_t);
		currentX = currentY = currentA = currentB = currentT = 0;

		seekX.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (isOpenCVLoaded) {
					currentX = progress;
					markEllipseValidation();
				}
			}
		});

		seekY.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (isOpenCVLoaded) {
					currentY = progress;
					markEllipseValidation();
				}
			}
		});

		seekA.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (isOpenCVLoaded) {
					currentA = progress;
					markEllipseValidation();
				}
			}
		});

		seekB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (isOpenCVLoaded) {
					currentB = progress;
					markEllipseValidation();
				}
			}
		});

		seekT.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if (isOpenCVLoaded) {
					currentT = progress;
					markEllipseValidation();
				}
			}
		});

		// Drawable usgPhoto =
		// SDUtils.getPhotoDrawable(currentPhoto.getFilename());
		Drawable usgPhoto = SDUtils.getDrawable(this, SDUtils
				.getBigPhotoBitmap(SDUtils.getAbsolutePath(currentPhoto
						.getFilename())));
		Log.d("New Image!!", "Status:"+(usgPhoto != null));
		if (usgPhoto != null) {
			usgImage.setImageDrawable(usgPhoto);
		}
		sourceBitmap = ((BitmapDrawable) usgImage.getDrawable()).getBitmap();
		seekX.setMax(sourceBitmap.getWidth());
		seekY.setMax(sourceBitmap.getHeight());
		seekA.setMax(seekX.getMax());
		seekB.setMax(seekX.getMax());
		seekT.setMax(180);

		// Configure action bar
		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		Drawable patientPhoto = SDUtils.getDrawable(this,
				SDUtils.getSmallPhotoBitmap(p.getFilename()));
		if (patientPhoto != null) {
			getSupportActionBar().setIcon(patientPhoto);
		} else {
			getSupportActionBar().setIcon(R.drawable.content_picture);
		}
		getSupportActionBar().setTitle(p.getName());
		getSupportActionBar().setSubtitle(
				DateUtils.getStringOfCalendarFromLong(currentPhoto
						.getCreateTimestampLong()));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		methodSpinner.setSelection(getCurrentMethod(currentPhoto.getMethod()));
		methodSpinner.setVisibility(View.GONE);
		choiceAdapter = new ValidationSpinnerAdapter(choices,
				getLayoutInflater());
		choiceSpinner.setAdapter(choiceAdapter);
		seekContainer.setVisibility(View.GONE);
		basicContainer.setVisibility(View.VISIBLE);
		updateParameterBox(choiceSpinner.getSelectedItemPosition());
	}

	private void updateParameterBox(int position) {
		Validation v = choices.get(position);
		hc.setText(Ellipse.keliling(v.getA(), v.getB()) + " cm");
		bpd.setText(Ellipse.area(v.getA(), v.getB()) + " cm");
		a.setText(v.getA() + "");
		b.setText(v.getB() + "");
		x.setText(v.getX() + "");
		y.setText(v.getY() + "");
		theta.setText(v.getTheta() + "");
		scale.setText(currentPhoto.getScale() + "");
	}

	private int getCurrentMethod(String method) {
		int res = 0;
		if (method.equals("RHT")) {
			res = 0;
		} else if (method.equals("IRHT")) {
			res = 1;
		}
		return res;
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_4, this,
				mLoaderCallback);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isDoctor || choices.size() == 1) {
			menu.add("Edit").setIcon(R.drawable.content_edit)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("Edit") && isDoctor) {
			validateMode = startActionMode(new ValidationActionMode());
		} else if (item.getTitle().equals("Edit") && !isDoctor) {
			automaticMode = startActionMode(new AutomaticActionMode());
		} else if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}

	private void approxEllipse() {
		recycleTempBitmap();
		if (m1 != null) {
			m1.release();
		}
		m1 = new Mat();
		Utils.bitmapToMat(tempBitmap, m1);

		float[] rs = null;
		if (selectedMethod == 0) {
			rs = RHT(m1.getNativeObjAddr(), 10000);
		} else if (selectedMethod == 1) {
			rs = IRHT(m1.getNativeObjAddr(), 10000, 100, 50);
		}

		Log.d("Result", "length" + rs.length + "[" + rs[0] + "," + rs[1] + ","
				+ rs[2] + "," + rs[3] + "," + rs[4] + "," + rs[5] + "]");

		// Bitmap bmp = Bitmap.createBitmap(m1.cols(), m1.rows(),
		// Bitmap.Config.ARGB_8888);

		currentPhoto.setX(rs[0]);
		currentPhoto.setY(rs[1]);
		currentPhoto.setA(rs[2]);
		currentPhoto.setB(rs[3]);
		currentPhoto.setTheta(rs[4]);
		currentPhoto.setMethod((String) methodSpinner
				.getItemAtPosition(selectedMethod));
		currentPhoto.setAutoDateLong(DateUtils.getCurrentLong());
		currentPhoto.setDirty(true);
		currentPhoto.setModifyTimestampLong(DateUtils.getCurrentLong());

		Validation v = choices.get(automaticPosition);
		v.setX(currentPhoto.getX());
		v.setY(currentPhoto.getY());
		v.setA(currentPhoto.getA());
		v.setB(currentPhoto.getB());
		v.setTheta(currentPhoto.getTheta());
		v.setNoPasien(currentPhoto.getMethod());
		updateParameterBox(automaticPosition);

		helper.open();
		helper.updatePhoto(currentPhoto);
		helper.close();

		Utils.matToBitmap(m1, tempBitmap);
		usgImage.setImageBitmap(tempBitmap);

	}

	private void markEllipse(int position) {
		if (!isOpenCVLoaded)
			return;
		Validation v = choices.get(position);
		float a = v.getA();
		float b = v.getB();
		float theta = v.getTheta();
		float x = v.getX();
		float y = v.getY();
		if (a > -1 && b > -1 && x > -1 && y > -1 && theta > -1) {
			recycleTempBitmap();
			if (m1 != null) {
				m1.release();
			}
			m1 = new Mat();
			Utils.bitmapToMat(tempBitmap, m1);
			Mark(m1.getNativeObjAddr(), a, b, x, y, theta);
			Utils.matToBitmap(m1, tempBitmap);
			usgImage.setImageBitmap(tempBitmap);
		} else {
			Toast.makeText(this, "The elips parameter is not valid",
					Toast.LENGTH_SHORT).show();
			recycleTempBitmap();
			usgImage.setImageBitmap(tempBitmap);
		}
	}

	private void markEllipseValidation() {
		recycleTempBitmap();
		if (m1 != null) {
			m1.release();
		}
		m1 = new Mat();
		Utils.bitmapToMat(tempBitmap, m1);
		Mark(m1.getNativeObjAddr(), currentA, currentB, currentX, currentY,
				currentT);
		Utils.matToBitmap(m1, tempBitmap);
		usgImage.setImageBitmap(tempBitmap);
	}

	private void recycleTempBitmap() {
		if (tempBitmap != null) {
			tempBitmap.recycle();
		}
		tempBitmap = sourceBitmap.copy(sourceBitmap.getConfig(), true);

	}

	public native float[] RHT(long matAddrGr, int sample);

	public native float[] IRHT(long matAddrGr, int sample, int interval,
			float delta);

	public native void Mark(long matAddrrGr, float a, float b, float x,
			float y, float theta);

	private final class ValidationActionMode implements ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			choiceSpinner.setVisibility(View.GONE);
			basicContainer.setVisibility(View.GONE);
			seekContainer.setVisibility(View.VISIBLE);
			if (!hasContributed) {
				seekX.setProgress((int) (seekX.getMax() * 0.5));
				seekY.setProgress((int) (seekY.getMax() * 0.5));
				seekA.setProgress((int) (seekA.getMax() * 0.5));
				seekB.setProgress((int) (seekB.getMax() * 0.5));
			} else {
				Validation v = choices.get(contributionPosition);
				seekX.setProgress((int) (v.getX()));
				seekY.setProgress((int) (v.getY()));
				seekA.setProgress((int) (v.getA()));
				seekB.setProgress((int) (v.getB()));
				seekT.setProgress((int) (v.getTheta()));
			}
			menu.add("Accept")
					.setIcon(R.drawable.navigation_accept)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Toast.makeText(UsgActivity.this, "Validation saved!",
					Toast.LENGTH_SHORT).show();
			Long currentTime = DateUtils.getCurrentLong();
			if (!hasContributed) {
				Validation validation = new Validation("-1", true, true,
						currentTime, currentTime, userId,
						currentPhoto.getNoPhoto(),
						currentPhoto.getNoKandungan(),
						currentPhoto.getIdPasien(), seekX.getProgress(),
						seekY.getProgress(), seekA.getProgress(),
						seekB.getProgress(), seekT.getProgress(), false,
						currentPhoto.getServerId(), -1, "-1");
				helper.open();
				helper.insertValidation(validation);
				helper.close();

				choices.add(validation);
				hasContributed = true;
				contributionPosition = choices.size() - 1;
				validateMode.finish();

				choiceAdapter.notifyDataSetChanged();
				choiceSpinner.setSelection(contributionPosition);
				updateParameterBox(contributionPosition);

			} else {
				Validation v = choices.get(contributionPosition);
				v.setX(seekX.getProgress());
				v.setY(seekY.getProgress());
				v.setA(seekA.getProgress());
				v.setB(seekB.getProgress());
				v.setTheta(seekT.getProgress());
				v.setModifyTimestampLong(currentTime);
				v.setDirty(true);
				helper.open();
				helper.updateValidation(v);
				helper.close();
				validateMode.finish();

				choiceAdapter.notifyDataSetChanged();
				choiceSpinner.setSelection(contributionPosition);
			}
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			choiceSpinner.setVisibility(View.VISIBLE);
			seekContainer.setVisibility(View.GONE);
			basicContainer.setVisibility(View.VISIBLE);
		}
	}

	private final class AutomaticActionMode implements ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			choiceSpinner.setVisibility(View.GONE);
			methodSpinner.setVisibility(View.VISIBLE);
			menu.add("Accept")
					.setIcon(R.drawable.navigation_accept)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_ALWAYS
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Log.d("AutomaticMode", (String) item.getTitle());
			approxEllipse();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			choiceSpinner.setVisibility(View.VISIBLE);
			methodSpinner.setVisibility(View.GONE);
		}
	}
}

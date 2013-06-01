package id.fruity.usg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;

public class AddPhotoActivity extends SherlockActivity {
	private ImageView i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13,
			i14, i15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chose_photo);
		i1 = (ImageView) findViewById(R.id.usg_sample1);
		i2 = (ImageView) findViewById(R.id.usg_sample2);
		i3 = (ImageView) findViewById(R.id.usg_sample3);
		i4 = (ImageView) findViewById(R.id.usg_sample4);
		i5 = (ImageView) findViewById(R.id.usg_sample5);

		i6 = (ImageView) findViewById(R.id.usg_sample6);
		i7 = (ImageView) findViewById(R.id.usg_sample7);
		i8 = (ImageView) findViewById(R.id.usg_sample8);
		i9 = (ImageView) findViewById(R.id.usg_sample9);
		i10 = (ImageView) findViewById(R.id.usg_sample10);

		i11 = (ImageView) findViewById(R.id.usg_sample11);
		i12 = (ImageView) findViewById(R.id.usg_sample12);
		i13 = (ImageView) findViewById(R.id.usg_sample13);
		i14 = (ImageView) findViewById(R.id.usg_sample14);
		i15 = (ImageView) findViewById(R.id.usg_sample15);

		i1.setOnClickListener(new OnSampleImageClickListener(1));
		i2.setOnClickListener(new OnSampleImageClickListener(2));
		i3.setOnClickListener(new OnSampleImageClickListener(3));
		i4.setOnClickListener(new OnSampleImageClickListener(4));
		i5.setOnClickListener(new OnSampleImageClickListener(5));

		i6.setOnClickListener(new OnSampleImageClickListener(6));
		i7.setOnClickListener(new OnSampleImageClickListener(7));
		i8.setOnClickListener(new OnSampleImageClickListener(8));
		i9.setOnClickListener(new OnSampleImageClickListener(9));
		i10.setOnClickListener(new OnSampleImageClickListener(10));

		i11.setOnClickListener(new OnSampleImageClickListener(11));
		i12.setOnClickListener(new OnSampleImageClickListener(12));
		i13.setOnClickListener(new OnSampleImageClickListener(13));
		i14.setOnClickListener(new OnSampleImageClickListener(14));
		i15.setOnClickListener(new OnSampleImageClickListener(15));
	}

	private class OnSampleImageClickListener implements OnClickListener {
		private int imageId;

		public OnSampleImageClickListener(int imageId) {
			this.imageId = imageId;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			popUpSureDialog(imageId);
		}

	}

	private int getResId(final int imageId) {
		int resId = 0;
		switch (imageId) {
		case 1:
			resId = R.drawable.sample1;
			break;
		case 2:
			resId = R.drawable.sample2;
			break;
		case 3:
			resId = R.drawable.sample3;
			break;
		case 4:
			resId = R.drawable.sample4;
			break;
		case 5:
			resId = R.drawable.sample5;
			break;

		case 6:
			resId = R.drawable.sample6;
			break;
		case 7:
			resId = R.drawable.sample7;
			break;
		case 8:
			resId = R.drawable.sample8;
			break;
		case 9:
			resId = R.drawable.sample9;
			break;
		case 10:
			resId = R.drawable.sample10;
			break;

		case 11:
			resId = R.drawable.sample11;
			break;
		case 12:
			resId = R.drawable.sample12;
			break;
		case 13:
			resId = R.drawable.sample13;
			break;
		case 14:
			resId = R.drawable.sample14;
			break;
		case 15:
			resId = R.drawable.sample15;
			break;
		}
		return resId;
	}

	private void popUpSureDialog(final int imageId) {
		AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.setTitle("Selected Image");
		dialog.setMessage("Image id: " + imageId);
		dialog.setCancelable(true);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {
						Log.d("Add Photo Activity", "yes confirmed!");
						Intent i = new Intent();
						Bundle b = new Bundle();
						b.putInt("imageId", imageId);
						b.putInt("imageIdRes", getResId(imageId));
						i.putExtras(b);
						setResult(RESULT_OK, i);
						AddPhotoActivity.this.finish();
					}
				});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

					}
				});
		dialog.setIcon(android.R.drawable.ic_dialog_alert);
		dialog.show();
	}
}

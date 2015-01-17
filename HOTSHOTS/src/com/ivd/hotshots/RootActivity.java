package com.ivd.hotshots;

import java.lang.reflect.Type;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.ivd.http.RestController;
import com.ivd.http.RestResponse.StatusCode;
import com.ivd.http.UiUpdator;
import com.ivd.http.request.GeneralRequest;

public abstract class RootActivity extends Activity implements UiUpdator{

	private ProgressDialog progressDialog;
	public RestController controller;

	public void showProgressDialog(){
		progressDialog = new ProgressDialog(this);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.cancel();
			}
		});
		progressDialog.show();
	}

	public void hideProgressDialog(){
		if(progressDialog != null){
			progressDialog.cancel();
			progressDialog.dismiss();
		}
	}

	public void sendRequest(int requestCode, Object obj, Type outputType){
		if(controller == null){
			controller = RestController.getInstance();
		}

		GeneralRequest request = new GeneralRequest(requestCode, obj);
		controller.sendRequest(this, requestCode, request, outputType);
	}


	@Override
	public void updateUI(int requestCode, StatusCode statusCode,
			int responseCode, Type data) {

	}
}

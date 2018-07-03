package com.jain.tavish.emojifyme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

class Emojifier {

    /**
     * Method for detecting faces in a bitmap.
     *
     * @param context The application context.
     * @param picture The picture in which to detect the faces.
     */
    static void detectFaces(Context context, Bitmap picture) {

        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Frame frame = new Frame.Builder().setBitmap(picture).build();

        SparseArray<Face> faces = detector.detect(frame);

        Log.e("tavish", "Detected " + faces.size() + " faces");

        for (int index = 0; index < faces.size() ; index++) {
            Face face = faces.valueAt(index);
            getClassifications(context, face);
        }

        detector.release();
    }

    static void getClassifications(final Context context, final Face face){

        final TextView smileTextView = ((Activity)context).findViewById(R.id.tv_smile);
        final TextView leftEyeTextView = ((Activity)context).findViewById(R.id.tv_left_eye);
        final TextView rightEyeTextView = ((Activity)context).findViewById(R.id.tv_right_eye);

     //   ((Activity)context).runOnUiThread(new Runnable() {
      //      @Override
       //     public void run() {
                Toast.makeText(context, "d", Toast.LENGTH_SHORT).show();
                smileTextView.setText( "Smile Probability :" + face.getIsSmilingProbability());
                rightEyeTextView.setText( "Left Eye open :" + face.getIsRightEyeOpenProbability());
                leftEyeTextView.setText( "Right Eye open :" + face.getIsLeftEyeOpenProbability());
         //   }
       // });

        Log.e("tavish", "Right Eye open" + face.getIsRightEyeOpenProbability());
        Log.e("tavish", "Left Eye open :" + face.getIsLeftEyeOpenProbability());
        Log.e("tavish", "Smile probability :" + face.getIsSmilingProbability());
    }

}
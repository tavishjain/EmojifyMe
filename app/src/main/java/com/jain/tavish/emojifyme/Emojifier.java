package com.jain.tavish.emojifyme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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

    public static final double SMILE_PROB_THRESHOLD = 0.25;
    public static final double EYE_OPEN_PROB_THRESHOLD = 0.50;

    static void detectFaces(Context context, Bitmap picture) {

        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Frame frame = new Frame.Builder().setBitmap(picture).build();

        SparseArray<Face> faces = detector.detect(frame);

        Toast.makeText(context, "Detected " + faces.size() + " faces", Toast.LENGTH_SHORT).show();

        for (int index = 0; index < faces.size() ; index++) {
            Face face = faces.valueAt(index);
            whichEmoji(context, face);
        }

        detector.release();
    }

    static void whichEmoji(final Context context, final Face face){

        boolean smiling = (face.getIsSmilingProbability() > SMILE_PROB_THRESHOLD);
        boolean right_eye_open = (face.getIsRightEyeOpenProbability() > EYE_OPEN_PROB_THRESHOLD);
        boolean left_eye_open = (face.getIsLeftEyeOpenProbability() > EYE_OPEN_PROB_THRESHOLD);

        TextView smileTextView = ((Activity)context).findViewById(R.id.tv_smile);
        TextView leftEyeTextView = ((Activity)context).findViewById(R.id.tv_left_eye);
        TextView rightEyeTextView = ((Activity)context).findViewById(R.id.tv_right_eye);

        smileTextView.setText( "Smile Probability :" + face.getIsSmilingProbability());
        rightEyeTextView.setText( "Left Eye open :" + face.getIsRightEyeOpenProbability());
        leftEyeTextView.setText( "Right Eye open :" + face.getIsLeftEyeOpenProbability());

        Emoji emoji = null;
        if(smiling){
            if(right_eye_open){
                if(left_eye_open){
                    emoji = Emoji.SMILE;
                }else if(!left_eye_open){
                    emoji = Emoji.LEFT_WINK;
                }
            }else if(!right_eye_open){
                if(left_eye_open){
                    emoji = Emoji.RIGHT_WINK;
                }else if(!left_eye_open){
                    emoji = Emoji.CLOSED_EYE_SMILE;
                }
            }
        }else if(!smiling){
            if(right_eye_open){
                if(left_eye_open){
                    emoji = Emoji.FROWN;
                }else if(!left_eye_open){
                    emoji = Emoji.LEFT_WINK_FROWN;
                }
            }else if(!right_eye_open){
                if(left_eye_open){
                    emoji = Emoji.RIGHT_WINK_FROWN;
                }else if(!left_eye_open){
                    emoji = Emoji.CLOSED_EYE_FROWN;
                }
            }
        }

        Toast.makeText(context, "" + emoji.name(), Toast.LENGTH_SHORT).show();

    }

    private enum Emoji {
        SMILE,
        FROWN,
        LEFT_WINK,
        RIGHT_WINK,
        LEFT_WINK_FROWN,
        RIGHT_WINK_FROWN,
        CLOSED_EYE_SMILE,
        CLOSED_EYE_FROWN
    }

}
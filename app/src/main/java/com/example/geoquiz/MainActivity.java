package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActiviti";
    private static final String KEY_INDEX = "index";
    private static int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_ocean, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
//    int question = mQuestionBank[mCurrentIndex].getTextResId();
//    mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//            Toast.makeText(MainActivity.this,
//                    R.string.correct_toast,
//                    Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//            Toast.makeText(MainActivity.this,
//                    R.string.incorrect_toast,
//                    Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
//            int question = mQuestionBank[mCurrentIndex].getTextResId();
//            mQuestionTextView.setText(question);
                mIsCheater = false;
                updateQuestion();
            }
        });
        updateQuestion();

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this,answerIsTrue);
//                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }
    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
    @Override
        public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
        }

    @Override
        public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
        }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
         }
    @Override
    public void onSaveInstanceState(Bundle savedInstancesState){
        super.onSaveInstanceState(savedInstancesState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstancesState.putInt(KEY_INDEX,mCurrentIndex);
         }
    @Override
        public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
        }

    @Override
        public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
         }

        private void updateQuestion(){
//            Log.d(TAG,"Updating question text", new Exception());
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);
           }

        private void checkAnswer(boolean userPressedTrue){
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
            int messegeResId = 0;
            if (mIsCheater){
                messegeResId = R.string.judgment_toast;
            }else {
            if (userPressedTrue == answerIsTrue) {
                messegeResId = R.string.correct_toast;
            } else {
                messegeResId = R.string.incorrect_toast;
            }
            Toast.makeText(this,messegeResId,Toast.LENGTH_SHORT)
                    .show();
        }
    }
}

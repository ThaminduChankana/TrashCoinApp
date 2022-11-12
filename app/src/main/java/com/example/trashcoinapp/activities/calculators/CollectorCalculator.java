package com.example.trashcoinapp.activities.calculators;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trashcoinapp.R;
import com.example.trashcoinapp.activities.chat.Chat;
import com.example.trashcoinapp.activities.inventory.InventoryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class CollectorCalculator extends AppCompatActivity implements View.OnClickListener{

        TextView resultTv,solutionTv;
        MaterialButton buttonC,buttonBrackOpen,buttonBrackClose;
        MaterialButton buttonDivide,buttonMultiply,buttonPlus,buttonMinus,buttonEquals;
        MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
        MaterialButton buttonAC,buttonDot;
        ImageView img_calculator_back;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_collector_calculator);
            getSupportActionBar().hide();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            resultTv = findViewById(R.id.result_tv);
            solutionTv = findViewById(R.id.solution_tv);
            img_calculator_back = findViewById(R.id.img_calculator_back);

            assignId(buttonC,R.id.button_c);
            assignId(buttonBrackOpen,R.id.button_open_bracket);
            assignId(buttonBrackClose,R.id.button_close_bracket);
            assignId(buttonDivide,R.id.button_divide);
            assignId(buttonMultiply,R.id.button_multiply);
            assignId(buttonPlus,R.id.button_plus);
            assignId(buttonMinus,R.id.button_minus);
            assignId(buttonEquals,R.id.button_equals);
            assignId(button0,R.id.button_0);
            assignId(button1,R.id.button_1);
            assignId(button2,R.id.button_2);
            assignId(button3,R.id.button_3);
            assignId(button4,R.id.button_4);
            assignId(button5,R.id.button_5);
            assignId(button6,R.id.button_6);
            assignId(button7,R.id.button_7);
            assignId(button8,R.id.button_8);
            assignId(button9,R.id.button_9);
            assignId(buttonAC,R.id.button_ac);
            assignId(buttonDot,R.id.button_dot);

            img_calculator_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_collector);
            bottomNavigationView.setSelectedItemId(R.id.img_collector_home);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.img_collector_home:
                            return true;
//                    case R.id.img_collector_disposers:
//                        startActivity(new Intent(getApplicationContext(), CollectorsForDisposers.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//                    case R.id.img_collector_recyclers:
//                        startActivity(new Intent(getApplicationContext(), ProductViewActivity.class));
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
                        case R.id.img_collector_inventory:
                            startActivity(new Intent(getApplicationContext(), InventoryActivity.class));
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                        case R.id.img_collector_chat:
                            startActivity(new Intent(getApplicationContext(), Chat.class));
                            overridePendingTransition(0, 0);
                            finish();
                            return true;
                    }

                    return false;
                }
            });

        }


        void assignId(MaterialButton btn,int id){
            btn = findViewById(id);
            btn.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            MaterialButton button =(MaterialButton) view;
            String buttonText = button.getText().toString();
            String dataToCalculate = solutionTv.getText().toString();

            if(buttonText.equals("AC")){
                solutionTv.setText("");
                resultTv.setText("0");
                return;
            }
            if(buttonText.equals("=")){
                solutionTv.setText(resultTv.getText());
                return;
            }
            if(buttonText.equals("C")){
                dataToCalculate = dataToCalculate.substring(0,dataToCalculate.length()-1);
            }else{
                dataToCalculate = dataToCalculate+buttonText;
            }
            solutionTv.setText(dataToCalculate);

            String finalResult = getResult(dataToCalculate);

            if(!finalResult.equals("Err")){
                resultTv.setText(finalResult);
            }



        }

        String getResult(String data){
            try{
                Context context  = Context.enter();
                context.setOptimizationLevel(-1);
                Scriptable scriptable = context.initStandardObjects();
                String finalResult =  context.evaluateString(scriptable,data,"Javascript",1,null).toString();
                if(finalResult.endsWith(".0")){
                    finalResult = finalResult.replace(".0","");
                }
                return finalResult;
            }catch (Exception e){
                return "Err";
            }
        }

    public void dashboardBack(){
        onBackPressed();
    }
}
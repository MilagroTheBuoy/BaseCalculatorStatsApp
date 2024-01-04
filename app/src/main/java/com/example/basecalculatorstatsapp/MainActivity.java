package com.example.basecalculatorstatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public TextView txtInput;
    public TextView txtOutput;
    boolean wasDeletePressed = false;
    String answer = "";
    Button btnZero, btnOne, btnTwo, btnThree, btnDecimal, btnEqual;
    Button btnFour, btnFive, btnSix, btnSeven, btnAdd, btnSubtract;
    Button btnEight, btnNine, btnTen, btnEleven, btnMultiply, btnDivide;
    Button btnTwelve, btnThirteen, btnFourteen, btnFifteen, btnDelete, btnClear;
    Button btnBracketLeft, btnBracketRight, btnPower, btnPercentage, btnPosNeg, btnAnswer;
    Spinner spnCalculatorMode;
    String mode = "Decimal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInput = findViewById(R.id.txtInput);
        txtOutput = findViewById(R.id.txtOutput);

        btnZero = findViewById(R.id.btnZero);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnDecimal = findViewById(R.id.btnDecimal);
        btnEqual = findViewById(R.id.btnEqual);

        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);

        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        btnTen = findViewById(R.id.btnTen);
        btnEleven = findViewById(R.id.btnEleven);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);

        btnTwelve = findViewById(R.id.btnTwelve);
        btnThirteen = findViewById(R.id.btnThirteen);
        btnFourteen = findViewById(R.id.btnFourteen);
        btnFifteen = findViewById(R.id.btnFifteen);
        btnDelete = findViewById(R.id.btnDelete);
        btnClear = findViewById(R.id.btnClear);

        btnBracketLeft = findViewById(R.id.btnBracketLeft);
        btnBracketRight = findViewById(R.id.btnBracketRight);
        btnPower = findViewById(R.id.btnPower);
        btnPercentage = findViewById(R.id.btnPercentage);
        btnPosNeg = findViewById(R.id.btnPosNeg);
        btnAnswer = findViewById(R.id.btnAnswer);

        btnZero.setOnClickListener(view -> editInput(btnZero.getText().toString()));
        btnOne.setOnClickListener(view -> editInput(btnOne.getText().toString()));
        btnTwo.setOnClickListener(view -> editInput(btnTwo.getText().toString()));
        btnThree.setOnClickListener(view -> editInput(btnThree.getText().toString()));
        btnDecimal.setOnClickListener(view -> editInput(btnDecimal.getText().toString()));

        btnFour.setOnClickListener(view -> editInput(btnFour.getText().toString()));
        btnFive.setOnClickListener(view -> editInput(btnFive.getText().toString()));
        btnSix.setOnClickListener(view -> editInput(btnSix.getText().toString()));
        btnSeven.setOnClickListener(view -> editInput(btnSeven.getText().toString()));
        btnAdd.setOnClickListener(view -> editInput(btnAdd.getText().toString()));
        btnSubtract.setOnClickListener(view -> editInput(btnSubtract.getText().toString()));

        btnEight.setOnClickListener(view -> editInput(btnEight.getText().toString()));
        btnNine.setOnClickListener(view -> editInput(btnNine.getText().toString()));
        btnTen.setOnClickListener(view -> editInput(btnTen.getText().toString()));
        btnEleven.setOnClickListener(view -> editInput(btnEleven.getText().toString()));
        btnMultiply.setOnClickListener(view -> editInput(btnMultiply.getText().toString()));
        btnDivide.setOnClickListener(view -> editInput(btnDivide.getText().toString()));

        btnTwelve.setOnClickListener(view -> editInput(btnTwelve.getText().toString()));
        btnThirteen.setOnClickListener(view -> editInput(btnThirteen.getText().toString()));
        btnFourteen.setOnClickListener(view -> editInput(btnFourteen.getText().toString()));
        btnFifteen.setOnClickListener(view -> editInput(btnFifteen.getText().toString()));

        btnBracketLeft.setOnClickListener(view -> editInput(btnBracketLeft.getText().toString()));
        btnBracketRight.setOnClickListener(view -> editInput(btnBracketRight.getText().toString()));
        btnPower.setOnClickListener(view -> editInput(btnPower.getText().toString()));
        //btnPercentage.setOnClickListener(view -> editInput(btnPercentage.getText().toString()));
        //btnPosNeg.setOnClickListener(view -> editInput(btnPosNeg.getText().toString()));

        btnPercentage.setEnabled(false);
        btnPosNeg.setEnabled(false);
        btnTen.setEnabled(false);
        btnEleven.setEnabled(false);
        btnTwelve.setEnabled(false);
        btnThirteen.setEnabled(false);
        btnFourteen.setEnabled(false);
        btnFifteen.setEnabled(false);

        btnDelete.setOnClickListener(view -> deleteInput(txtInput.getText().toString()));
        btnClear.setOnClickListener(view -> clearAllTextViews());

        btnEqual.setOnClickListener(view -> calculateResult());

        btnAnswer.setOnClickListener(view -> {
            if(txtOutput.getText().toString().equals("")){
                if(answer.equals("")){
                    editInput("0");
                }
                else{
                    editInput(answer);
                }
            }
            else{
                answer = txtOutput.getText().toString();
                editInput(answer);
            }
        });

        spnCalculatorMode = findViewById(R.id.spnCalculatorMode);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.modes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCalculatorMode.setAdapter(adapter);
        spnCalculatorMode.setOnItemSelectedListener(this);

    }

    private void clearAllTextViews(){
        txtInput.setText("");
        txtOutput.setText("");
    }

    /*This takes the equation from the input textview, sends the equation to a Calculator object,
    and returns and outputs the result. It doesn't check whether the input is valid (yet)*/
    private void calculateResult() {
        String equation = txtInput.getText().toString();
        String answer;
        try {
            DecimalCalculator decimalCalculator = new DecimalCalculator();
            if(mode.equals("Binary")){
                answer = decimalCalculator.giveAnswer(equation, 2);
            }
            else if(mode.equals("Decimal")){
                answer = decimalCalculator.giveAnswer(equation, 10);
            }
            else{
                answer = decimalCalculator.giveAnswer(equation, 16);
            }
            txtOutput.setText(answer);
        }
        catch(Exception e){
            System.out.println("it worked");
            Toast.makeText(this, "Invalid Syntax", Toast.LENGTH_SHORT).show();
        }

    }

    /*public boolean validEquation(String eqn){
        if(!(Character.isAlphabetic(eqn.charAt(0)) || Character.isDigit(eqn.charAt(0)) || eqn.charAt(0) =='(')){
            return false;
        }
        else if(!(Character.isAlphabetic(eqn.charAt(eqn.length() - 1))
                || Character.isDigit(eqn.charAt(eqn.length() - 1)) || eqn.charAt(eqn.length() - 1) == ')')){
            return false;
        }
        //checks whether the number of left brackets is the same as the number of right brackets
        int leftRightDifference = 0;
        for(int i = 0; i < eqn.length(); i++){
            if(eqn.charAt(i) == '('){
                leftRightDifference++;            }
            else if(eqn.charAt(i) ==')'){
                leftRightDifference--;
            }
        }
        if(!(leftRightDifference == 0)){
            return false;
        }
        else{
            //checks whether two operation symbols are next to each other, eg. +-. -*
            for(int i = 0; i < eqn.length() - 1; i++){
                for (int j = i+1; j < i+2; j++){
                    if((eqn.charAt(i) == '+' || eqn.charAt(i) == '-' || eqn.charAt(i) == '*'
                            || eqn.charAt(i) == '/' || eqn.charAt(i) == '^') &&
                            (eqn.charAt(j) == '+' || eqn.charAt(j) == '-' || eqn.charAt(j) == '*'
                            || eqn.charAt(j) == '/' || eqn.charAt(j) == '^')){
                        return false;
                    }
                }
            }
        }
        return true;
    }*/

    public void editInput(String btnString){
        if(!txtOutput.getText().toString().equals("")){
            answer = txtOutput.getText().toString();
            if(!wasDeletePressed){
                clearAllTextViews();
            }
            else{
                wasDeletePressed = false;
            }
        }
        String s = txtInput.getText().toString();
        s += btnString;
        txtInput.setText(s);
    }

    public void deleteInput(String strInputString){
        if(!strInputString.equals("")){
            StringBuilder stringBuffer = new StringBuilder(strInputString);
            stringBuffer.deleteCharAt(strInputString.length() - 1);
            strInputString = stringBuffer.toString();
            txtInput.setText(strInputString);
        }
        wasDeletePressed = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        if(text.equals("Binary")){
            btnTwo.setEnabled(false);
            btnThree.setEnabled(false);
            btnFour.setEnabled(false);
            btnFive.setEnabled(false);
            btnSix.setEnabled(false);
            btnSeven.setEnabled(false);
            btnEight.setEnabled(false);
            btnNine.setEnabled(false);
            btnTen.setEnabled(false);
            btnEleven.setEnabled(false);
            btnTwelve.setEnabled(false);
            btnThirteen.setEnabled(false);
            btnFourteen.setEnabled(false);
            btnFifteen.setEnabled(false);
            btnDecimal.setEnabled(false);
            mode = "Binary";
            answer = "0";
        }
        else if(text.equals("Decimal")){
            btnTwo.setEnabled(true);
            btnThree.setEnabled(true);
            btnFour.setEnabled(true);
            btnFive.setEnabled(true);
            btnSix.setEnabled(true);
            btnSeven.setEnabled(true);
            btnEight.setEnabled(true);
            btnNine.setEnabled(true);
            btnTen.setEnabled(false);
            btnEleven.setEnabled(false);
            btnTwelve.setEnabled(false);
            btnThirteen.setEnabled(false);
            btnFourteen.setEnabled(false);
            btnFifteen.setEnabled(false);
            btnDecimal.setEnabled(true);
            mode = "Decimal";
            answer = "0";
        }
        else if(text.equals("Hexadecimal")){
            btnTwo.setEnabled(true);
            btnThree.setEnabled(true);
            btnFour.setEnabled(true);
            btnFive.setEnabled(true);
            btnSix.setEnabled(true);
            btnSeven.setEnabled(true);
            btnEight.setEnabled(true);
            btnNine.setEnabled(true);
            btnTen.setEnabled(true);
            btnEleven.setEnabled(true);
            btnTwelve.setEnabled(true);
            btnThirteen.setEnabled(true);
            btnFourteen.setEnabled(true);
            btnFifteen.setEnabled(true);
            btnDecimal.setEnabled(false);
            mode = "Hexadecimal";
            answer = "0";
        }
        clearAllTextViews();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        
    }
}
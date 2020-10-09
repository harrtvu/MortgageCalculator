package com.example.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText myMortgageAmount, myInterest, myAmortizationPeriod;
    private TextView myMonthlyPaymentResult, myTotalPaymentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myMortgageAmount = (EditText) findViewById(R.id.mortgage_amount);
        myInterest = (EditText) findViewById(R.id.interest_rate);
        myAmortizationPeriod = (EditText) findViewById(R.id.amortization_period);

        myMonthlyPaymentResult = (TextView) findViewById(R.id.monthly_payment_result);
        myTotalPaymentResult = (TextView) findViewById(R.id.total_payment_result);
    }

    public void showLoanPayment(View clickedButton){
        double loanAmount = Double.parseDouble(myMortgageAmount.getText().toString());
        double interestRate = Double.parseDouble(myInterest.getText().toString());
        double amortizationPeriod = (Double.parseDouble(myAmortizationPeriod.getText().toString())*12);

        double r = interestRate/1200;
        double r1 = Math.pow(r+1,amortizationPeriod);

        double monthlyPayment = (double) ((r+(r/(r1-1))) * loanAmount);
        double totalPayment = monthlyPayment * amortizationPeriod;

        myMonthlyPaymentResult.setText(new DecimalFormat("##.##").format(monthlyPayment));
        myTotalPaymentResult.setText(new DecimalFormat("##.##").format(totalPayment));
    }


}
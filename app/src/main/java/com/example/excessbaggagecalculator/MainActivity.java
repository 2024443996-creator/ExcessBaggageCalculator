package com.example.excessbaggagecalculator;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Input components
    EditText etWeight, etRate;
    Spinner spTicketClass;

    // Buttons
    Button btnCalculate, btnAbout, btnShare;

    // Output components
    TextView tvExcessWeight, tvExcessCharge, tvTotalPayable;

    // Results layout
    LinearLayout layoutResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML components
        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);

        spTicketClass = findViewById(R.id.spTicketClass);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnAbout = findViewById(R.id.btnAbout);
        btnShare = findViewById(R.id.btnShare);

        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotalPayable = findViewById(R.id.tvTotalPayable);

        layoutResults = findViewById(R.id.layoutResults);

        // Hide results when application starts
        layoutResults.setVisibility(View.GONE);

        // Ticket class options
        String[] ticketClasses = {
                "Economy",
                "Business"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                ticketClasses
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spTicketClass.setAdapter(adapter);

        // Calculate button
        btnCalculate.setOnClickListener(v -> calculateFee());

        // About button
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    AboutActivity.class
            );
            startActivity(intent);
        });

        // Share button
        btnShare.setOnClickListener(v -> shareApplication());

    }

    // =====================================
    // CALCULATE EXCESS BAGGAGE FEE
    // =====================================

    private void calculateFee() {

        String weightInput = etWeight.getText().toString().trim();
        String rateInput = etRate.getText().toString().trim();

        // Hide previous results before validation
        layoutResults.setVisibility(View.GONE);

        // Validate empty weight
        if (weightInput.isEmpty()) {
            etWeight.setError("Please enter baggage weight");
            etWeight.requestFocus();
            return;
        }

        // Validate empty rate
        if (rateInput.isEmpty()) {
            etRate.setError("Please enter rate per kg");
            etRate.requestFocus();
            return;
        }

        try {

            double baggageWeight = Double.parseDouble(weightInput);
            double ratePerKg = Double.parseDouble(rateInput);

            // Validate negative and invalid values
            if (!Double.isFinite(baggageWeight)
                    || !Double.isFinite(ratePerKg)
                    || baggageWeight < 0
                    || ratePerKg < 0) {

                Toast.makeText(
                        this,
                        "Please enter valid positive numbers.",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            // Determine baggage allowance
            String selectedClass =
                    spTicketClass.getSelectedItem().toString();

            double allowance;

            if (selectedClass.equals("Economy")) {
                allowance = 20;
            } else {
                allowance = 40;
            }

            // Calculate excess baggage
            double excessWeight = Math.max(
                    0,
                    baggageWeight - allowance
            );

            // Calculate excess charge
            double excessCharge = excessWeight * ratePerKg;

            // Calculate 8% service tax
            double serviceTax = excessCharge * 0.08;

            // Calculate total payable
            double totalPayable = excessCharge + serviceTax;

            // Display results
            tvExcessWeight.setText(
                    String.format(
                            Locale.US,
                            "Excess Weight: %.2f kg",
                            excessWeight
                    )
            );

            tvExcessCharge.setText(
                    String.format(
                            Locale.US,
                            "Excess Charge: RM %.2f",
                            excessCharge
                    )
            );

            tvTotalPayable.setText(
                    String.format(
                            Locale.US,
                            "Total Payable: RM %.2f",
                            totalPayable
                    )
            );

            // Show results only after successful calculation
            layoutResults.setVisibility(View.VISIBLE);

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "Please enter valid numbers.",
                    Toast.LENGTH_SHORT
            ).show();

        }
    }

    // =====================================
    // SHARE APPLICATION
    // =====================================

    private void shareApplication() {

        String appUrl =
                "https://github.com/2024443996-creator/ExcessBaggageCalculator";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");

        shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Excess Baggage Fee Calculator"
        );

        shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Try my Excess Baggage Calculator App!\n\n" + appUrl
        );

        startActivity(
                Intent.createChooser(
                        shareIntent,
                        "Share Application"
                )
        );
    }

}

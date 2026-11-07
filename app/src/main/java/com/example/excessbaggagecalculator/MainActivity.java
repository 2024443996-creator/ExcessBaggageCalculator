
package com.example.excessbaggagecalculator;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    } // End of onCreate()


    // =====================================
    // CALCULATE EXCESS BAGGAGE FEE
    // =====================================

    private void calculateFee() {

        String weightInput = etWeight.getText().toString().trim();
        String rateInput = etRate.getText().toString().trim();

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
            if (!Double.isFinite(baggageWeight) ||
                    !Double.isFinite(ratePerKg) ||
                    baggageWeight < 0 ||
                    ratePerKg < 0) {

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

        // Replace this with your actual GitHub repository URL later
        String appUrl = "https://github.com/";

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

} // End of MainActivity class

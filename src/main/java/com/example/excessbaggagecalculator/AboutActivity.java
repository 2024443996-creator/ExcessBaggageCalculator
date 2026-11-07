package com.example.excessbaggagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.res.ColorStateList;

public class AboutActivity extends Activity {

    // Purple theme colours
    private final int PURPLE_PRIMARY = Color.rgb(103, 58, 183);
    private final int PURPLE_DARK = Color.rgb(49, 27, 146);
    private final int PURPLE_BACKGROUND = Color.rgb(247, 243, 252);
    private final int WHITE = Color.WHITE;
    private final int BLACK = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main layout
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(PURPLE_BACKGROUND);
        rootLayout.setPadding(16, 16, 16, 16);

        // ScrollView
        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(false);

        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);

        // Page title
        TextView title = new TextView(this);
        title.setText("ABOUT APPLICATION");
        title.setTextSize(22);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(PURPLE_DARK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 12, 0, 20);

        contentLayout.addView(title);

        // Application name
        addSection(
                contentLayout,
                "EXCESS BAGGAGE FEE CALCULATOR",
                ""
        );

        // Application description
        addSection(
                contentLayout,
                "APPLICATION DESCRIPTION",
                "This application calculates excess baggage charges " +
                        "based on baggage weight and ticket class. It also " +
                        "calculates the applicable service tax."
        );

        // Main features
        addSection(
                contentLayout,
                "MAIN FEATURES",
                "• Baggage weight input\n" +
                        "• Ticket class selection\n" +
                        "• Excess baggage fee calculation\n" +
                        "• Service tax calculation"
        );

        // Developer information
        addSection(
                contentLayout,
                "DEVELOPER INFORMATION",
                "Name: Aina Fasiha\n" +
                        "Student Number: 2024443996\n" +
                        "Programme Code: CDCS240\n" +
                        "Group: 5A"
        );

        // Application information
        addSection(
                contentLayout,
                "APPLICATION INFORMATION",
                "Version: 1.0"
        );

        // Copyright
        addSection(
                contentLayout,
                "COPYRIGHT",
                "Copyright 2026 Aina Fasiha. All Rights Reserved."
        );

        // GitHub link
        TextView githubLink = new TextView(this);
        githubLink.setText("VISIT GITHUB REPOSITORY");
        githubLink.setTextSize(14);
        githubLink.setTextColor(PURPLE_PRIMARY);
        githubLink.setTypeface(null, Typeface.BOLD);
        githubLink.setGravity(Gravity.CENTER);
        githubLink.setPadding(0, 18, 0, 12);

        githubLink.setOnClickListener(v -> {
            String githubUrl = "https://github.com/";

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(githubUrl)
            );

            startActivity(intent);
        });

        contentLayout.addView(githubLink);

        // Add content to ScrollView
        scrollView.addView(contentLayout);

        // ScrollView uses only the content height
        rootLayout.addView(
                scrollView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        // Back button
        Button btnBack = new Button(this);
        btnBack.setText("BACK TO CALCULATOR");
        btnBack.setTextSize(14);
        btnBack.setTextColor(WHITE);
        btnBack.setTypeface(null, Typeface.BOLD);

        btnBack.setBackgroundTintList(
                ColorStateList.valueOf(PURPLE_DARK)
        );

        // Return to calculator
        btnBack.setOnClickListener(v -> finish());

        // Button directly below GitHub link
        LinearLayout.LayoutParams backParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        backParams.setMargins(0, 8, 0, 0);

        rootLayout.addView(btnBack, backParams);

        // Display page
        setContentView(rootLayout);
    }

    // Method to create sections
    private void addSection(
            LinearLayout layout,
            String heading,
            String content
    ) {

        TextView section = new TextView(this);

        if (content.isEmpty()) {
            section.setText(heading);
        } else {
            section.setText(heading + "\n" + content);
        }

        section.setTextSize(14);
        section.setTextColor(BLACK);
        section.setLineSpacing(3, 1.0f);
        section.setPadding(12, 14, 12, 14);
        section.setBackgroundColor(WHITE);

        layout.addView(section);

        // Space between sections
        TextView spacing = new TextView(this);
        spacing.setHeight(4);

        layout.addView(spacing);
    }
}

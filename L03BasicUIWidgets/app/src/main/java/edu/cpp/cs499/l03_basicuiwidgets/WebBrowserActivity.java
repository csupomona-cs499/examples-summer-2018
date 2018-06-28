package edu.cpp.cs499.l03_basicuiwidgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class WebBrowserActivity extends AppCompatActivity {

    EditText urlEditText;
    Button goButton;
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        urlEditText = findViewById(R.id.urlEditText);
        goButton = findViewById(R.id.goButton);
        webView = findViewById(R.id.webView);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!urlEditText.getText().toString().isEmpty()) {
                    webView.loadUrl(urlEditText.getText().toString());
                }
            }
        });
    }
}

package com.example.myapplication;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageView = findViewById(R.id.imageProfile);
        TextView nameView = findViewById(R.id.textName);
        TextView locationView = findViewById(R.id.textLocation);

        nameView.setText("Trần Văn Dự");
        locationView.setText("Hồ Chí Minh, Việt Nam");
        imageView.setImageResource(R.drawable.profile_image);
        float scaleFactor = 0.57f;
        float translateX = 1f;

        Matrix matrix = new Matrix();
        matrix.setScale(scaleFactor, scaleFactor, imageView.getWidth() / 2f, imageView.getHeight() / 2f);
        matrix.postTranslate(translateX, 0);

        imageView.setImageMatrix(matrix);

    }
}

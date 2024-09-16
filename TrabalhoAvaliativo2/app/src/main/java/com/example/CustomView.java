package com.example;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class CustomView extends View {

    private Paint paint;
    private int circleColor;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "ColorPreferences";
    private static final String COLOR_KEY = "circle_color";

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Recupera a cor salva ou define a cor padrão (azul)
        circleColor = sharedPreferences.getInt(COLOR_KEY, Color.BLUE);

        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Define a cor do círculo
        paint.setColor(circleColor);

        // Desenha o círculo maior
        int radius = Math.min(width, height) / 3;
        canvas.drawCircle(width / 2, height / 2, radius, paint);

        // Desenha círculos concêntricos
        paint.setStyle(Paint.Style.FILL);
        for (int i = 1; i <= 3; i++) {
            canvas.drawCircle(width / 2, height / 2, radius * i / 3, paint);
        }

        // Desenha retas ortogonais
        paint.setColor(Color.BLACK);
        canvas.drawLine(width / 2, 0, width / 2, height, paint);
        canvas.drawLine(0, height / 2, width, height / 2, paint);
    }

    // Método para atualizar a cor do círculo
    public void updateColor(int newColor) {
        circleColor = newColor;
        invalidate(); // Redesenha o componente
        saveColor(newColor); // Salva a cor nas Preferências
    }

    // Salva a cor nas Preferências
    private void saveColor(int color) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLOR_KEY, color);
        editor.apply();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Chama o diálogo de seleção de cor com três opções
            ((MainActivity) getContext()).openColorDialog(this);
        }
        return true;
    }
}
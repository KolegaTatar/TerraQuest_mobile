package edu.zsk.terraquest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class TrapezoidImageView extends AppCompatImageView {

    private Path path = new Path();

    public TrapezoidImageView(Context context) {
        super(context);
    }

    public TrapezoidImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrapezoidImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(width * 0.35f, 0);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();

        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}

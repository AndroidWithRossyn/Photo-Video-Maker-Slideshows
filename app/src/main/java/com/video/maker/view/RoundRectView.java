package com.video.maker.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.video.maker.R;

public class RoundRectView extends ShapeOfView {
    private int borderColor = -1;
    private final Paint borderPaint = new Paint(1);
    private final Path borderPath = new Path();
    private final RectF borderRectF = new RectF();
    private float borderWidthPx = 0.0f;

    public float bottomLeftRadius = 0.0f;

    public float bottomRightRadius = 0.0f;

    public final RectF rectF = new RectF();

    public float topLeftRadius = 0.0f;

    public float topRightRadius = 0.0f;

    public RoundRectView(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public RoundRectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public RoundRectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            int[] RoundRectView = {R.attr.shape_roundRect_borderColor, R.attr.shape_roundRect_borderWidth, R.attr.shape_roundRect_bottomLeftRadius, R.attr.shape_roundRect_bottomRightRadius, R.attr.shape_roundRect_topLeftRadius, R.attr.shape_roundRect_topRightRadius};
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, RoundRectView);
            this.topLeftRadius = (float) obtainStyledAttributes.getDimensionPixelSize(4, (int) this.topLeftRadius);
            this.topRightRadius = (float) obtainStyledAttributes.getDimensionPixelSize(5, (int) this.topRightRadius);
            this.bottomLeftRadius = (float) obtainStyledAttributes.getDimensionPixelSize(2, (int) this.bottomLeftRadius);
            this.bottomRightRadius = (float) obtainStyledAttributes.getDimensionPixelSize(3, (int) this.bottomRightRadius);
            this.borderColor = obtainStyledAttributes.getColor(0, this.borderColor);
            this.borderWidthPx = (float) obtainStyledAttributes.getDimensionPixelSize(1, (int) this.borderWidthPx);
            obtainStyledAttributes.recycle();
        }
        this.borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            public boolean requiresBitmap() {
                return false;
            }

            public Path createClipPath(int i, int i2) {
                float f = (float) i;
                float f2 = (float) i2;
                RoundRectView.this.rectF.set(0.0f, 0.0f, f, f2);
                RoundRectView roundRectView = RoundRectView.this;
                RectF access$000 = roundRectView.rectF;
                RoundRectView roundRectView2 = RoundRectView.this;
                float limitSize = roundRectView2.limitSize(roundRectView2.topLeftRadius, f, f2);
                RoundRectView roundRectView3 = RoundRectView.this;
                float limitSize2 = roundRectView3.limitSize(roundRectView3.topRightRadius, f, f2);
                RoundRectView roundRectView4 = RoundRectView.this;
                float limitSize3 = roundRectView4.limitSize(roundRectView4.bottomRightRadius, f, f2);
                RoundRectView roundRectView5 = RoundRectView.this;
                return roundRectView.generatePath(access$000, limitSize, limitSize2, limitSize3, roundRectView5.limitSize(roundRectView5.bottomLeftRadius, f, f2));
            }
        });
    }


    public float limitSize(float f, float f2, float f3) {
        return Math.min(f, Math.min(f2, f3));
    }

    public void requiresShapeUpdate() {
        RectF rectF2 = this.borderRectF;
        float f = this.borderWidthPx;
        rectF2.set(f / 2.0f, f / 2.0f, ((float) getWidth()) - (this.borderWidthPx / 2.0f), ((float) getHeight()) - (this.borderWidthPx / 2.0f));
        this.borderPath.set(generatePath(this.borderRectF, this.topLeftRadius, this.topRightRadius, this.bottomRightRadius, this.bottomLeftRadius));
        super.requiresShapeUpdate();
    }


    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float f = this.borderWidthPx;
        if (f > 0.0f) {
            this.borderPaint.setStrokeWidth(f);
            this.borderPaint.setColor(this.borderColor);
            canvas.drawPath(this.borderPath, this.borderPaint);
        }
    }


    public Path generatePath(RectF rectF2, float f, float f2, float f3, float f4) {
        return generatePath(false, rectF2, f, f2, f3, f4);
    }

    private Path generatePath(boolean z, RectF rectF2, float f, float f2, float f3, float f4) {
        RectF rectF3 = rectF2;
        Path path = new Path();
        float f5 = rectF3.left;
        float f6 = rectF3.top;
        float f7 = rectF3.bottom;
        float f8 = rectF3.right;
        float min = Math.min(rectF2.width() / 2.0f, rectF2.height() / 2.0f);
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        float abs3 = Math.abs(f4);
        float abs4 = Math.abs(f3);
        if (abs > min) {
            abs = min;
        }
        if (abs2 > min) {
            abs2 = min;
        }
        if (abs3 > min) {
            abs3 = min;
        }
        if (abs4 <= min) {
            min = abs4;
        }
        float f9 = f5 + abs;
        path.moveTo(f9, f6);
        path.lineTo(f8 - abs2, f6);
        if (z) {
            path.quadTo(f8, f6, f8, abs2 + f6);
        } else {
            float f10 = abs2 * 2.0f;
            path.arcTo(new RectF(f8 - f10, f6, f8, f10 + f6), -90.0f, f2 > 0.0f ? 90.0f : -270.0f);
        }
        path.lineTo(f8, f7 - min);
        if (z) {
            path.quadTo(f8, f7, f8 - min, f7);
        } else {
            float f11 = min > 0.0f ? 90.0f : -270.0f;
            float f12 = min * 2.0f;
            path.arcTo(new RectF(f8 - f12, f7 - f12, f8, f7), 0.0f, f11);
        }
        path.lineTo(f5 + abs3, f7);
        if (z) {
            path.quadTo(f5, f7, f5, f7 - abs3);
        } else {
            float f13 = abs3 > 0.0f ? 90.0f : -270.0f;
            float f14 = abs3 * 2.0f;
            path.arcTo(new RectF(f5, f7 - f14, f14 + f5, f7), 90.0f, f13);
        }
        path.lineTo(f5, f6 + abs);
        if (z) {
            path.quadTo(f5, f6, f9, f6);
        } else {
            float f15 = abs > 0.0f ? 90.0f : -270.0f;
            float f16 = abs * 2.0f;
            path.arcTo(new RectF(f5, f6, f5 + f16, f16 + f6), 180.0f, f15);
        }
        path.close();
        return path;
    }

    public float getTopLeftRadius() {
        return this.topLeftRadius;
    }

    public void setTopLeftRadius(float f) {
        this.topLeftRadius = f;
        requiresShapeUpdate();
    }

    public float getTopLeftRadiusDp() {
        return pxToDp(getTopLeftRadius());
    }

    public void setTopLeftRadiusDp(float f) {
        setTopLeftRadius(dpToPx(f));
    }

    public float getTopRightRadius() {
        return this.topRightRadius;
    }

    public void setTopRightRadius(float f) {
        this.topRightRadius = f;
        requiresShapeUpdate();
    }

    public float getTopRightRadiusDp() {
        return pxToDp(getTopRightRadius());
    }

    public void setTopRightRadiusDp(float f) {
        setTopRightRadius(dpToPx(f));
    }

    public float getBottomRightRadius() {
        return this.bottomRightRadius;
    }

    public void setBottomRightRadius(float f) {
        this.bottomRightRadius = f;
        requiresShapeUpdate();
    }

    public float getBottomRightRadiusDp() {
        return pxToDp(getBottomRightRadius());
    }

    public void setBottomRightRadiusDp(float f) {
        setBottomRightRadius(dpToPx(f));
    }

    public float getBottomLeftRadius() {
        return this.bottomLeftRadius;
    }

    public void setBottomLeftRadius(float f) {
        this.bottomLeftRadius = f;
        requiresShapeUpdate();
    }

    public float getBottomLeftRadiusDp() {
        return pxToDp(getBottomLeftRadius());
    }

    public void setBottomLeftRadiusDp(float f) {
        setBottomLeftRadius(dpToPx(f));
    }

    public float getBorderColor() {
        return (float) this.borderColor;
    }

    public void setBorderColor(int i) {
        this.borderColor = i;
        requiresShapeUpdate();
    }

    public float getBorderWidth() {
        return this.borderWidthPx;
    }

    public void setBorderWidth(float f) {
        this.borderWidthPx = f;
        requiresShapeUpdate();
    }

    public float getBorderWidthDp() {
        return pxToDp(getBorderWidth());
    }

    public void setBorderWidthDp(float f) {
        setBorderWidth(dpToPx(f));
    }
}

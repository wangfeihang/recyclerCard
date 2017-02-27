package com.userinfo.card.myapplication.our;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    // 圆形边框的厚度默认值。
    // 如果是0，则没有天蓝色渐变的边框。
    private static final int DEFAULT_BORDER_WIDTH = 0;

    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    //    private final Paint mBorderPaint = new Paint();

    //    private int mBorderColor = DEFAULT_BORDER_COLOR;
    //    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    //    private float mBorderRadius;

    private boolean mReady;
    private boolean mSetupPending;
    private final Paint mFlagBackgroundPaint = new Paint();
    private final TextPaint mFlagTextPaint = new TextPaint();
    private String mFlagText;
    private boolean mShowFlag = false;
    private Rect mFlagTextBounds = new Rect();
    private Paint mOuterBorderPaint = new Paint();
    private Paint mInnerBorderPaint = new Paint();
    private int mInnerBorderWidth, mOuterBorderWidth;
    private int mInnerBorderColor, mOuterBorderColor;
    private float mInnerBorderRadius, mOuterBorderRadius;
    //    Shader mSweepGradient = null;

    public CircleImageView(Context context) {
        super(context);

        init();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius, mBitmapPaint);
        drawBorder(canvas, mOuterBorderWidth, mOuterBorderPaint, mOuterBorderRadius);
        drawBorder(canvas, mInnerBorderWidth, mInnerBorderPaint, mInnerBorderRadius);
        if (mShowFlag && mFlagText != null) {
            canvas.drawArc(mBorderRect, 40, 100, false, mFlagBackgroundPaint);
            mFlagTextPaint.getTextBounds(mFlagText, 0, mFlagText.length(), mFlagTextBounds);
            canvas.drawText(mFlagText, getWidth() / 2,
                    (float) (3 + Math.cos((float) (Math.PI * 5 / 18))) * getHeight() / 4 + mFlagTextBounds.height() / 3,
                    mFlagTextPaint);
        }
    }

    private void drawBorder(Canvas canvas, int borderWidth, Paint borderPaint, float borderRadius) {
        if (borderWidth != 0) {
            canvas.save();
            canvas.rotate(20, getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, borderRadius, borderPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public void setBorderColor(int borderColor) {
        if (borderColor == mOuterBorderColor) {
            return;
        }
        mOuterBorderColor = borderColor;
        mOuterBorderPaint.setColor(borderColor);
        invalidate();
    }

    public void setInnerBorderColor(int borderColor) {
        if (borderColor == mInnerBorderColor) {
            return;
        }
        mInnerBorderColor = borderColor;
        mInnerBorderPaint.setColor(borderColor);
        invalidate();
    }

    /**
     * @param borderWidth 圆形的边框厚度。
     */
    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mOuterBorderWidth) {
            return;
        }

        mOuterBorderWidth = borderWidth;
        setup();
    }

    //内边框
    public void setInnerBorderWidth(int borderWidth) {
        if (borderWidth == mInnerBorderWidth) {
            return;
        }
        mInnerBorderWidth = borderWidth;
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap =
                        Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);
        initPaint(mOuterBorderPaint, mOuterBorderColor, mOuterBorderWidth);
        initPaint(mInnerBorderPaint, mInnerBorderColor, mInnerBorderWidth);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mBorderRect.set(0, 0, getWidth(), getHeight());
        mOuterBorderRadius = getBorderRadius(mOuterBorderWidth);
        mInnerBorderRadius = getBorderRadius(mInnerBorderWidth + mOuterBorderWidth * 2);

        mDrawableRect.set(mOuterBorderWidth, mOuterBorderWidth, mBorderRect.width() - mOuterBorderWidth,
                mBorderRect.height() - mOuterBorderWidth);
        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);

        mFlagBackgroundPaint.setColor(Color.BLACK & 0x66FFFFFF);
        mFlagBackgroundPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        mFlagTextPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);
        mFlagTextPaint.setTextAlign(Align.CENTER);
        mFlagTextPaint.setColor(Color.WHITE);
        mFlagTextPaint.setTextSize(getResources().getDisplayMetrics().density * 18);

        updateShaderMatrix();
        invalidate();
    }

    private void initPaint(Paint borderPaint, int borderColor, int borderWidth) {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
    }

    private float getBorderRadius(int borderWidth) {
        return Math.min((getHeight() - borderWidth) / 2, (getWidth() - borderWidth) / 2);
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mOuterBorderWidth, (int) (dy + 0.5f) + mOuterBorderWidth);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    public void setShowFlag(boolean show) {
        mShowFlag = show;
        invalidate();
    }

    public void setFlagText(String text) {
        mFlagText = text;
        invalidate();
    }
}

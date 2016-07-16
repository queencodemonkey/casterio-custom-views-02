package io.caster.customviews.debug;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import io.caster.customviews.util.MeasureUtils;
import io.caster.customviews.R;
import io.caster.customviews.TimerView;

/**
 * Alternate implementation of {@link io.caster.customviews.TimerView} that includes rendering of
 * parent measure specs to the screen.
 */
public class MeasureSpecTimerView extends TimerView {

    // Fields for displaying measure spec.
    private int widthMeasureSpec;
    private int heightMeasureSpec;
    private TextPaint sizePaint;

    //
    // Constructors/initialization
    //

    public MeasureSpecTimerView(Context context) {
        super(context);
        init();
    }

    public MeasureSpecTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Just for measure spec display.
        sizePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        sizePaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        sizePaint.setTextSize(14f * getResources().getDisplayMetrics().scaledDensity);
    }

    //
    // View overrides
    //

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = Math.round(canvas.getWidth() * 0.5f);
        float centerY = Math.round(canvas.getHeight() * 0.5f);

        // Grab font metrics for convenience.
        Paint.FontMetrics numberFontMetrics = getNumberPaint().getFontMetrics();
        Paint.FontMetrics sizeFontMetrics = sizePaint.getFontMetrics();
        Resources resources = getResources();

        // Draw layout params.
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        String widthParams = String.format(resources.getString(R.string.layout_width_format),
            MeasureUtils.layoutParamToString(resources, layoutParams.width)).toUpperCase();
        float textOffsetX = sizePaint.measureText(widthParams) * 0.5f;
        float textOffsetY = numberFontMetrics.ascent * 0.8f;
        canvas.drawText(widthParams, centerX - textOffsetX, centerY + textOffsetY, sizePaint);
        String heightParams = String.format(resources.getString(R.string.layout_height_format),
            MeasureUtils.layoutParamToString(resources, layoutParams.height)).toUpperCase();
        textOffsetX = sizePaint.measureText(heightParams) * 0.5f;
        textOffsetY += sizeFontMetrics.descent + -sizeFontMetrics.top;
        canvas.drawText(heightParams, centerX - textOffsetX, centerY + textOffsetY,
            sizePaint);

        // Draw width measure spec.
        String measureSpec = String.format(resources.getString(R.string.spec_width_format),
            measureSpecToString(widthMeasureSpec)).toUpperCase();
        textOffsetX = sizePaint.measureText(measureSpec) * 0.5f;
        textOffsetY = numberFontMetrics.ascent * -0.4f +
            8f * resources.getDisplayMetrics().density + -sizeFontMetrics.top;
        canvas.drawText(measureSpec, centerX - textOffsetX, centerY + textOffsetY,
            sizePaint);
        // Draw height measure spec.
        measureSpec = String.format(resources.getString(R.string.spec_height_format),
            measureSpecToString(heightMeasureSpec)).toUpperCase();
        textOffsetX = sizePaint.measureText(measureSpec) * 0.5f;
        textOffsetY += sizeFontMetrics.descent + -sizeFontMetrics.top;
        canvas.drawText(measureSpec, centerX - textOffsetX, centerY + textOffsetY,
            sizePaint);
    }

    //
    // MeasureSpec display helpers
    //

    private String measureSpecToString(int measureSpec) {
        Resources resources = getResources();
        String mode;
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.EXACTLY:
                mode = resources.getString(R.string.exactly);
                break;
            case MeasureSpec.AT_MOST:
                mode = resources.getString(R.string.at_most);
                break;
            case MeasureSpec.UNSPECIFIED:
                mode = resources.getString(R.string.unspecified);
                break;
            default:
                mode = "";
                break;
        }

        return mode + " " + MeasureUtils.pixelsToDPString(resources,
            MeasureSpec.getSize(measureSpec));
    }
}

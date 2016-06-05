package pl.applover.mydebts;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * author:  Adrian Kuta
 * date:    13.04.2016
 * email:   redione193@gmail.com
 */
public class ColorableButton extends Button{


	public ColorableButton(Context context) {
		super(context);
	}

	public ColorableButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ColorableButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setButtonColor(int colorResId) {
		Drawable background = getBackground();
		int color = ContextCompat.getColor(getContext(), colorResId);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
			background.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
		else {
			background = DrawableCompat.wrap(background);
			DrawableCompat.setTint(background, color);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
				setBackground(background);
			else
				setBackgroundColor(color);
		}
	}
}

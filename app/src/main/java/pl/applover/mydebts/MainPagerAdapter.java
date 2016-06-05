package pl.applover.mydebts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

	private Context context;

	public MainPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {switch (position) {
		case 0:
			return new PersonsFragment();
		case 1:
			return new EventFragment();
		default:
			return new PersonsFragment();
	}
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
			case 0:
				return context.getString(R.string.persons);
			case 1:
				return context.getString(R.string.events);
			default:
				return "Page" + (position + 1);
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
}

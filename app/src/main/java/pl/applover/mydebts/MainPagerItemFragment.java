package pl.applover.mydebts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;
import java.util.List;

import pl.applover.mydebts.firebase.FireObject;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public abstract class MainPagerItemFragment<T extends FireObject> extends Fragment {

	List<T> adapterList = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		return inflater.inflate(R.layout.persons_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);



	}
}

package pl.applover.mydebts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class PersonsFragment extends Fragment implements ChildEventListener {

	List<User> adapterList = new ArrayList<>();
	RecyclerView recyclerView;
	RecyclerView.Adapter adapter;
	RecyclerView.LayoutManager layoutManager;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.persons_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		adapter = new PersonsAdapter(adapterList);
		recyclerView = (RecyclerView) view.findViewById(R.id.persons_recyclerView);

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		DatabaseReference reference = User.getDatabaseReference();
		reference.addChildEventListener(this);
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String s) {
		User addedUser = dataSnapshot.getValue(User.class);
		Log.d("DEBUG_TAG", "test");
		String key = dataSnapshot.getKey();
		addedUser.setKey(key);
		adapterList.add(addedUser);
		adapter.notifyItemInserted(adapterList.indexOf(addedUser));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String s) {
		User changedUser = dataSnapshot.getValue(User.class);
		String key = dataSnapshot.getKey();
		changedUser.setKey(key);
		for (int i = 0; i < adapterList.size(); i++) {
			User user = adapterList.get(i);
			if (key.equals(user.getUid())) {
				adapterList.set(i, changedUser);
				adapter.notifyItemChanged(i);
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildRemoved(DataSnapshot dataSnapshot) {
		String key = dataSnapshot.getKey();
		Iterator<User> iterator = adapterList.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (key.equals(user.getUid())) {
				int i = adapterList.indexOf(user);
				iterator.remove();
				adapter.notifyItemRemoved(i);
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildMoved(DataSnapshot dataSnapshot, String s) {

	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}
}

package pl.applover.mydebts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class PersonsFragment extends MainPagerItemFragment<User> implements ChildEventListener {

	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<User> userList;

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		userList = new ArrayList<>();
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.persons_recyclerView);
		layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		adapter = new PersonsAdapter(userList);

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
		databaseReference.addChildEventListener(this);

	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
		User user = dataSnapshot.getValue(User.class);
		String key = dataSnapshot.getKey();
		user.setKey(key);
		userList.add(user);
		adapter.notifyItemInserted(userList.indexOf(user));
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
		String key = dataSnapshot.getKey();
		User changedUser = dataSnapshot.getValue(User.class);
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			if (user.getUid().equals(key)) {
				userList.set(i, changedUser);
				adapter.notifyItemChanged(i);
			}
		}
	}

	@Override
	public void onChildRemoved(DataSnapshot dataSnapshot) {
		String key = dataSnapshot.getKey();
		Iterator<User> iterator = userList.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (key.equals(user.getUid())) {
				int index = userList.indexOf(user);
				iterator.remove();
				adapter.notifyItemRemoved(index);
			}
		}
	}

	@Override
	public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}
}

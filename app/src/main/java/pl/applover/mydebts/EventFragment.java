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

import pl.applover.mydebts.firebase.Event;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class EventFragment extends Fragment implements ChildEventListener {

	List<Event> adapterList = new ArrayList<>();
	RecyclerView recyclerView;
	RecyclerView.Adapter adapter;
	RecyclerView.LayoutManager layoutManager;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.events_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		adapter = new EventAdapter(adapterList);
		layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.persons_recyclerView);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		DatabaseReference reference = Event.getDatabaseReference();
		reference.addChildEventListener(this);
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String s) {
		Event addedEvent = dataSnapshot.getValue(Event.class);
		Log.d("DEBUG_TAG", "test");
		String key = dataSnapshot.getKey();
		addedEvent.setKey(key);
		adapterList.add(addedEvent);
		adapter.notifyItemInserted(adapterList.indexOf(addedEvent));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String s) {
		Event changedEvent = dataSnapshot.getValue(Event.class);
		String key = dataSnapshot.getKey();
		changedEvent.setKey(key);
		for (int i = 0; i < adapterList.size(); i++) {
			Event event = adapterList.get(i);
			if (key.equals(event.getUid())) {
				adapterList.set(i, changedEvent);
				adapter.notifyItemChanged(i);
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onChildRemoved(DataSnapshot dataSnapshot) {
		String key = dataSnapshot.getKey();
		Iterator<Event> iterator = adapterList.iterator();
		while (iterator.hasNext()) {
			Event event = iterator.next();
			if (key.equals(event.getUid())) {
				int i = adapterList.indexOf(event);
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


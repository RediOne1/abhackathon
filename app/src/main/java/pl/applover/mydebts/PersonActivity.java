package pl.applover.mydebts;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.applover.mydebts.firebase.Event;
import pl.applover.mydebts.firebase.User;

public class PersonActivity extends AppCompatActivity implements ChildEventListener {

	public static final String USER_ID = "userId";
	List<Event> adapterList = new ArrayList<>();
	RecyclerView recyclerView;
	RecyclerView.Adapter adapter;
	RecyclerView.LayoutManager layoutManager;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		imageView = (ImageView) findViewById(R.id.user_image);
		adapter = new EventAdapter(adapterList);
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView = (RecyclerView) findViewById(R.id.person_activity_recyclerView);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		DatabaseReference reference = Event.getDatabaseReference();
		reference.addChildEventListener(this);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String userId = bundle.getString(USER_ID);
			displayUser(userId);
		}

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

	@Override
	public void onBackPressed() {
		supportFinishAfterTransition();
	}

	private void displayUser(final String userId) {
		DatabaseReference reference = User.getDatabaseReference().child(userId);
		reference.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				User user = dataSnapshot.getValue(User.class);
				if (imageView != null) {
					ImageLoader imageLoader = ImageLoader.getInstance();
					imageLoader.displayImage(user.photoUrl, imageView);
				}
				CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
				toolbarLayout.setTitle(user.displayName);
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
}

package pl.applover.mydebts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import pl.applover.mydebts.firebase.User;

public class SelectUsersActivity extends AppCompatActivity implements ChildEventListener, View.OnClickListener {

	public static final int SELECT_USERS = 123;
	private RecyclerView.LayoutManager layoutManager;
	private RecyclerView.Adapter adapter;
	private List<User> userList;
	private List<String> selectedIds;
	private DatabaseReference reference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_users);

		Bundle bundle = getIntent().getExtras();
		if(bundle != null)
			selectedIds = bundle.getStringArrayList("selectedIds");
		 else
			selectedIds = new ArrayList<>();

		userList = new ArrayList<>();

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.select_users_recyclerView);
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		adapter = new SelectUsersAdapter(userList, selectedIds, new SelectUsersAdapter.SelectUserListener() {
			@Override
			public void onUserSelected(int position, User user) {
				selectedIds.add(user.getUid());
			}

			@Override
			public void onUserUnselected(int position, User user) {
				selectedIds.remove(user.getUid());
			}
		});
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);

		reference = User.getDatabaseReference();

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		reference.addChildEventListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		reference.removeEventListener(this);
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String s) {
		User user = dataSnapshot.getValue(User.class);
		user.setKey(dataSnapshot.getKey());
		userList.add(user);
		adapter.notifyItemInserted(userList.indexOf(user));
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String s) {

	}

	@Override
	public void onChildRemoved(DataSnapshot dataSnapshot) {

	}

	@Override
	public void onChildMoved(DataSnapshot dataSnapshot, String s) {

	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.fab) {
			Intent returnIntent = new Intent();
			returnIntent.putStringArrayListExtra("selectedIds", (ArrayList<String>) selectedIds);
			setResult(Activity.RESULT_OK, returnIntent);
			finish();
		}
	}
}

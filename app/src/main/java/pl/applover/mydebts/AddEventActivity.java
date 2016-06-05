package pl.applover.mydebts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.applover.mydebts.firebase.Balance;
import pl.applover.mydebts.firebase.Event;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

	private RecyclerView recyclerView;
	private RecyclerView.Adapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<Balance> balanceList;
	private Event event;
	private ArrayList<String> selectedIds = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		event = new Event();
		balanceList = new ArrayList<>();

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(this);

		EditText amount = (EditText) findViewById(R.id.amount);
		amount.addTextChangedListener(this);

		TextView manage_persons = (TextView) findViewById(R.id.manage_persons);
		manage_persons.setOnClickListener(this);

		recyclerView = (RecyclerView) findViewById(R.id.add_event_recyclerView);
		adapter = new AddEventUserAdapter(balanceList);
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.fab) {
			saveEvent();
		} else if (id == R.id.manage_persons) {
			Intent intent = new Intent(this, SelectUsersActivity.class);
			intent.putStringArrayListExtra("selectedIds", selectedIds);
			startActivityForResult(intent, SelectUsersActivity.SELECT_USERS);
		}
	}

	private void saveEvent() {
		EditText titleTV = (EditText) findViewById(R.id.event_name);
		CharSequence text = titleTV.getText();
		String title = "";
		if (text != null)
			title = text.toString();
		event.title = title;
		EditText amountEditText = (EditText) findViewById(R.id.amount);
		event.price= Double.parseDouble(amountEditText.getText()+"");
		for (String userId : selectedIds)
			event.connected_users.put(userId, true);

		DatabaseReference push = Event.getDatabaseReference()
				.push();
		String eventKey = push.getKey();

		Map<String, Object> childUpdates = new HashMap<>();

		for (Balance balance : balanceList) {
			balance.event_id = eventKey;
			String key = Balance.getDatabaseReference().push().getKey();
			childUpdates.put("/balance/" + key, balance.toMap());
		}
		childUpdates.put("/event/" + eventKey, event.toMap());

		DatabaseReference database = FirebaseDatabase.getInstance().getReference();
		database.updateChildren(childUpdates)
				.addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						finish();
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			List<String> selectedIds = data.getStringArrayListExtra("selectedIds");
			displayUsers(selectedIds);
		}

	}

	private void displayUsers(List<String> selectedIds) {
		this.selectedIds = (ArrayList<String>) selectedIds;
		balanceList.clear();
		for (String user_id : selectedIds) {
			Balance balance = new Balance();
			balance.user_id = user_id;
			balanceList.add(balance);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (s.length() == 0)
			return;
		float amount = Float.parseFloat(s.toString());
		int size = balanceList.size();
		for (Balance balance : balanceList) {
			balance.expectedValue = amount / size;
		}
		adapter.notifyDataSetChanged();
	}
}

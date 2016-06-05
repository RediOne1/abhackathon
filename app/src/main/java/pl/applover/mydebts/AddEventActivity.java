package pl.applover.mydebts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import pl.applover.mydebts.firebase.Balance;
import pl.applover.mydebts.firebase.Event;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

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
			Intent intent = new Intent(this, SelectUsersActivity.class);
			intent.putStringArrayListExtra("selectedIds", selectedIds);
			startActivityForResult(intent, SelectUsersActivity.SELECT_USERS);
		}
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
}

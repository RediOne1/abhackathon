package pl.applover.mydebts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private ViewPager pager;
	private MainPagerAdapter pagerAdapter;
	private TabLayout tabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		tabLayout = (TabLayout) findViewById(R.id.tab_layout);


		pagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());

		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(pagerAdapter);

		tabLayout.setupWithViewPager(pager);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.fab) {
			if (tabLayout.getSelectedTabPosition() == 0)
				addPerson();
			else if (tabLayout.getSelectedTabPosition() == 1)
				addInvoice();
		}
	}

	private void addPerson() {
		startActivity(new Intent(this, AddPersonActivity.class));
	}

	private void addInvoice() {
		startActivity(new Intent(this, AddEventActivity.class));
	}
}

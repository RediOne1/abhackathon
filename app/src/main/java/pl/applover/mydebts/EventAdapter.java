package pl.applover.mydebts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.applover.mydebts.firebase.Event;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
	private List<Event> adapterList;

	public EventAdapter(List<Event> adapterList) {
		this.adapterList = adapterList;
	}

	@Override
	public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.event_layout, parent, false);
		ViewHolder holder = new ViewHolder(view);
		holder.title = (TextView) view.findViewById(R.id.title);
		return holder;
	}

	@Override
	public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
		Event event = adapterList.get(position);
		holder.title.setText("" + event.title);
	}

	@Override
	public int getItemCount() {
		return adapterList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public TextView title;

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}

package pl.applover.mydebts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

import pl.applover.mydebts.firebase.User;

/**
 * author:  Adrian Kuta
 * date:    04.06.2016
 */
public class PersonsAdapter extends RecyclerView.Adapter<PersonsAdapter.ViewHolder> {

	private List<User> userList;
	private ViewHolder.OnItemClickListener listener;

	public PersonsAdapter(List<User> userList, ViewHolder.OnItemClickListener listener) {
		this.userList = userList;
		this.listener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.single_person_layout, parent, false);
		ViewHolder holder = new ViewHolder(view, listener);
		holder.image = (ImageView) view.findViewById(R.id.user_image);
		holder.username = (TextView) view.findViewById(R.id.username);
		holder.price = (TextView) view.findViewById(R.id.price);
		holder.eventsCount = (TextView) view.findViewById(R.id.eventsCount);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		User user = userList.get(position);
		holder.username.setText(user.displayName);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(user.photoUrl, holder.image);
		DecimalFormat df = new DecimalFormat("#.00");
		holder.price.setText(df.format(user.price));
		df = new DecimalFormat("#");
		holder.eventsCount.setText(df.format(user.eventsCount));
	}

	@Override
	public int getItemCount() {
		return userList.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView image;
		public TextView username;
		public TextView price;
		public TextView eventsCount;

		public ViewHolder(final View itemView, final OnItemClickListener listener) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (listener != null)
						listener.onItemClick(getAdapterPosition(), itemView, image);
				}
			});
		}

		public interface OnItemClickListener {
			void onItemClick(int position, View rootView, ImageView imageView);
		}
	}
}

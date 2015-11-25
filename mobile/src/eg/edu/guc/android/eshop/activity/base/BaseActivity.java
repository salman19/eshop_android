package eg.edu.guc.android.eshop.activity.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import eg.edu.guc.android.eshop.R;
import eg.edu.guc.android.eshop.model.User;

public abstract class BaseActivity extends Activity {
	private static final String PREF_USER_ID = "PREF_USER_ID";
	private static final String PREF_USER_NAME = "PREF_USER_NAME";
	private static final String PREF_USER_EMAIL = "PREF_USER_EMAIL";
	private static final String PREF_USER_TOKEN = "PREF_USER_TOKEN";

	private User currentUser;
	private int inProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}

	@Override
	protected void onResume() {
		super.onResume();

		invalidateOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		if (isLoggedIn()) {
			inflater.inflate(R.menu.menu_eshop, menu);
		}
		else {
			inflater.inflate(R.menu.menu_eshop_guest, menu);
		}

		return isRefreshable();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			refreshViews();
			return true;
		case R.id.action_logout:
			setCurrentUser(null);
			recreate();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected boolean isRefreshable() {
		return true;
	}

	protected void refreshViews() {
	}

	protected void startProgress() {
		setProgressBarIndeterminateVisibility(true);
		inProgress++;
	}

	protected void stopProgress() {
		if (--inProgress == 0) {
			setProgressBarIndeterminateVisibility(false);
		}
	}

	protected void displayError(Exception e) {
		setProgressBarIndeterminateVisibility(false);
		Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
				.show();
	}

	protected boolean isLoggedIn() {
		return getCurrentUser() != null;
	}

	protected User getCurrentUser() {
		if (currentUser == null) {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

			if (sharedPreferences.contains(PREF_USER_TOKEN)) {
				currentUser = new User();
				currentUser.setId(sharedPreferences.getLong(PREF_USER_ID, 0));
				currentUser.setName(sharedPreferences.getString(PREF_USER_NAME, null));
				currentUser.setEmail(sharedPreferences.getString(PREF_USER_EMAIL, null));
				currentUser.setToken(sharedPreferences.getString(PREF_USER_TOKEN, null));
			}
		}

		return currentUser;
	}

	protected void setCurrentUser(User user) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor p = sharedPreferences.edit();

		if ((currentUser = user) != null) {
			p.putLong(PREF_USER_ID, currentUser.getId());
			p.putString(PREF_USER_NAME, currentUser.getName());
			p.putString(PREF_USER_EMAIL, currentUser.getEmail());
			p.putString(PREF_USER_TOKEN, currentUser.getToken());
		}
		else {
			p.clear();
		}

		p.commit();
	}
}

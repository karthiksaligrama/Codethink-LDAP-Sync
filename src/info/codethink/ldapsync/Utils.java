package info.codethink.ldapsync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

public class Utils {
	static Bundle bundleError(int code, String message)
	{
		final Bundle errorResult = new Bundle();
		errorResult.putInt(AccountManager.KEY_ERROR_CODE, code);
		errorResult.putString(AccountManager.KEY_ERROR_MESSAGE, message);
		return errorResult;
	}
	static Bundle bundleResult(boolean result)
	{
		final Bundle resultBundle = new Bundle();
		resultBundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, result);
		return resultBundle;
	}
	static Bundle bundleAccount(String type, String name, String authToken)
	{
		final Bundle accountBundle = new Bundle();
		accountBundle.putString(AccountManager.KEY_ACCOUNT_TYPE, type);
		accountBundle.putString(AccountManager.KEY_ACCOUNT_NAME, name);
		if (authToken != null)
			accountBundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);
		return accountBundle;
	}
	static Uri syncURI(Uri contentUri)
	{
		return contentUri.buildUpon().appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "1").build();
	}
	public static Bundle getSavedSettngs(AccountManager mgr, Account acct)
	{
		String[] allSettings = new String[] { "server", "binddn", "basedn", "security" };
		Bundle settings = new Bundle();
		for (String key: allSettings) {
			settings.putString(key, mgr.getUserData(acct, key));
		}
		settings.putString("password", mgr.getPassword(acct));
		return settings;
	}
	public static void saveSettings(AccountManager mgr, Account acct, Bundle settings)
	{
		for (String key: settings.keySet()) {
			if (key.equals("password")) {
				mgr.setPassword(acct, settings.getString(key));
			} else {
				mgr.setUserData(acct, key, settings.getString(key));
			}
		}
	}
}

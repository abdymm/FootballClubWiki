package com.abdymalikmulky.fooball.footballclubwiki.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.abdymalikmulky.fooball.footballclubwiki.R;

/**
 * Created by Abdy.
 */

public class SharedPreferenceUtil {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    public SharedPreferenceUtil(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(context.getString(R.string.teammates_sp), PRIVATE_MODE);
        editor = pref.edit();
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }


}

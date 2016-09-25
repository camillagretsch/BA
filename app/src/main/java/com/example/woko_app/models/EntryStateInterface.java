package com.example.woko_app.models;

import com.example.woko_app.fragment.DataGridFragment;

import java.util.List;

/**
 * Created by camillagretsch on 21.09.16.
 */
public interface EntryStateInterface {

    public void getEntries(DataGridFragment dataGridFragment);

    public void duplicateEntries(AP ap, AP oldAP);

    public void createNewEntry(AP ap);

    public void saveCheckEntries(List<Boolean> check);

    public void saveCheckOldEntries(List<Boolean> checkOld);

    public void saveCommentsEntries(List<String> comments);
}

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

    public void saveCheckEntries(List<String> check);

    public void saveCheckOldEntries(List<Boolean> checkOld);

    public void updateName(String newEx, String oldEx);

    public void saveCommentsEntries(List<String> comments);

    public String getCommentAtPosition(int pos);

    public Boolean getCheckAtPosition(int pos);

    public Boolean getCheckOldAtPosition(int pos);

    public void savePicture(int pos, byte[] picture);

    public byte[] getPictureAtPosition(int pos);

    public int countPicturesOfLast5Years(int pos, EntryStateInterface entryStateInterface);

}

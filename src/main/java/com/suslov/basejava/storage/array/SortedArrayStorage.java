package com.suslov.basejava.storage.array;

import com.suslov.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findSearchKey(String uuid) {
        // Created only for the search by UUID
        Resume dummyResume = new Resume(uuid, "Dummy");
        return Arrays.binarySearch(storage, 0, size, dummyResume, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insertResume(Resume resume, Integer insertionPoint) {
        // Get the estimated position of the resume in the sorted list (based on performing a binary search on Arrays)
        // http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        insertionPoint = -(insertionPoint + 1);
        // If the resume isn't the last one in the list, shift all subsequent resume to the right
        if (size > insertionPoint) {
            System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        }
        storage[insertionPoint] = resume;
    }
}

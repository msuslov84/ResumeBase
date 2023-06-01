package com.suslov.basejava.storage.array;

import com.suslov.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findSearchKey(String uuid) {
        Resume dummyResume = new Resume(uuid, "Dummy");
        return Arrays.binarySearch(storage, 0, size, dummyResume, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insertResume(Resume resume, Integer insertionPoint) {
        // Получаем предполагаемое место резюме в отсортированном списке (на основании выполнения бинарного поиска Arrays)
        // http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239
        insertionPoint = -(insertionPoint + 1);
        // Если резюме не последнее в списке, смещаем все последующие резюме хранилища вправо
        if (size > insertionPoint) {
            System.arraycopy(storage, insertionPoint, storage, insertionPoint + 1, size - insertionPoint);
        }
        storage[insertionPoint] = resume;
    }
}

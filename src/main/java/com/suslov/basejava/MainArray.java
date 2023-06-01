package com.suslov.basejava;

import com.suslov.basejava.model.Resume;
import com.suslov.basejava.storage.array.ArrayStorage;
import com.suslov.basejava.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainArray {
    private final static Storage arrayStorage = new ArrayStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save fullName | delete uuid | get uuid | update uuid fullName | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String param = null;
            if (params.length > 1) {
                param = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(arrayStorage.size());
                    break;
                case "save":
                    r = new Resume(param);
                    arrayStorage.save(r);
                    printAll();
                    break;
                case "update":
                    r = new Resume(param, params[2]);
                    arrayStorage.update(r);
                    printAll();
                    break;
                case "delete":
                    arrayStorage.delete(param);
                    printAll();
                    break;
                case "get":
                    System.out.println(arrayStorage.get(param));
                    break;
                case "clear":
                    arrayStorage.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private static void printAll() {
        List<Resume> all = arrayStorage.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
package com.example.backendapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class FileRepository<T> {

    private final ObjectMapper objectMapper;
    private final Map<String, ReadWriteLock> locks = new ConcurrentHashMap<>();

    public FileRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private File getFile(String fileName) {
        return Paths.get("data", fileName).toFile();
    }

    private ReadWriteLock getLock(String fileName) {
        return locks.computeIfAbsent(fileName, k -> new ReentrantReadWriteLock());
    }

    public List<T> readAll(String fileName, TypeReference<List<T>> typeReference) throws IOException {
        File file = getFile(fileName);
        ReadWriteLock lock = getLock(fileName);
        lock.readLock().lock();
        try {
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, typeReference);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void writeAll(String fileName, List<T> data) throws IOException {
        File file = getFile(fileName);
        ReadWriteLock lock = getLock(fileName);
        lock.writeLock().lock();
        try {
            // Ensure parent directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            objectMapper.writeValue(file, data);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
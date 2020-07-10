package com.krgcorporate.mongopagination.business;

import com.krgcorporate.mongopagination.domain.VeryImportantData;
import com.krgcorporate.mongopagination.repotisory.VeryImportantRepository;
import com.krgcorporate.mongopagination.result.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = @Autowired)
public class VeryImportantManager {

    private final @NonNull Random rnd = new Random();

    private final @NonNull VeryImportantRepository repository;

    public VeryImportantData generate() {
        return VeryImportantData.builder()
                .id(null)
                .createdAt(Instant.now())
                .updatedAt(null)
                .processed(false)
                .value(rnd.nextDouble())
                .build();
    }

    public long insert(int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> generate())
                .peek(repository::insert)
                .count();
    }

    public Status getStatus() {
        return Status.builder()
                .updated(repository.countByProcessed(true))
                .total(repository.count())
                .build();
    }
}

package com.lab.server.repositories;


import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Position;

@Repository
public interface PositionRepository extends BaseRepository<Position, Integer> {
}
